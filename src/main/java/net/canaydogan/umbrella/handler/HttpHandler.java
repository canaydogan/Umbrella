package net.canaydogan.umbrella.handler;

public interface HttpHandler {

	public boolean handleHttpRequest(HttpHandlerContext context) throws Exception;
	
}