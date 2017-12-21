package org.it.utils.sgd;

import java.util.Map;

import org.it.utils.sgd.delivery.After;
import org.it.utils.sgd.delivery.Before;

public interface InvokeRequestInterface  {
	
	public Before beforeRequest(String context) throws Exception;
	
	public After afterRequest(String response, String context, Map<String, Object> environment);
	
	public After onAfterRequestException(Exception e, String context, Map<String, Object> environment);
	
	public After onBeforeException(Exception e, String context);
	
}
