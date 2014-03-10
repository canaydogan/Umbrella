package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpRequest.Method;

public class CorsHandler implements HttpHandler {
	
	public final static String ALLOW_METHODS = "Access-Control-Allow-Methods";
	
	public final static String ALLOW_HEADERS = "Access-Control-Allow-Headers";
	
	public final static String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	
	public final static String REQUEST_METHOD = "Access-Control-Request-Method";
	
	public final static String REQUEST_HEADERS = "Access-Control-Request-Headers";
	
	protected final String origin;
	
	protected String methods; 

	public CorsHandler(String origin, String methods) {
		this.origin = origin;
		this.methods = methods;
	}

	@Override
	public boolean handleHttpRequest(HttpHandlerContext context)
			throws Exception {
		HttpHeaderCollection requestHc = context.getRequest().getHeaderCollection();
		context.getResponse().getHeaderCollection().add(ALLOW_ORIGIN, origin);
		
		if (Method.OPTIONS == context.getRequest().getMethod()
			&& requestHc.contains(REQUEST_HEADERS)
			&& requestHc.contains(REQUEST_METHOD)) {
			context.getResponse().getHeaderCollection().add(ALLOW_METHODS, methods);
			context.getResponse().getHeaderCollection().add(ALLOW_HEADERS, 
					requestHc.get(REQUEST_HEADERS));
			return true;
		}
		
		return false;
	}

}
