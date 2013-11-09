package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.handler.condition.Condition;

public interface HttpHandlerStack extends HttpHandler {
	
	public HttpHandlerStack addHttpHandler(HttpHandler handler, Condition condition);
	
	public HttpHandlerStack removeHttpHandler(HttpHandler handler);
	
}