package net.canaydogan.umbrella.util;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.router.RouteMatch;

public class DefaultHttpHandlerContext implements HttpHandlerContext {

	protected HttpRequest request;
	
	protected HttpResponse response;
	
	protected RouteMatch routeMatch;
	
	protected Exception exception;
	
	public DefaultHttpHandlerContext(HttpRequest request, HttpResponse response) {
		setRequest(request);
		setResponse(response);
	}
	
	public DefaultHttpHandlerContext(HttpRequest request, HttpResponse response, RouteMatch routeMatch) {
		this(request, response);
		setRouteMatch(routeMatch);
	}

	@Override
	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	@Override
	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	@Override
	public RouteMatch getRouteMatch() {
		return routeMatch;
	}

	@Override
	public void setRouteMatch(RouteMatch routeMatch) {
		this.routeMatch = routeMatch;
	}

	@Override
	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public Exception getException() {
		return exception;
	}

	@Override
	public boolean hasException() {
		return !(null == exception);
	}
	
}
