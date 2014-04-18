package net.canaydogan.umbrella;

import java.net.HttpCookie;
import java.util.List;

public interface HttpCookieCollection {

	public HttpCookie get(String name);
	
	public HttpCookieCollection add(HttpCookie cookie);
	
	public boolean remove(HttpCookie cookie);
	
	public List<String> toStringList();
	
	public boolean contains(String name);
	
}
