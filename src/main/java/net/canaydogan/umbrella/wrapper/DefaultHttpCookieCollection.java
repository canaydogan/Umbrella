package net.canaydogan.umbrella.wrapper;

import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.ServerCookieEncoder;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.canaydogan.umbrella.HttpCookieCollection;

public class DefaultHttpCookieCollection implements HttpCookieCollection {

	protected List<HttpCookie> list = new ArrayList<>();
	
	public DefaultHttpCookieCollection() {}
	
	public DefaultHttpCookieCollection(String cookie) {
		list.addAll(toHttpCookie(cookie));
	}

	@Override
	public HttpCookie getCookie(String name) {
		for (HttpCookie cookie : list) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		
		return null;
	}

	@Override
	public HttpCookieCollection addCookie(HttpCookie cookie) {
		list.add(cookie);
		return this;
	}

	@Override
	public boolean removeCookie(HttpCookie cookie) {
		return list.remove(cookie);
	}
	
	@Override
	public List<String> toStringList() {
		return ServerCookieEncoder.encode(toNettyCookieSet(list));
	}
	
	protected List<HttpCookie> toHttpCookie(String cookie) {
		List<HttpCookie> list = new ArrayList<>();
		
		if (null == cookie || cookie.equals("")) {
			return list;
		}
		
		Set<Cookie> nettyCookieSet = CookieDecoder.decode(cookie);
		
		for (Cookie nettyCookie : nettyCookieSet) {
			HttpCookie httpCookie = new HttpCookie(nettyCookie.getName(), nettyCookie.getValue());
			list.add(httpCookie);
		}
		
		return list;
	}
	
	protected Set<Cookie> toNettyCookieSet(List<HttpCookie> cookieList) {
		Set<Cookie> nettyCookieSet = new LinkedHashSet<>();
		
		for (HttpCookie cookie : cookieList) {
			Cookie nettyCookie = new DefaultCookie(cookie.getName(), cookie.getValue());
			nettyCookie.setDomain(cookie.getDomain());
			nettyCookie.setHttpOnly(cookie.isHttpOnly());
			nettyCookie.setMaxAge(cookie.getMaxAge());
			nettyCookie.setPath(cookie.getPath());
			nettyCookie.setSecure(cookie.getSecure());
			nettyCookie.setVersion(cookie.getVersion());
			nettyCookieSet.add(nettyCookie);
		}
		
		return nettyCookieSet;
	}
	
}