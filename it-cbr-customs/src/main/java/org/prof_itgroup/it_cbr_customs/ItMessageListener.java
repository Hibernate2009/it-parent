package org.prof_itgroup.it_cbr_customs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.jdbc.JDBCHelper;
import org.it.utils.properties.PropertiesService;
import org.it.utils.validate.CustomValidator;
import org.it.utils.xml.XmlUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ItMessageListener implements MessageListener{
	
	private static Logger log = Logger.getLogger(ItMessageListener.class.getName());
	
	private PropertiesService propertiesService;
	
	private XmlUtils utils;
	private CustomValidator validator;

	private JDBCHelper jdbcHelper;

	public ItMessageListener(PropertiesService propertiesService, JdbcTemplate template) {
		this.propertiesService = propertiesService;
		
		jdbcHelper = new JDBCHelper(template);
		
		utils = new XmlUtils();
		validator = new CustomValidator();
		
	}

	public void onMessage(Message message) {
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			Document doc = utils.parseMessage(payload);
			String validateInputEgg = propertiesService.get("validate");
			Boolean isValidate = Boolean.valueOf(validateInputEgg);
			
			if (isValidate) {
				try {
					String it_cbr_xsd = "/xsd/it-cbr.xsd";
					validator.validate(doc, ItMessageListener.class.getClassLoader().getResource(it_cbr_xsd));
				} catch (Exception e) {
					log.info("ItMessageListener validate error:"+ExceptionUtil.getPrintStackTraceAsString(e));
					return;
				}
			}
			String dateValue = utils.getStringValue("/ValCurs/@Date", doc);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Date date = simpleDateFormat.parse(dateValue);
			
			
			NodeList documentList = utils.getNodeList("/ValCurs/Valute[CharCode='CNY' or CharCode='INR' or CharCode='JPY' or CharCode='USD' or CharCode='EUR' ]", doc);

			for (int i = 0; i < documentList.getLength(); i++) {

				Node document = documentList.item(i).cloneNode(true);
				String numCode = utils.getStringValue("./NumCode", document);
				String value = utils.getStringValue("./Value", document);
				String nominal = utils.getStringValue("./Nominal", document);
				String charCode = utils.getStringValue("./CharCode", document);
				
				DecimalFormat myFormatter = (DecimalFormat) DecimalFormat.getNumberInstance(new Locale("ru"));
				Double doubleValue = new Double(myFormatter.parse(value).doubleValue());

				List<Map<String, Object>> listMap = jdbcHelper.getListMap("select id from CurrencyList where code=?", new Object[] { charCode });
				for (Map<String, Object> map : listMap) {
					String currencyid = (String) map.get("id");
					jdbcHelper.update("insert into CurrencyRates (dt, currencyid, rateValue, nominal) values(?, ?, ?, ?)", new Object[] { date, currencyid, doubleValue, nominal });
				}

			}
			
			
		}catch(Exception e){
			log.info("ItMessageListener error:"+ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

}
