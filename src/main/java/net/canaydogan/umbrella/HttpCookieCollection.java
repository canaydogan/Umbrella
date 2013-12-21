package net.canaydogan.umbrella;

import java.net.HttpCookie;
import java.util.List;

public interface HttpCookieCollection {

	public HttpCookie getCookie(String name);
	
	public HttpCookieCollection addCookie(HttpCookie cookie);
	
	public boolean removeCookie(HttpCookie cookie);
	
	public List<String> toStringList();
	
}
