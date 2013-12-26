package net.canaydogan.umbrella.util;

import java.util.List;

import io.netty.handler.codec.http.HttpHeaders;
import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.HttpHeaderCollection;

public class HttpHeadersBuilder {

	public static HttpHeaders build(HttpHeaders foundation, HttpHeaderCollection data) {
		for (String name : data.nameSet()) {
			foundation.add(name, data.getAll(name));
		}
		return foundation;
	}

	public static HttpHeaders build(HttpHeaders foundation, HttpCookieCollection data) {
		List<String> cookieList = data.toStringList();
		
		for (String cookie : cookieList) {
			foundation.add(HttpHeaders.Names.SET_COOKIE, cookie);
		}
		
		return foundation;
	}

}
