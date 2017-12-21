package org.prof_itgroup.it_directory_customs.invoke;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.it.utils.sgd.delivery.ActionService;
import org.prof_itgroup.directory.json.Directory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AddActionService implements ActionService{
	private static Logger log = Logger.getLogger(AddActionService.class.getName());
	
	private JdbcTemplate template;
	
	public AddActionService(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String action(String message, Map<String, Object> environment) throws Exception {
		String res = "0";
		ObjectMapper mapper = new ObjectMapper();
		try {
			Directory directory = mapper.readValue(message, Directory.class);
			int	result = customsAdd(directory);
			res = String.valueOf(result);
		} catch (Exception e) {
			log.info("AddActionService error:" + e.getMessage());
		}
		return res;
	}
	
	private int customsAdd(Directory directory) throws Exception {
		StringBuilder keyBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		ArrayList<Object> valueList = new ArrayList<Object>();
		keyBuilder.append("(");
		valueBuilder.append("(");
		Map<String, Object> fieldsMap = directory.getDoc().getFieldsMap();
		Set<Entry<String, Object>> entrySet = fieldsMap.entrySet();
		int i = 0;
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			keyBuilder.append(key);
			valueList.add(value);
			valueBuilder.append("?");
			if (i != (entrySet.size() - 1)) {
				keyBuilder.append(", ");
				valueBuilder.append(", ");
			}
			i++;
		}
		keyBuilder.append(")");
		valueBuilder.append(")");
		int update = template.update("insert into Measure " + keyBuilder + " values " + valueBuilder + "", valueList.toArray());
		return update;
	}

}
