package net.canaydogan.umbrella.wrapper;

import java.util.List;
import java.util.Set;

import io.netty.handler.codec.http.HttpHeaders;
import net.canaydogan.umbrella.HttpHeaderCollection;

public class HttpHeadersWrapper implements HttpHeaderCollection {

	protected HttpHeaders headers;
	
	public HttpHeadersWrapper(HttpHeaders headers) {
		this.headers = headers;
	}
	
	@Override
	public HttpHeaderCollection set(String name, String value) {
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
	public HttpHeaderCollection add(String name, String value) {
		headers.add(name, value);
		return this;
	}

	@Override
	public boolean contains(String name) {
		return headers.contains(name);
	}

	@Override
	public Set<String> nameSet() {
		return headers.names();
	}

}
