package net.canaydogan.umbrella.wrapper;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.FileUploadCollection;
import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpQuery;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.router.RouteMatch;
import net.canaydogan.umbrella.util.DefaultFileUploadCollection;
import net.canaydogan.umbrella.util.DefaultHttpCookieCollection;

public class FullHttpRequestWrapper implements HttpRequest {
	
	protected FullHttpRequest request;
	
	protected String content;
	
	protected HttpHeaderCollection headerCollection;
	
	protected HttpQuery query;
	
	protected HttpCookieCollection cookieCollection;
	
	protected RouteMatch routeMatch;
	
	protected Map<Object, Object> data = new HashMap<>();
	
	protected FileUploadCollection fileUploadCollection;
	
	protected HttpPostRequestDecoder postRequestDecoder;
	
	protected boolean initializedContent = false;
	
	public FullHttpRequestWrapper(FullHttpRequest request) {
		this.request = request;
		headerCollection = new HttpHeadersWrapper(request.headers());
		query = new QueryStringDecoderWrapper(new QueryStringDecoder(getUri()));
		cookieCollection = new DefaultHttpCookieCollection(getHeaderCollection().get(HttpHeaders.Names.COOKIE));
		if (getMethod() == Method.POST || getMethod() == Method.PUT) {
			postRequestDecoder = new HttpPostRequestDecoder(request);
			fileUploadCollection = new DefaultFileUploadCollection(postRequestDecoder.getBodyHttpDatas());
		} else {
			fileUploadCollection = new DefaultFileUploadCollection();
		}
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
		
		return HttpRequest.Method.valueOf(request.getMethod().toString());
	}

	@Override
	public String getContent() {
		if (!initializedContent) {
			initializedContent = true;
			content = request.content().toString(CharsetUtil.UTF_8);
		}
		
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

	@Override
	public FileUploadCollection getFileUploadCollection() {
		return fileUploadCollection;
	}

	@Override
	public void destroy() {
		if (null != postRequestDecoder) {
			postRequestDecoder.destroy();
		}
	}
	
}