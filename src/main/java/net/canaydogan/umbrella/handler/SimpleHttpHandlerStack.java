package net.canaydogan.umbrella.handler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.canaydogan.umbrella.handler.condition.Condition;

public class SimpleHttpHandlerStack implements HttpHandlerStack {

	protected Map<HttpHandler, Condition> stack = new LinkedHashMap<>();
	
	@Override
	public boolean handleHttpRequest(HttpHandlerContext context)
			throws Exception {
		
		for (Entry<HttpHandler, Condition> entry : stack.entrySet()) {
			if (true == entry.getValue().isValid(context)) {
				try {
					if (entry.getKey().handleHttpRequest(context)) {
						return true;
					}	
				} catch (Exception e) {
					context.setException(e);
				}				
			}
		}
		
		return false;
	}

	@Override
	public HttpHandlerStack addHttpHandler(HttpHandler handler, Condition condition) {
		stack.put(handler, condition);
		return this;
	}

	@Override
	public HttpHandlerStack removeHttpHandler(HttpHandler handler) {
		stack.remove(handler);
		return this;
	}

}
