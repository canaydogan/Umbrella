package net.canaydogan.umbrella.handler;

import java.util.ArrayList;
import java.util.List;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpHandlerStack;

public class SimpleHttpHandlerStack implements HttpHandlerStack {

	protected List<HttpHandler> stack = new ArrayList<>();
	
	@Override
	public boolean handleHttpRequest(HttpHandlerContext context)
			throws Exception {
		
		for (HttpHandler handler : stack) {
			try {
				if (handler.handleHttpRequest(context)) {
					return true;
				}
			} catch (Exception e) {
				context.setException(e);
			}
		}
		
		return false;
	}

	@Override
	public HttpHandlerStack addHttpHandler(HttpHandler handler) {
		stack.add(handler);
		return this;
	}

	@Override
	public HttpHandlerStack removeHttpHandler(HttpHandler handler) {
		stack.remove(handler);
		return this;
	}

}
