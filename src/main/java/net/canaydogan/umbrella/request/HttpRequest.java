package net.canaydogan.umbrella.request;

public interface HttpRequest {
	
	public enum Method {
		GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS, TRACE, CONNECT
	}
	
	public String getUri();
	
	public Method getMethod();

}
