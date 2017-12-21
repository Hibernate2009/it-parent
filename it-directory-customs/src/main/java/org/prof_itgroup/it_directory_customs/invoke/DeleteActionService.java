package org.prof_itgroup.it_directory_customs.invoke;

import java.util.Map;

import org.apache.log4j.Logger;
import org.it.utils.sgd.delivery.ActionService;
import org.prof_itgroup.directory.json.Directory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteActionService implements ActionService{
	private static Logger log = Logger.getLogger(DeleteActionService.class.getName());

	private JdbcTemplate template;
	
	public DeleteActionService(JdbcTemplate template) {
		this.template = template;
	}
	
	@Override
	public String action(String message, Map<String, Object> environment) throws Exception {
		String res = "0";
		ObjectMapper mapper = new ObjectMapper();

		try {
			Directory directory = mapper.readValue(message, Directory.class);
			int	result = customsDelete(directory);
			res = String.valueOf(result);
		} catch (Exception e) {
			log.info("CustomsActionService error:" + e.getMessage());
		}
		return res;
	}
	
	private int customsDelete(Directory directory) {
		Map<String, Object> fieldsMap = directory.getDoc().getFieldsMap();
		int update = template.update("delete Measure where id=?", new Object[]{fieldsMap.get("id")});
		return update;
	}

}
