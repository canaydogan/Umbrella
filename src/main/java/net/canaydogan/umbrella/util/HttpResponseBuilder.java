package net.canaydogan.umbrella.util;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import net.canaydogan.umbrella.HttpResponse.Status;

public class HttpResponseBuilder {

	public static FullHttpResponse createFullHttpResponse(
			net.canaydogan.umbrella.HttpResponse data) {
		ByteBuf buf;
		if (null != data.getContent()) {
			buf = Unpooled.copiedBuffer(data.getContent().toString(),
					CharsetUtil.UTF_8);
			data.setContent(null);
		} else {
			buf = Unpooled.buffer(0);
		}

		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
				buf);
		build(response, data);

		return response;
	}

	public static io.netty.handler.codec.http.DefaultHttpResponse createDefaultHttpResponse(
			net.canaydogan.umbrella.HttpResponse data) {
		DefaultHttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
		build(response, data);
		
		return response;
	}

	public static FullHttpResponse build(FullHttpResponse foundation,
			net.canaydogan.umbrella.HttpResponse data) {
		build((HttpResponse) foundation, data);

		return foundation;
	}

	public static HttpResponse build(HttpResponse foundation,
			net.canaydogan.umbrella.HttpResponse data) {
		HttpHeadersBuilder.build(foundation.headers(),
				data.getHeaderCollection());
		HttpHeadersBuilder.build(foundation.headers(),
				data.getCookieCollection());
		setStatus(foundation, data.getStatus());

		return foundation;
	}

	protected static HttpResponse setStatus(HttpResponse response, Status status) {
		response.setStatus(HttpResponseStatus.valueOf(status.getCode()));
		return response;
	}

}