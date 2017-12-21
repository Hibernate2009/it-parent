package org.it.utils.sgd;

import java.util.UUID;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.it.utils.exception.ExceptionUtil;
import org.it.utils.sgd.delivery.ActionService;
import org.it.utils.sgd.delivery.After;
import org.it.utils.sgd.delivery.Before;
import org.it.utils.sgd.delivery.ContinueAfter;
import org.it.utils.sgd.delivery.ContinueBefore;
import org.it.utils.sgd.delivery.FinalAfter;
import org.it.utils.sgd.delivery.FinalBefore;

public class SGD implements SGDInterface, MessageListener {
	private static Logger log = Logger.getLogger(SGD.class.getName());
	public static final long RECEIVE_TIMEOUT = 1;

	private String queue;
	private String url;
	private InvokeRequestInterface invoker;
	private ActionService actionService;
	private Delivery delivery;

	public SGD(String queue, ActionService actionService, Delivery delivery, InvokeRequestInterface invokeInterface) {
		this.queue = queue;
		this.invoker = invokeInterface;
		this.actionService = actionService;
		this.delivery = delivery;
	}

	public String getQueue() {
		return queue;
	}

	public String getUrl() {
		return url;
	}

	public void invoke(String message, UUID id) {
		log.info("invoke:" + Thread.currentThread().getName());
		// TODO Auto-generated method stub
		delivery.save(message, queue, id);
	}

	public void onMessage(Message jmsMessage) {
		// TODO Auto-generated method stub
		log.info("onMessage:" + Thread.currentThread().getName());
		try {
			TextMessage textMessage = (TextMessage) jmsMessage;
			String id = textMessage.getText();
			String context = delivery.get(id);
			After after = null;
			try {
				Before before = invoker.beforeRequest(context);
				if (before instanceof ContinueBefore) {
					ContinueBefore continueBefore = (ContinueBefore) before;
					try {
						String go = actionService.action(continueBefore.message, continueBefore.environment);
						after = invoker.afterRequest(go, context, continueBefore.environment);
					} catch (Exception e) {
						after = invoker.onAfterRequestException(e, context, continueBefore.environment);
					}
					if (after instanceof FinalAfter) {
						delivery.updateStatus(id, "FINAL");
					} else if (after instanceof ContinueAfter) {
						ContinueAfter continueAfter = (ContinueAfter) after;
						delivery.update(id, continueAfter.getContext(), continueAfter.getDate(), "PROCESS");
					} 
				} else if (before instanceof FinalBefore) {
					delivery.updateStatus(id, "FINAL");
				}
			} catch (Exception e) {
				after = invoker.onBeforeException(e, context);
				if (after instanceof FinalAfter) {
					delivery.updateStatus(id, "FINAL");
				} else if (after instanceof ContinueAfter) {
					ContinueAfter continueAfter = (ContinueAfter) after;
					delivery.update(id, continueAfter.getContext(), continueAfter.getDate(), "PROCESS");
				} 
			}

		} catch (JMSException e) {
			log.info(ExceptionUtil.getPrintStackTraceAsString(e));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info(ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}
}