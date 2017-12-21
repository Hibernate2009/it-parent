package org.prof_itgroup.it_order;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.jms.JmsSender;
import org.prof_itgroup.json.order.Order;
import org.prof_itgroup.json.result.ServiceResult;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ItMessageListener implements MessageListener {
	private static Logger log = Logger.getLogger(ItMessageListener.class.getName());
	
	private JmsSender jmsSender;
	private JdbcTemplate jdbcTemplate;
	
	public ItMessageListener(JdbcTemplate jdbcTemplate, JmsSender jmsSender) {
		this.jdbcTemplate = jdbcTemplate;
		this.jmsSender = jmsSender;
	}

	public void onMessage(Message message) {
		ObjectMapper mapper = new ObjectMapper();
		ServiceResult serviceResult = new ServiceResult();
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			Order order = mapper.readValue(payload, Order.class);
			log.info("Модуль it-order-claims принял JSON объект Order id:"+order.getDoc().getId());
			try{
				
				Date orderDate = null;
				if (order.getDoc().getOrderDate()!=null && !"".equals(order.getDoc().getOrderDate())){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					orderDate = format.parse(order.getDoc().getOrderDate());
				}
				Date orderPlanDateFrom = null;
				if(order.getDoc().getOrderPlanDateFrom()!=null && !"".equals(order.getDoc().getOrderPlanDateFrom())){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					orderPlanDateFrom=format.parse(order.getDoc().getOrderPlanDateFrom());
				}
				Date orderPlanDateTo = null;
				if (order.getDoc().getOrderPlanDateTo()!=null && !"".equals(order.getDoc().getOrderPlanDateTo())){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					orderPlanDateTo = format.parse(order.getDoc().getOrderPlanDateTo());
				}
				Date dtLoss = null;
				if (order.getDoc().getDtLoss()!=null && !"".equals(order.getDoc().getDtLoss())){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					dtLoss = format.parse(order.getDoc().getDtLoss());
				}
				
				Object[] param = new Object[]{
				order.getDoc().getCargoInvoice(),
				order.getDoc().getClient(),
				order.getDoc().getClientNameEn(),
				order.getDoc().getClientNameRu(),
				order.getDoc().getConsignee(),
				order.getDoc().getConsigneeNameEn(),
				order.getDoc().getConsigneeNameRu(),
				order.getDoc().getEmploye(),
				order.getDoc().getEmployeName(),
				order.getDoc().getFlowtype(),
				order.getDoc().getFlowtypeName(),
				order.getDoc().getId(),
				order.getDoc().getImport(),
				order.getDoc().getModel(),
				order.getDoc().getModelNameEn(),
				order.getDoc().getModelNameRu(),
				orderDate,
				order.getDoc().getOrderDescription(),
				order.getDoc().getOrderDescription1(),
				order.getDoc().getOrderDescription2(),
				order.getDoc().getOrderLdm(),
				order.getDoc().getOrderNo(),
				order.getDoc().getOrderNumber(),
				order.getDoc().getOrderParcels(),
				orderPlanDateFrom,
				orderPlanDateTo,
				order.getDoc().getOrderPrice(),
				order.getDoc().getOrderPriority(),
				order.getDoc().getOrderSono(),
				order.getDoc().getOrderType(),
				order.getDoc().getOrderVolume(),
				order.getDoc().getOrderWeight(),
				order.getDoc().getSender(),
				order.getDoc().getSenderNameEn(),
				order.getDoc().getSenderNameRu(),
				order.getDoc().getSupplier(),
				order.getDoc().getSupplierNameEn(),
				order.getDoc().getSupplierNameRu(),
				order.getDoc().getTruck(),
				order.getDoc().getTruckName(),
				dtLoss,
				order.getDoc().getCompensatedByInsurance(),
				order.getDoc().getNotes(),
				order.getDoc().getTypeId(),
				order.getDoc().getCLMstatus()
				};
			
				jdbcTemplate.update("insert into CustomerOrder ("
				+ "cargoinvoice,"
				+ "client,"
				+ "clientNameEn,"
				+ "clientNameRu,"
				+ "consignee,"
				+ "consigneeNameEn,"
				+ "consigneeNameRu,"
				+ "employe,"
				+ "employeName,"
				+ "flowtype,"
				+ "flowtypeName,"
				+ "id,"
				+ "import,"
				+ "model,"
				+ "modelNameEn,"
				+ "modelNameRu,"
				+ "orderDate,"
				+ "orderDescription,"
				+ "orderDescription1,"
				+ "orderDescription2,"
				+ "orderLdm,"
				+ "orderNo,"
				+ "orderNumber,"
				+ "orderParcels,"
				+ "orderPlanDateFrom,"
				+ "orderPlanDateTo,"
				+ "orderPrice,"
				+ "orderPriority,"
				+ "orderSono,"
				+ "orderType,"
				+ "orderVolume,"
				+ "orderWeight,"
				+ "sender,"
				+ "senderNameEn,"
				+ "senderNameRu,"
				+ "supplier,"
				+ "supplierNameEn,"
				+ "supplierNameRu,"
				+ "truck,"
				+ "truckName,"
				+ "dtLoss,"
				+ "compensatedByInsurance,"
				+ "notes,"
				+ "typeId,"
				+ "CLMstatus"
				+ ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", param);
				
				serviceResult.setError(true);
				serviceResult.setId(order.getDoc().getId());
				try {
					String serviceResultAsString = mapper.writeValueAsString(serviceResult);
					jmsSender.send("it-order-ticket-queue", serviceResultAsString);
				} catch (JsonProcessingException e1) {
					log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
				}
				
			}catch(Exception e){
				log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e));
				serviceResult.setError(false);
				serviceResult.setId(order.getDoc().getId());
				try {
					String serviceResultAsString = mapper.writeValueAsString(serviceResult);
					jmsSender.send("it-order-ticket-queue", serviceResultAsString);
				} catch (JsonProcessingException e1) {
					log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
				}
			}
		}catch(Exception e){
			log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			serviceResult.setError(false);
			try {
				String serviceResultAsString = mapper.writeValueAsString(serviceResult);
				jmsSender.send("it-order-ticket-queue", serviceResultAsString);
			} catch (JsonProcessingException e1) {
				log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e1));
			}
			
		}
	}

}
