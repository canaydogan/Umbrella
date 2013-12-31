package net.canaydogan.umbrella;

import net.canaydogan.umbrella.condition.Condition;

public interface HttpHandlerStack extends HttpHandler {
	
	public HttpHandlerStack addHttpHandler(HttpHandler handler, Condition condition);
	
	public HttpHandlerStack removeHttpHandler(HttpHandler handler);
	
}