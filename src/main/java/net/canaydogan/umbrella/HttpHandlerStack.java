package net.canaydogan.umbrella;

public interface HttpHandlerStack extends HttpHandler {
	
	public HttpHandlerStack addHttpHandler(HttpHandler handler);
	
	public HttpHandlerStack removeHttpHandler(HttpHandler handler);
	
}