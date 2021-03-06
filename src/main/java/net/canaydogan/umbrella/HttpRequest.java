package net.canaydogan.umbrella;

import java.net.SocketAddress;
import java.util.Map;

import net.canaydogan.umbrella.router.RouteMatch;

public interface HttpRequest {
	
	public enum Method {
		GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS, TRACE, CONNECT
	}
	
	public String getUri();
	
	public String getUriWithoutQuery();
	
	public Method getMethod();
	
	public String getContent();
	
	public HttpHeaderCollection getHeaderCollection();
	
	public HttpQuery getQuery();
	
	public HttpCookieCollection getCookieCollection();
	
	public FileUploadCollection getFileUploadCollection();
	
	public boolean isSuccess();
	
	public RouteMatch getRouteMatch();

	public void setRouteMatch(RouteMatch routeMatch);
	
	public boolean isKeepAlive();
	
	/**
	 * This is general purpose data holder. Use it for what you want
	 */
	public Map<Object, Object> getData();
	
	public boolean release();
	
	public SocketAddress getRemoteAddress();
		
}
