package org.prof_itgroup.it_order;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.prof_itgroup.json.result.ServiceResult;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ItTicketListener implements MessageListener {
	private static Logger log = Logger.getLogger(ItTicketListener.class.getName());
	
	private JdbcTemplate jdbcTemplate;

	public ItTicketListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void onMessage(Message message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			ServiceResult order = mapper.readValue(payload, ServiceResult.class);
			log.info("Модуль it-order-claims принял сообщение о изменении статуса id:"+order.getId());
			try {
				Object[] param = new Object[] { order.getError(), order.getId() };

				jdbcTemplate.update("update DamageCase set \"4PLstatus\" = ? where orderId = ?", param);

			} catch (Exception e) {
				log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			}
		} catch (Exception e) {
			log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

}
