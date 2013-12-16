package net.canaydogan.umbrella.wrapper;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpResponse;

public class FullHttpResponseWrapper implements HttpResponse {

	protected FullHttpResponse response;
	
	protected Object content;
	
	protected HttpHeaderCollection headerCollection;
	
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
		response.content().writeBytes(Unpooled.copiedBuffer(content.toString(), CharsetUtil.UTF_8));
		return true;
	}
	
	public FullHttpResponse getNettyResponse() {
		return response;
	}

}
