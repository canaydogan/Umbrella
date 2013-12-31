package net.canaydogan.umbrella;


public interface HttpHandler {

	public boolean handleHttpRequest(HttpHandlerContext context) throws Exception;
	
}