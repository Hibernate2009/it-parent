package org.prof_itgroup.it_directory_customs.invoke;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.it.utils.sgd.delivery.ActionService;
import org.prof_itgroup.directory.json.Directory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateActionService implements ActionService{
	private static Logger log = Logger.getLogger(UpdateActionService.class.getName());
	
	private JdbcTemplate template;
	
	public UpdateActionService(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String action(String message, Map<String, Object> environment) throws Exception {
		String res = "0";
		ObjectMapper mapper = new ObjectMapper();

		try {
			Directory directory = mapper.readValue(message, Directory.class);
			int	result = customsUpdate(directory);
			res = String.valueOf(result);
		} catch (Exception e) {
			log.info("UpdateActionService error:" + e.getMessage());
		}
		return res;
	}
	
	private int customsUpdate(Directory directory) {
		// TODO Auto-generated method stub
		StringBuilder keyBuilder = new StringBuilder();
		ArrayList<Object> valueList = new ArrayList<Object>();
		Map<String, Object> fieldsMap = directory.getDoc().getFieldsMap();
		Set<Entry<String, Object>> entrySet = fieldsMap.entrySet();
		int i = 0;
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			keyBuilder.append(key);
			keyBuilder.append("=");
			keyBuilder.append("?");
			valueList.add(value);
			if (i != (entrySet.size() - 1)) {
				keyBuilder.append(", ");
			}
			i++;
		}
		valueList.add(directory.getDoc().getFieldsMap().get("id"));

		int update = template.update("update Measure set " + keyBuilder + " where id=?", valueList.toArray());
		return update;
	}

}
