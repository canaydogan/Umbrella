package net.canaydogan.umbrella.wrapper;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import io.netty.handler.codec.http.HttpHeaders;

import org.junit.Test;

import net.canaydogan.umbrella.HttpHeaderCollection;

public class HttpHeadersWrapperTest {

	@Test
	public void testSet() {
		HttpHeaders httpHeaders = mock(HttpHeaders.class);
		HttpHeaderCollection collection = new HttpHeadersWrapper(httpHeaders);
		
		collection.set("name", "value");
		verify(httpHeaders, times(1)).set("name", "value");		
	}
	
	@Test
	public void testGet() {
		HttpHeaders httpHeaders = mock(HttpHeaders.class);
		HttpHeaderCollection collection = new HttpHeadersWrapper(httpHeaders);
		when(httpHeaders.get("name")).thenReturn("value");
		
		assertEquals("value", collection.get("name"));
	}
		
}
