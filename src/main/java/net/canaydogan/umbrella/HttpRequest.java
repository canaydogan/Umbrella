package net.canaydogan.umbrella;

import net.canaydogan.umbrella.router.RouteMatch;

public interface HttpRequest {
	
	public enum Method {
		GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS, TRACE, CONNECT
	}
	
	public String getUri();
	
	public Method getMethod();
	
	public HttpRequest setContent(String content);
	
	public String getContent();
	
	public HttpHeaderCollection getHeaderCollection();
	
	public HttpQuery getQuery();
	
	public HttpCookieCollection getCookieCollection();
	
	public boolean isSuccess();
	
	public RouteMatch getRouteMatch();

	public void setRouteMatch(RouteMatch routeMatch);
		
}
