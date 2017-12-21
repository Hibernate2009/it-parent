package org.prof_itgroup.it_directory_fourpl.invoke;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.properties.PropertiesService;
import org.it.utils.sgd.delivery.ActionService;
import org.prof_itgroup.directory.json.Directory;
import org.prof_itgroup.scorocode.json.login.LoginRequest;
import org.prof_itgroup.scorocode.json.login.LoginResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DeleteActionService implements ActionService{
	private static Logger log = Logger.getLogger(DeleteActionService.class.getName());
	private PropertiesService propertiesService;

	public DeleteActionService(PropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}
	
	@Override
	public String action(String message, Map<String, Object> environment) throws Exception {
		String res = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			Directory directory = mapper.readValue(message, Directory.class);
			boolean	result = customsDelete(directory);
			res = Boolean.toString(result);
		} catch (Exception e) {
			log.info("AddActionService error:" + e.getMessage());
		}
		return res;
	}
	
	private boolean customsDelete(Directory directory) throws Exception {
		boolean res = true;
		try {
			LoginRequest loginRequest = toLoginRequest(propertiesService);
			RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
			restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

			HttpEntity<LoginRequest> httpLoginRequest = new HttpEntity<LoginRequest>(loginRequest);
			LoginResponse loginResponse = restTemplate.postForObject("https://api.scorocode.ru/api/v1/login", httpLoginRequest, LoginResponse.class);
			Boolean loginError = loginResponse.getError();

			if (!loginError) {
				try {
					String session = loginResponse.getResult().getSessionId();
					
					ObjectMapper mapper = new ObjectMapper();
					ObjectNode mainNode = mapper.createObjectNode();
					mainNode.put("app", propertiesService.get("app"));
					mainNode.put("cli", propertiesService.get("cli"));
					mainNode.put("acc", "");
					mainNode.put("sess", session);
					mainNode.put("coll", "measure_test");
					
					mainNode.set("query", toQueryFields(directory));
					
					String deleteRequest = mapper.writeValueAsString(mainNode);
					
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					HttpEntity<String> entity = new HttpEntity<String>(deleteRequest, headers);

					String deleteResponseBody = restTemplate.postForObject("https://api.scorocode.ru/api/v1/data/remove", entity, String.class);
					Map<String, Object> deleteResponseMap = new HashMap<String, Object>();

					deleteResponseMap = mapper.readValue(deleteResponseBody, new TypeReference<HashMap<String, Object>>() {});
					res = (Boolean)deleteResponseMap.get("error");
					if (res){
						log.info("ItMessageListener error:" + deleteResponseBody);
					}else{
						HashMap resultMap = (HashMap)deleteResponseMap.get("result");
						Integer count = (Integer)resultMap.get("count");
						if(count==0){
							res = true;
						}
					}
				}catch (Exception e) {
					log.info("Error:"+ ExceptionUtil.getPrintStackTraceAsString(e));
					throw e;
				}
				
			} else {
				log.info("Login business fail");
				throw new Exception("Login business fail");
			}
		}catch(Exception e){
			log.info("Error:"+ ExceptionUtil.getPrintStackTraceAsString(e));
			throw e;
		}
		return res;
	}
	
	
	private JsonNode toQueryFields(Directory directory) {
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> fieldsMap = directory.getDoc().getFieldsMap();
		
		Integer id = (Integer)fieldsMap.get("id");
		
		ObjectNode node = mapper.createObjectNode();
		ObjectNode fieldNode = mapper.createObjectNode();
		fieldNode.put("$eq", id);
		node.set("id", fieldNode);
		
		return node;
	}

	private LoginRequest toLoginRequest(PropertiesService propertiesService) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setApp(propertiesService.get("app"));
		loginRequest.setCli(propertiesService.get("cli"));
		loginRequest.setEmail(propertiesService.get("email"));
		loginRequest.setPassword(propertiesService.get("password"));
		return loginRequest;
	}
	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(60000);
		factory.setConnectTimeout(60000);
		return factory;
	}

}
