package net.canaydogan.umbrella.wrapper;

import io.netty.handler.codec.http.HttpHeaders;
import net.canaydogan.umbrella.HttpHeaderCollection;

public class HttpHeadersWrapper implements HttpHeaderCollection {

	protected HttpHeaders headers;
	
	public HttpHeadersWrapper(HttpHeaders headers) {
		this.headers = headers;
	}
	
	@Override
	public HttpHeaderCollection set(String name, Object value) {
		headers.set(name, value);
		return this;
	}

	@Override
	public Object get(String name) {
		return headers.get(name);
	}

}
