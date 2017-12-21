package org.it.utils.sgd.delivery;

import java.util.Map;

public interface ActionService {

	public String action(String message, Map<String, Object> environment) throws Exception;
	
}
