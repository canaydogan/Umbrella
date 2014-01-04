package net.canaydogan.umbrella.handler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpHandlerStack;
import net.canaydogan.umbrella.condition.AlwaysValidCondition;
import net.canaydogan.umbrella.condition.Condition;

public class ConditionalHttpHandlerStack implements HttpHandlerStack {

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

	public HttpHandlerStack addHttpHandler(HttpHandler handler, Condition condition) {
		stack.put(handler, condition);
		return this;
	}

	public HttpHandlerStack removeHttpHandler(HttpHandler handler) {
		stack.remove(handler);
		return this;
	}

	@Override
	public HttpHandlerStack addHttpHandler(HttpHandler handler) {
		addHttpHandler(handler, new AlwaysValidCondition());
		return this;
	}

}
