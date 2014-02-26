package net.canaydogan.umbrella.util;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;

public class DefaultHttpHandlerContext implements HttpHandlerContext {

	protected HttpRequest request;
	
	protected HttpResponse response;	
	
	protected Exception exception;
	
	public DefaultHttpHandlerContext(HttpRequest request, HttpResponse response) {
		setRequest(request);
		setResponse(response);
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

	@Override
	public boolean release() {
		getRequest().release();
		return false;
	}
	
}
