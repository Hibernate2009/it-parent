package org.prof_itgroup.it_directory_customs.invoke;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.jms.JmsSender;
import org.it.utils.properties.PropertiesService;
import org.it.utils.sgd.InvokeRequestInterface;
import org.it.utils.sgd.delivery.After;
import org.it.utils.sgd.delivery.Before;
import org.it.utils.sgd.delivery.ContinueAfter;
import org.it.utils.sgd.delivery.ContinueBefore;
import org.it.utils.sgd.delivery.FinalAfter;
import org.prof_itgroup.directory.json.Directory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SgdDeliveryInvoke implements InvokeRequestInterface {
	private static Logger log = Logger.getLogger(SgdDeliveryInvoke.class.getName());

	private PropertiesService propertiesService;

	private JmsSender jmsSender;

	public SgdDeliveryInvoke(PropertiesService propertiesService, JmsSender jmsSender) {
		this.propertiesService = propertiesService;
		this.jmsSender = jmsSender;
	}

	public Before beforeRequest(String context) throws Exception {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(context);
		JsonNode requestNode = root.at("/Request");
		String directoryJson = objectMapper.writeValueAsString(requestNode);

		return new ContinueBefore(directoryJson, null);


	}

	public After afterRequest(String response, String context, Map<String, Object> environment) {
		After after = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(context);
			JsonNode requestNode = root.at("/Request");
			String directoryJson = objectMapper.writeValueAsString(requestNode);
			Directory directory = objectMapper.readValue(directoryJson, Directory.class);
			
			int parseInt = Integer.parseInt(response);
			if (parseInt != 0) {
				log.info("Модуль it-directory-customs успешно выполнил операцию типа "+directory.getEnum());
				after = new FinalAfter();
			}else{
				log.info("Модуль it-directory-customs не смог выполнить операцию типа "+directory.getEnum()+" будет выполнена еще одна попытка");
				after = countLogic(context);
			}
		} catch (Exception e) {
			after = countLogic(context);
		}

		return after;
	}

	public After onAfterRequestException(Exception e, String context, Map<String, Object> environment) {
		return countLogic(context);
	}

	public After onBeforeException(Exception e, String context) {
		return countLogic(context);
	}

	private  After countLogic(String context) {
		After after = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode root = (ObjectNode)objectMapper.readTree(context);
			int count = root.at("/Environment/count").asInt();
			Integer numberOfAttempts = Integer.valueOf(propertiesService.get("NumberOfAttempts"));
			if (count >= numberOfAttempts) {
				JsonNode requestNode = root.at("/Request");
				String json = objectMapper.writeValueAsString(requestNode);
				String message = "Исчерпано количество попыток Customs для сообщения:"+json;
				log.info("Модуль it-directory-customs исчерпал количество попыток для сообщения "+json);
				jmsSender.send("queue_email", message);
				after = new FinalAfter();
			}else{
				count = count + 1;
				ObjectNode environmentNode = (ObjectNode)root.at("/Environment");
				environmentNode.put("count", count);
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				int valueOf = Integer.valueOf(propertiesService.get("DeliverySchedule"));
				cal.add(Calendar.MINUTE, valueOf);
				after = new ContinueAfter(objectMapper.writeValueAsString(root), cal.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(ExceptionUtil.getPrintStackTraceAsString(e));
		}
		return after;
	}

}
