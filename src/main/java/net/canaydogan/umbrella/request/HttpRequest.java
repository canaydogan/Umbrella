package net.canaydogan.umbrella.request;

public interface HttpRequest {
	
	public enum Method {
		GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS
	}
	
	public String getUri();
	
	public HttpRequest setUri(String uri);
	
	public Method getMethod();
	
	public HttpRequest setMethod(Method method);

}
