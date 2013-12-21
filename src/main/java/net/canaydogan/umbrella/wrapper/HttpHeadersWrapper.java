package net.canaydogan.umbrella.wrapper;

import java.util.List;

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
	public String get(String name) {
		return headers.get(name);
	}

	@Override
	public List<String> getAll(String name) {
		return headers.getAll(name);
	}

	@Override
	public HttpHeaderCollection add(String name, Object value) {
		headers.add(name, value);
		return this;
	}

}
