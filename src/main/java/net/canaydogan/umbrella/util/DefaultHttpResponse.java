package net.canaydogan.umbrella.util;

import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpResponse;

public class DefaultHttpResponse implements HttpResponse {

	protected Object content;
	
	protected HttpHeaderCollection headerCollection = new SimpleHttpHeaderCollection();
	
	protected HttpCookieCollection cookieCollection = new DefaultHttpCookieCollection();
	
	protected HttpResponse.Status status = HttpResponse.Status.OK;
	
	@Override
	public HttpResponse setContent(Object content) {
		this.content = content;
		return this;
	}

	@Override
	public Object getContent() {
		return content;
	}

	@Override
	public HttpHeaderCollection getHeaderCollection() {
		return headerCollection;
	}

	@Override
	public HttpCookieCollection getCookieCollection() {
		return cookieCollection;
	}

	@Override
	public HttpResponse setStatus(Status status) {
		this.status = status;
		return this;
	}

	@Override
	public Status getStatus() {
		return status;
	}
	
	@Override
	public boolean release() {
		return false;
	}
	
}