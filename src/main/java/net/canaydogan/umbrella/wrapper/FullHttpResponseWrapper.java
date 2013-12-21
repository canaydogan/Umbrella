package net.canaydogan.umbrella.wrapper;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;
import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpResponse;

public class FullHttpResponseWrapper implements HttpResponse {

	protected FullHttpResponse response;
	
	protected Object content;
	
	protected HttpHeaderCollection headerCollection;
	
	protected HttpCookieCollection cookieCollection = new DefaultHttpCookieCollection();
	
	public FullHttpResponseWrapper(FullHttpResponse response) {
		this.response = response;
		this.headerCollection = new HttpHeadersWrapper(response.headers());
	}
	
	@Override
	public HttpResponse setContent(Object content) {
		this.content = content;
		return this;
	}

	@Override
	public Object getContent() {
		return content;
	}

	@Override
	public HttpHeaderCollection getHeaderCollection() {
		return headerCollection;
	}

	@Override
	public boolean finish() {
		if (null != content) {
			response.content().writeBytes(Unpooled.copiedBuffer(content.toString(), CharsetUtil.UTF_8));	
		}
		
		List<String> cookieList = getCookieCollection().toStringList();
		
		for (String cookie : cookieList) {
			getHeaderCollection().add(HttpHeaders.Names.SET_COOKIE, cookie);
		}
		
		return true;
	}
	
	public FullHttpResponse getNettyResponse() {
		return response;
	}

	@Override
	public HttpCookieCollection getCookieCollection() {
		return cookieCollection;
	}

}
