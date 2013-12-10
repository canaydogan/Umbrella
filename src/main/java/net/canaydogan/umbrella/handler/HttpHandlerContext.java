package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.router.RouteMatch;

public class HttpHandlerContext {

	protected HttpRequest request;
	
	protected HttpResponse response;
	
	protected RouteMatch routeMatch;
	
	protected Exception exception;
	
	public HttpHandlerContext(HttpRequest request, HttpResponse response) {
		setRequest(request);
		setResponse(response);
	}
	
	public HttpHandlerContext(HttpRequest request, HttpResponse response, RouteMatch routeMatch) {
		this(request, response);
		setRouteMatch(routeMatch);
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public RouteMatch getRouteMatch() {
		return routeMatch;
	}

	public void setRouteMatch(RouteMatch routeMatch) {
		this.routeMatch = routeMatch;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public boolean hasException() {
		return !(null == exception);
	}	
	
}
