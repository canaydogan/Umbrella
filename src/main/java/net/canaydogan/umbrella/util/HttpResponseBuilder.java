package net.canaydogan.umbrella.util;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import net.canaydogan.umbrella.HttpResponse.Status;

public class HttpResponseBuilder {

	public static FullHttpResponse build(FullHttpResponse foundation, net.canaydogan.umbrella.HttpResponse data) {
		build((HttpResponse) foundation, data);

		if (null != data.getContent()) {
			foundation.content().writeBytes(Unpooled.copiedBuffer(data.getContent().toString(), CharsetUtil.UTF_8));	
		}
		
		return foundation;
	}
	
	public static HttpResponse build(HttpResponse foundation, net.canaydogan.umbrella.HttpResponse data) {
		HttpHeadersBuilder.build(foundation.headers(), data.getHeaderCollection());
		HttpHeadersBuilder.build(foundation.headers(), data.getCookieCollection());
		setStatus(foundation, data.getStatus());

		return foundation;
	}
	
	protected static HttpResponse setStatus(HttpResponse response, Status status) {
		response.setStatus(HttpResponseStatus.valueOf(status.getCode()));
		return response;
	}

}
