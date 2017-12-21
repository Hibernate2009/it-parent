package org.prof_itgroup.rest;

import org.apache.log4j.Logger;
import org.it.utils.jms.JmsSender;
import org.prof_itgroup.json.damage.Damage;
import org.prof_itgroup.json.order.Order;
import org.prof_itgroup.json.result.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/it/")
public class JSONController {
	private static Logger log = Logger.getLogger(JSONController.class.getName());
	
	private JmsSender jmsSender;

	public JSONController(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	//https://timboudreau.com/blog/json/read
	
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<ServiceResult> order(@RequestBody String request) throws Exception{
		ServiceResult result = new ServiceResult();
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			Order order = mapper.readValue(request, Order.class);
			result.setId(order.getDoc().getId());
			result.setError(false);
			
			log.info("Модуль it-rest принял JSON объект Order id:"+order.getDoc().getId());
			
			jmsSender.send("it-order-queue", request);
		}catch(Exception e){
			log.info(e.getMessage());
			result.setError(true);
		}
	    return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/damage", method = RequestMethod.POST)
	public ResponseEntity<ServiceResult> damage(@RequestBody String request) throws Exception {
		ServiceResult result = new ServiceResult();
		try{
			log.info("Модуль it-rest принял JSON объект Damage");
			ObjectMapper mapper = new ObjectMapper();
			Damage damage = mapper.readValue(request, Damage.class);
			result.setId(damage.getDoc().getOrderId());
			result.setError(false);
			
			log.info("Модуль it-rest принял JSON объект Damage id:"+damage.getDoc().getOrderId());
			
			jmsSender.send("it-damage-queue", request);
		}catch(Exception e){
			log.info(e.getMessage());
			result.setError(true);
		}
	    return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}
	
	
	public static void main(String arg[]) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String request = "{\r\n" + 
				"  \"app\":\"1\",\r\n" + 
				"  \"doc\": { \r\n" + 
				"  \"_id\": \"BrK7B0iOKd\",\r\n" + 
				"  \"CLMstatus\": 0,\r\n" + 
				"  \"compensatedByInsurance\": 20,\r\n" + 
				"  \"createdAt\": \"2017-11-16T08:32:45.280Z\",\r\n" + 
				"  \"updateACL\": [],\r\n" + 
				"  \"orderDate\": \"2017-11-21T08:31:11.067Z\",\r\n" + 
				"  \"removeACL\": [],\r\n" + 
				"  \"cargoInvoice\": \"INV-299986\",\r\n" + 
				"  \"readACL\": [],\r\n" + 
				"  \"orderNumber\": \"305847334\",\r\n" + 
				"  \"updatedAt\": \"2017-11-21T07:41:32.416Z\",\r\n" + 
				"  \"notes\": \"QuattroPole\",\r\n" + 
				"  \"senderId\": 212,\r\n" + 
				"  \"typeId\": 1,\r\n" + 
				"  \"id\": 30584733421226,\r\n" + 
				"  \"client\": 648,\r\n" + 
				"  \"orderPrice\": 5000,\r\n" + 
				"  \"orderWeight\": 5,\r\n" + 
				"  \"orderVolume\": 3,\r\n" + 
				"  \"consignee\": 264,\r\n" + 
				"  \"clientNameEn\": \"Cluster4\",\r\n" + 
				"  \"clientNameRu\": \"Ровио инк. филиал 2\\n\\n\",\r\n" + 
				"  \"consigneeNameEn\": \"Del Ta 333\",\r\n" + 
				"  \"consigneeNameRu\": \"Дельта ООО отдел 3\",\r\n" + 
				"  \"employe\": \"Nmhp7QypCh\",\r\n" + 
				"  \"employeName\": \"DeFr22 ddod\\n\",\r\n" + 
				"  \"flowtype\": 2,\r\n" + 
				"  \"import\": \"2RPdcUff32\",\r\n" + 
				"  \"model\": \"9DMheVw3kU\",\r\n" + 
				"  \"modelNameEn\": \"Vega\",\r\n" + 
				"  \"modelNameRu\": \"Вега\",\r\n" + 
				"  \"orderNo\": \"ges\",\r\n" + 
				"  \"sender\": \"Deville\",\r\n" + 
				"  \"supplier\": \"af8X2J0QHP\",\r\n" + 
				"  \"supplierNameEn\": \"PRIM\",\r\n" + 
				"  \"senderNameRu\": \"Сито 333\" }\r\n" + 
				"}";
		
		Order readValue = mapper.readValue(request, Order.class);
		int r=0;
	}

}
