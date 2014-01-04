package net.canaydogan.umbrella.util;

import static org.junit.Assert.*;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import net.canaydogan.umbrella.HttpResponse.Status;

import org.junit.Test;

public class HttpResponseBuilderTest {

	@Test
	public void testBuildWithHttpResponse() {
		HttpResponse foundation = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		net.canaydogan.umbrella.HttpResponse response = new net.canaydogan.umbrella.util.DefaultHttpResponse();
		
		response.getHeaderCollection().add("name", "value");
		response.getCookieCollection().add(new HttpCookie("name", "value"));
		
		HttpResponseBuilder.build(foundation, response);
		
		assertEquals("value", foundation.headers().get("name"));
		assertEquals(1, foundation.headers().getAll(HttpHeaders.Names.SET_COOKIE).size());		
	}
	
	@Test
	public void testBuildWithFullHttpResponse() {
		FullHttpResponse foundation = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		net.canaydogan.umbrella.HttpResponse response = new net.canaydogan.umbrella.util.DefaultHttpResponse();
		
		response.getHeaderCollection().add("name", "value");
		response.getCookieCollection().add(new HttpCookie("name", "value"));
		response.setContent("content");
		
		HttpResponseBuilder.build(foundation, response);
		
		assertEquals("value", foundation.headers().get("name"));
		assertEquals(1, foundation.headers().getAll(HttpHeaders.Names.SET_COOKIE).size());
		assertEquals(7, foundation.content().readableBytes());
	}
	
	@Test
	public void testBuildWithFullHttpResponseForNullContent() {
		FullHttpResponse foundation = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		net.canaydogan.umbrella.HttpResponse response = new net.canaydogan.umbrella.util.DefaultHttpResponse();
		
		HttpResponseBuilder.build(foundation, response);
	}
	
	@Test
	public void testBuildWithHttpResponseForStatus() {
		HttpResponse foundation = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.GATEWAY_TIMEOUT);
		net.canaydogan.umbrella.HttpResponse response = new net.canaydogan.umbrella.util.DefaultHttpResponse();
		Map<Status, HttpResponseStatus> statusMap = new HashMap<>();
		statusMap.put(Status.OK, HttpResponseStatus.OK);
		statusMap.put(Status.CONTINUE, HttpResponseStatus.CONTINUE);
		statusMap.put(Status.NOT_FOUND, HttpResponseStatus.NOT_FOUND);
		statusMap.put(Status.NOT_MODIFIED, HttpResponseStatus.NOT_MODIFIED);
		statusMap.put(Status.FORBIDDEN, HttpResponseStatus.FORBIDDEN);
		statusMap.put(Status.METHOD_NOT_ALLOWED, HttpResponseStatus.METHOD_NOT_ALLOWED);
		
		for (Entry<Status, HttpResponseStatus> entry : statusMap.entrySet()) {
			response.setStatus(entry.getKey());
			HttpResponseBuilder.build(foundation, response);
			assertEquals(foundation.getStatus(), entry.getValue());
		}
	}
	
}
