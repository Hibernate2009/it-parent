package org.it.utils.sgd.delivery;

import java.util.Map;

import org.it.utils.http.HTTPLogListener;

public class ContinueBefore implements Before{
	public String message;
	
	public Map<String, Object> environment;
	public ContinueBefore(String message, Map<String, Object> environment) {
		
		this.message = message;
		this.environment = environment;
	}
}
