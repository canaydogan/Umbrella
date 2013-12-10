package net.canaydogan.umbrella;

public interface HttpHeaderCollection {

	public HttpHeaderCollection set(String name, Object value);
	
	public Object get(String name);
	
}
