package org.prof_itgroup.it_directory_fourpl;

import java.util.UUID;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.it.utils.sgd.SGD;
import org.prof_itgroup.directory.json.Directory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SgdMessageListener implements MessageListener{
	private static Logger log = Logger.getLogger(SgdMessageListener.class.getName());
	
	private SGD sgd;

	public SgdMessageListener(SGD sgd) {
		this.sgd = sgd;
	}
	
	public void onMessage(Message message) {
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			Directory directory = objectMapper.readValue(payload, Directory.class);
			String system = directory.getSystem();
			if (!"4pl".equalsIgnoreCase(system)){
				log.info("Модуль it-directory-fourpl принял JSON объект "+directory.getEnum() + " от системы "+directory.getSystem());
				JsonNode directoryNode = objectMapper.readValue(payload, JsonNode.class);
				ObjectNode contextNode = toContext(directoryNode);
				String context = objectMapper.writeValueAsString(contextNode);
				sgd.invoke(context, UUID.randomUUID());
			}
			
		}catch(Exception e){
			log.info("SgdMessageListener error:"+e.getMessage());
		}
	}
	
	private ObjectNode toContext(JsonNode directoryNode){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		ObjectNode environment = mapper.createObjectNode();
		environment.put("count", 0);
		mainNode.set("Environment", environment);
		mainNode.set("Request", directoryNode);
		return mainNode;
		
	}
}
