package net.canaydogan.umbrella.wrapper;

import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpRequest;

public class HttpRequestWrapper implements HttpRequest {
	
	protected io.netty.handler.codec.http.HttpRequest request;
	
	protected String content;
	
	protected HttpHeaderCollection headerCollection;
	
	public HttpRequestWrapper(io.netty.handler.codec.http.HttpRequest request) {
		this.request = request;
		this.headerCollection = new HttpHeadersWrapper(request.headers());
	}

	@Override
	public String getUri() {
		return request.getUri();
	}

	@Override
	public Method getMethod() {
		if (null == request.getMethod()) {
			return null;
		}
		String methodAsString = request.getMethod().toString();
		
		for (HttpRequest.Method method : HttpRequest.Method.values()) {
			if (method.toString() == methodAsString) {
				return method;
			}
		}		
		
		return null;
	}

	@Override
	public HttpRequest setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public String getContent() {
		return content;
	}
	
	@Override
	public HttpHeaderCollection getHeaderCollection() {
		return headerCollection;
	}

}
