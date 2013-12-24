package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpHandlerContext;

public interface HttpHandler {

	public boolean handleHttpRequest(HttpHandlerContext context) throws Exception;
	
}