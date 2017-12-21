package org.prof_itgroup.email;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.properties.PropertiesService;

public class EmailMessageListener implements MessageListener {
	private static Logger log = Logger.getLogger(EmailMessageListener.class);

	private EmailSenderTLS emailSender;

	private PropertiesService propertiesService;

	public EmailMessageListener(PropertiesService propertiesService, EmailSenderTLS emailSender) {
		this.propertiesService = propertiesService;
		this.emailSender = emailSender;
	}

	public void onMessage(Message message) {
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			String isSend = propertiesService.get("isSend");
			boolean parseBoolean = Boolean.parseBoolean(isSend);
			if(parseBoolean){
				emailSender.send(payload);
			}else{
				log.info("Email message:"+payload);
			}
		} catch (Exception e) {
			log.info(ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

}
