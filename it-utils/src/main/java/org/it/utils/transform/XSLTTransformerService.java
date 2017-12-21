package org.it.utils.transform;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTTransformerService{

	private Logger log = Logger.getLogger(XSLTTransformerService.class.getName());

	public String transform(String data, InputStream resourceAsStream, Map<String, Object> parameters) throws TransformerException {
		return transform(data, resourceAsStream, "yes", parameters);
	}
	
	public String transform(String data, InputStream resourceAsStream, String indent, Map<String, Object> parameters) throws TransformerException {
		String res = "";
		if (resourceAsStream == null) {
			log.info("resourceAsStream is null");
			return res;
		}

		TransformerFactory factory = TransformerFactory.newInstance();
		StreamSource xslStream = new StreamSource(resourceAsStream);

		Transformer transformer = factory.newTransformer(xslStream);
		if (indent!=null){
			transformer.setOutputProperty(OutputKeys.INDENT, indent);
		}else{
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		}
		if (parameters != null) {
			Set<String> keySet = parameters.keySet();
			for (String key : keySet) {
				transformer.setParameter(key, parameters.get(key));
			}
		}
		StringWriter stringWriter = new StringWriter();
		StreamSource in = new StreamSource(new StringReader(data));
		StreamResult out = new StreamResult(stringWriter);
		transformer.transform(in, out);
		res = stringWriter.toString();
		return res;
	}

	public String toStub(String message) {
		String stub = "<p:stub xmlns:p=\"http://com.bssys/stub\">%s</p:stub>";
		return String.format(stub, message);
	}

	public String toStupid() {
		String stub = "<p:stub xmlns:p=\"http://com.bssys/stub\"/>";
		return stub;
	}

}
