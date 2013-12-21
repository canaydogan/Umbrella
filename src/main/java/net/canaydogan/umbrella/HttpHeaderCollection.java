package net.canaydogan.umbrella;

import java.util.List;

public interface HttpHeaderCollection {

	public HttpHeaderCollection set(String name, Object value);
	
	public String get(String name);
	
	public List<String> getAll(String name);
	
	public HttpHeaderCollection add(String name, Object value);
	
}
