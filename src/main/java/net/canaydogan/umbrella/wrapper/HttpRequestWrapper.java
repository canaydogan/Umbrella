package net.canaydogan.umbrella.wrapper;

import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpQuery;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.router.RouteMatch;
import net.canaydogan.umbrella.util.DefaultHttpCookieCollection;

public class HttpRequestWrapper implements HttpRequest {
	
	protected io.netty.handler.codec.http.HttpRequest request;
	
	protected String content;
	
	protected HttpHeaderCollection headerCollection;
	
	protected HttpQuery query;
	
	protected HttpCookieCollection cookieCollection;
	
	protected RouteMatch routeMatch;
	
	protected Map<Object, Object> data = new HashMap<>();
	
	public HttpRequestWrapper(io.netty.handler.codec.http.HttpRequest request) {
		this.request = request;
		this.headerCollection = new HttpHeadersWrapper(request.headers());
		this.query = new QueryStringDecoderWrapper(new QueryStringDecoder(getUri()));
		this.cookieCollection = new DefaultHttpCookieCollection(getHeaderCollection().get(HttpHeaders.Names.COOKIE));
	}

	@Override
	public String getUri() {
		return request.getUri();
	}

	@Override
	public Method getMethod() {
		if (null == request.getMethod()) {
			return null;
		}
		String methodAsString = request.getMethod().toString();
		
		for (HttpRequest.Method method : HttpRequest.Method.values()) {
			if (method.toString() == methodAsString) {
				return method;
			}
		}		
		
		return null;
	}

	@Override
	public HttpRequest setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public String getContent() {
		return content;
	}
	
	@Override
	public HttpHeaderCollection getHeaderCollection() {
		return headerCollection;
	}

	@Override
	public HttpQuery getQuery() {
		return query;
	}

	@Override
	public HttpCookieCollection getCookieCollection() {
		return cookieCollection;
	}

	@Override
	public boolean isSuccess() {
		return request.getDecoderResult().isSuccess();
	}

	@Override
	public RouteMatch getRouteMatch() {
		return routeMatch;
	}

	@Override
	public void setRouteMatch(RouteMatch routeMatch) {
		this.routeMatch = routeMatch;
	}

	@Override
	public boolean isKeepAlive() {
		return HttpHeaders.isKeepAlive(request);
	}

	@Override
	public Map<Object, Object> getData() {
		return data;
	}

	@Override
	public String getUriWithoutQuery() {
		String uri = getUri();		
		if (null != uri && uri.indexOf('?') > 0) {
			return uri.substring(0, uri.indexOf('?'));	
		}
		
		return uri;
	}	

}