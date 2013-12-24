package net.canaydogan.umbrella;

import java.util.List;
import java.util.Set;

public interface HttpHeaderCollection {

	public HttpHeaderCollection set(String name, String value);
	
	public String get(String name);
	
	public List<String> getAll(String name);
	
	public HttpHeaderCollection add(String name, String value);
	
	public boolean contains(String name);
	
	public Set<String> nameSet();
	
}
