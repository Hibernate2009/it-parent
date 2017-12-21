package org.prof_itgroup.it_damage;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.jms.JmsSender;
import org.it.utils.properties.PropertiesService;
import org.it.utils.xml.XmlUtils;
import org.prof_itgroup.json.damage.Damage;
import org.prof_itgroup.json.result.ServiceResult;
import org.prof_itgroup.scorocode.json.login.LoginRequest;
import org.prof_itgroup.scorocode.json.login.LoginResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ItMessageListener implements MessageListener {
	private static Logger log = Logger.getLogger(ItMessageListener.class.getName());

	private PropertiesService propertiesService;

	private JmsSender jmsSender;

	public ItMessageListener(PropertiesService propertiesService, JmsSender jmsSender) {
		this.propertiesService = propertiesService;
		this.jmsSender = jmsSender;
	}

	public void onMessage(Message message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			Damage damage = mapper.readValue(payload, Damage.class);
			log.info("Модуль it-damage-fourpl принял JSON объект Damage id:"+damage.getDoc().getId());

			LoginRequest loginRequest = toLoginRequest(propertiesService);

			try {
				RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

				HttpEntity<LoginRequest> httpLoginRequest = new HttpEntity<LoginRequest>(loginRequest);
				LoginResponse loginResponse = restTemplate.postForObject("https://api.scorocode.ru/api/v1/login", httpLoginRequest, LoginResponse.class);
				Boolean loginError = loginResponse.getError();

				if (!loginError) {
					try {
						String session = loginResponse.getResult().getSessionId();
						String collection = "customerorder";

						ObjectNode queryNode = toQueryNode("id", String.valueOf(damage.getDoc().getOrderId()));
						ObjectNode docFieldNode = toDocFieldNode("dtLoss", damage.getDoc().getDtLoss());
						docFieldNode.put("compensatedByInsurance", damage.getDoc().getCompensatedByInsurance());
						docFieldNode.put("notes", damage.getDoc().getNotes());
						docFieldNode.put("typeId", damage.getDoc().getTypeId());
						ObjectNode docNode = toDocNode("$set", docFieldNode);
						ObjectNode updateByIdJson = toUpdateByIdJson(session, collection, queryNode, docNode);
						String updateRequest = mapper.writeValueAsString(updateByIdJson);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						HttpEntity<String> entity = new HttpEntity<String>(updateRequest, headers);

						String updateResponseBody = restTemplate.postForObject("https://api.scorocode.ru/api/v1/data/update", entity, String.class);
						// String updateResponseBody =
						// updateResponseHttpEntity.getBody();

						Map<String, Object> updateResponseMap = new HashMap<String, Object>();

						updateResponseMap = mapper.readValue(updateResponseBody, new TypeReference<HashMap<String, Object>>() {});
						Boolean error = (Boolean)updateResponseMap.get("error");
						if (error){
							log.info("ItMessageListener error:" + updateResponseBody);
							sendServiceResult("it-damage-ticket-queue", damage.getDoc().getOrderId(), error);
						}else{
							HashMap resultMap = (HashMap)updateResponseMap.get("result");
							Integer count = (Integer)resultMap.get("count");
							if (count == 0){
								log.info("ItMessageListener error:" + updateResponseBody);
								sendServiceResult("it-damage-ticket-queue", damage.getDoc().getOrderId(), false);
							}else{
								sendServiceResult("it-damage-ticket-queue", damage.getDoc().getOrderId(), true);
							}
						}
					} catch (Exception e) {
						log.info("updateById system fail:"+e.getMessage());
						sendServiceResult("it-damage-ticket-queue", damage.getDoc().getOrderId(), false);
					}
				} else {
					log.info("Login business fail");
					sendServiceResult("it-damage-ticket-queue", damage.getDoc().getOrderId(), false);
				}
			} catch (Exception e) {
				log.info("Login system fail:"+e.getMessage());
				sendServiceResult("it-damage-ticket-queue", damage.getDoc().getOrderId(), false);
			}

		} catch (Exception e) {
			log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			sendServiceResult("it-damage-ticket-queue",  false);
		}
	}

	private void sendServiceResult(String queue, long orderId, boolean error) {
		
		ObjectMapper mapper = new ObjectMapper();
		ServiceResult serviceResult = new ServiceResult();
		serviceResult.setId(orderId);
		serviceResult.setError(error);
		try {
			String serviceResultAsString = mapper.writeValueAsString(serviceResult);
			jmsSender.send(queue, serviceResultAsString);
		} catch (Exception e1) {
			log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
		}
	}
	private void sendServiceResult(String queue, boolean error) {
		
		ObjectMapper mapper = new ObjectMapper();
		ServiceResult serviceResult = new ServiceResult();
		serviceResult.setError(error);
		try {
			String serviceResultAsString = mapper.writeValueAsString(serviceResult);
			jmsSender.send(queue, serviceResultAsString);
		} catch (Exception e1) {
			log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
		}
	}

	private LoginRequest toLoginRequest(PropertiesService propertiesService) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setApp(propertiesService.get("app"));
		loginRequest.setCli(propertiesService.get("cli"));
		loginRequest.setEmail(propertiesService.get("email"));
		loginRequest.setPassword(propertiesService.get("password"));
		return loginRequest;
	}

	private ObjectNode toQueryNode(String field, String value) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		ObjectNode rqNode = mapper.createObjectNode();
		rqNode.put("$eq", Long.parseLong(value));
		mainNode.set(field, rqNode);
		return mainNode;
	}

	private ObjectNode toDocNode(String field, ObjectNode docFieldNode) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.set(field, docFieldNode);
		return mainNode;
	}

	private ObjectNode toDocFieldNode(String field, String value) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.put(field, value);
		return mainNode;
	}

	private ObjectNode toUpdateByIdJson(String session, String collection, ObjectNode queryNode, ObjectNode docNode) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.put("app", propertiesService.get("app"));
		mainNode.put("cli", propertiesService.get("cli"));
		mainNode.put("acc", "");
		mainNode.put("sess", session);
		mainNode.put("coll", collection);

		mainNode.set("query", queryNode);
		mainNode.set("doc", docNode);

		return mainNode;
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(60000);
		factory.setConnectTimeout(60000);
		return factory;
	}

}
