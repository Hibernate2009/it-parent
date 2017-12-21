package org.prof_itgroup.it_cbr_fourpl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.properties.PropertiesService;
import org.it.utils.validate.CustomValidator;
import org.it.utils.xml.XmlUtils;
import org.prof_itgroup.it_cbr_fourpl.json.find.CurrencyName;
import org.prof_itgroup.it_cbr_fourpl.json.find.FindRequest;
import org.prof_itgroup.it_cbr_fourpl.json.find.FindResponse;
import org.prof_itgroup.it_cbr_fourpl.json.find.Query;
import org.prof_itgroup.it_cbr_fourpl.json.insert.Doc;
import org.prof_itgroup.it_cbr_fourpl.json.insert.InsertRequest;
import org.prof_itgroup.it_cbr_fourpl.json.insert.InsertResponse;
import org.prof_itgroup.it_cbr_fourpl.json.login.LoginRequest;
import org.prof_itgroup.it_cbr_fourpl.json.login.LoginResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;

public class ItMessageListener implements MessageListener {

	private static Logger log = Logger.getLogger(ItMessageListener.class.getName());

	private PropertiesService propertiesService;

	private XmlUtils utils;
	private CustomValidator validator;

	public ItMessageListener(PropertiesService propertiesService) {
		this.propertiesService = propertiesService;

		utils = new XmlUtils();
		validator = new CustomValidator();

	}

	public void onMessage(Message message) {
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			Document payloadDoc = utils.parseMessage(payload);
			String validateInputEgg = propertiesService.get("validate");
			Boolean isValidate = Boolean.valueOf(validateInputEgg);

			if (isValidate) {
				try {
					String it_cbr_xsd = "/xsd/it-cbr.xsd";
					validator.validate(payloadDoc, ItMessageListener.class.getClassLoader().getResource(it_cbr_xsd));
				} catch (Exception e) {
					log.info("ItMessageListener validate error:" + ExceptionUtil.getPrintStackTraceAsString(e));
					return;
				}
			}
			String dateValue = utils.getStringValue("/ValCurs/@Date", payloadDoc);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Date date = simpleDateFormat.parse(dateValue);

			// http://www.jsonschema2pojo.org/
			LoginRequest loginRequest = toLoginRequest();

			RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

			HttpEntity<LoginRequest> httpLoginRequest = new HttpEntity<LoginRequest>(loginRequest);
			LoginResponse loginResponse = restTemplate.postForObject("https://api.scorocode.ru/api/v1/login", httpLoginRequest, LoginResponse.class);
			Boolean loginError = loginResponse.getError();
			if (!loginError) {
				String sessionId = loginResponse.getResult().getSessionId();

				NodeList valuteList = utils.getNodeList("/ValCurs/Valute[CharCode='CNY' or CharCode='INR' or CharCode='JPY' or CharCode='USD' or CharCode='EUR' ]", payloadDoc);

				for (int i = 0; i < valuteList.getLength(); i++) {

					Node valuteDoc = valuteList.item(i).cloneNode(true);
					String value = utils.getStringValue("./Value", valuteDoc);
					String charCode = utils.getStringValue("./CharCode", valuteDoc);

					FindRequest findRequest = toFindRequest(sessionId, charCode);

					HttpEntity<FindRequest> httpFindRequest = new HttpEntity<FindRequest>(findRequest);
					FindResponse findResponse = restTemplate.postForObject("https://api.scorocode.ru/api/v1/data/find", httpFindRequest, FindResponse.class);

					Boolean findError = findResponse.getError();

					if (!findError) {

						String resultBson = findResponse.getResult();
						List<Map<String, Object>> listMap = getListMap(Base64.decodeBase64(resultBson));
						if (!listMap.isEmpty()) {
							Map<String, Object> map = listMap.get(0);
							String currency = (String) map.get("_id");

							InsertRequest insertRequest = toInsertRequest(date, sessionId, value, currency);

							HttpEntity<InsertRequest> httpInsertRequest = new HttpEntity<InsertRequest>(insertRequest);

							InsertResponse insertResponse = restTemplate.postForObject("https://api.scorocode.ru/api/v1/data/insert", httpInsertRequest, InsertResponse.class);
							Boolean insertError = insertResponse.getError();
							if (insertError) {
								log.info("https://api.scorocode.ru/api/v1/insert error");
							}
						}else {
							log.info("https://api.scorocode.ru/api/v1/data/find is empty");
						}
					}
				}
			} else {
				log.info("https://api.scorocode.ru/api/v1/login error");
			}

		} catch (Exception e) {
			log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

	private void exchangeRequest(RestTemplate restTemplate, HttpEntity<InsertRequest> httpInsertRequest) {
		ResponseEntity<String> response = restTemplate.exchange("https://api.scorocode.ru/api/v1/data/insert", HttpMethod.POST, httpInsertRequest, String.class);
	}

	private LoginRequest toLoginRequest() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setApp(propertiesService.get("app"));
		loginRequest.setCli(propertiesService.get("cli"));
		loginRequest.setEmail(propertiesService.get("email"));
		loginRequest.setPassword(propertiesService.get("password"));
		return loginRequest;
	}

	private InsertRequest toInsertRequest(Date date, String sessionId, String value, String currency) throws ParseException {
		DecimalFormat myFormatter = (DecimalFormat) DecimalFormat.getNumberInstance(new Locale("ru"));
		InsertRequest insertRequest = new InsertRequest();
		insertRequest.setApp(propertiesService.get("app"));
		insertRequest.setCli(propertiesService.get("cli"));
		insertRequest.setSess(sessionId);
		insertRequest.setColl("currencyratebus");

		Doc doc = new Doc();
		doc.setCurrency(currency);

		doc.setCurrencyrateDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date));
		doc.setCurrencyrateValue(new Double(myFormatter.parse(value).doubleValue()));
		insertRequest.setDoc(doc);
		return insertRequest;
	}

	private FindRequest toFindRequest(String sessionId, String charCode) {
		FindRequest findRequest = new FindRequest();
		findRequest.setApp(propertiesService.get("app"));
		findRequest.setCli(propertiesService.get("cli"));
		findRequest.setSess(sessionId);
		findRequest.setColl("currency");
		Query query = new Query();
		CurrencyName currencyName = new CurrencyName();
		currencyName.set$eq(charCode);
		query.setCurrencyName(currencyName);
		findRequest.setQuery(query);
		ArrayList<String> fieldList = new ArrayList<String>();
		fieldList.add("_id");
		findRequest.setFields(fieldList);
		findRequest.setLimit(100);
		return findRequest;
	}

	public static List<Map<String, Object>> getListMap(byte[] data) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new BsonFactory());
		JsonNode treeNode = mapper.readTree(data);
		Map<String, Object> result = mapper.convertValue(treeNode, Map.class);
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Set<Entry<String, Object>> entrySet = result.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			Map value = (Map) entry.getValue();
			list.add(value);
		}
		return list;
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(60000);
		factory.setConnectTimeout(60000);
		return factory;
	}
}
