
package org.prof_itgroup.directory.json;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Doc {
	
	private Map<String, Object> properties = new LinkedHashMap<String, Object>();

	@JsonAnySetter
    public void add(String key, Object value) {
        properties.put(key, value);
    }
	
	public Map<String, Object> getFieldsMap(){
		return properties;
	}

}
