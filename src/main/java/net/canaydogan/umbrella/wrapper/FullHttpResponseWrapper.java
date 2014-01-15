package net.canaydogan.umbrella.wrapper;

import io.netty.handler.codec.http.FullHttpResponse;
import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.util.DefaultHttpCookieCollection;

public class FullHttpResponseWrapper implements HttpResponse {

	protected FullHttpResponse response;
	
	protected Object content;
	
	protected HttpHeaderCollection headerCollection;
	
	protected HttpCookieCollection cookieCollection = new DefaultHttpCookieCollection();
	
	public FullHttpResponseWrapper(FullHttpResponse response) {
		this.response = response;
		this.headerCollection = new HttpHeadersWrapper(response.headers());
	}
	
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

	public FullHttpResponse getNettyResponse() {
		return response;
	}

	@Override
	public HttpCookieCollection getCookieCollection() {
		return cookieCollection;
	}

	@Override
	public HttpResponse setStatus(Status status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
