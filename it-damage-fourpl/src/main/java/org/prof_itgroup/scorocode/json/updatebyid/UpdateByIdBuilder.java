package org.prof_itgroup.scorocode.json.updatebyid;

import org.it.utils.properties.PropertiesService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UpdateByIdBuilder {
	
	private PropertiesService propertiesService;

	public UpdateByIdBuilder(PropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}
	
	
	
	
	private ObjectNode toQueryNode(String field, String value){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.put(field,value);
		return mainNode;
	}
	private ObjectNode toDocNode(String field, ObjectNode docFieldNode){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.set(field,docFieldNode);
		return mainNode;
	}
	private ObjectNode toDocFieldNode(String field, String value){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.put(field,value);
		return mainNode;
	}
	
	private ObjectNode toUpdateByIdJson(String session, String collection, ObjectNode queryNode, ObjectNode docNode){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.put("app",propertiesService.get("app"));
		mainNode.put("cli",propertiesService.get("cli"));
		mainNode.put("acc","");
		mainNode.put("sess",session);
		mainNode.put("coll",collection);
		
		mainNode.set("query",queryNode);
		mainNode.set("doc",queryNode);
		
		return mainNode;
	}
}
