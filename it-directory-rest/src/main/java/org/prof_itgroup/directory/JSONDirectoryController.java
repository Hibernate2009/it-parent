package org.prof_itgroup.directory;

import org.apache.log4j.Logger;
import org.it.utils.jms.JmsSender;
import org.prof_itgroup.directory.json.Directory;
import org.prof_itgroup.json.result.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/it/")
public class JSONDirectoryController {
	private static Logger log = Logger.getLogger(JSONDirectoryController.class.getName());
	
	private JmsSender jmsSender;
	
	public JSONDirectoryController(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	@RequestMapping(value = "/directory", method = RequestMethod.POST)
	public ResponseEntity<ServiceResult> order(@RequestBody String request) throws Exception{
		ServiceResult result = new ServiceResult();
		try{
			ObjectMapper mapper = new ObjectMapper();
			Directory directory = mapper.readValue(request, Directory.class);
			String enumType = directory.getEnum();
			log.info("Модуль it-directory-rest принял JSON объект "+enumType + " от системы "+directory.getSystem());
			if ("add".equals(enumType)){
				jmsSender.send("add-topic", request);
			}else if ("update".equals(enumType)){
				jmsSender.send("update-topic", request);
			}else if ("delete".equals(enumType)){
				jmsSender.send("delete-topic", request);
			}
			result.setError(false);
		}catch(Exception e){
			log.info(e.getMessage());
			result.setError(true);
		}
	    return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}

}
