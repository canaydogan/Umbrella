package net.canaydogan.umbrella.wrapper;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	@Test
	public void testGetAll() {
		List<String> list = new ArrayList<>();
		HttpHeaders httpHeaders = mock(HttpHeaders.class);
		HttpHeaderCollection collection = new HttpHeadersWrapper(httpHeaders);
		when(httpHeaders.getAll("name")).thenReturn(list);
		
		assertEquals(list, collection.getAll("name"));
	}
	
	@Test
	public void testAdd() {
		HttpHeaders httpHeaders = mock(HttpHeaders.class);
		HttpHeaderCollection collection = new HttpHeadersWrapper(httpHeaders);
		
		collection.add("name", "value");
		verify(httpHeaders, times(1)).add("name", "value");		
	}
	
	@Test
	public void testContains() {
		HttpHeaders httpHeaders = mock(HttpHeaders.class);		
		HttpHeaderCollection collection = new HttpHeadersWrapper(httpHeaders);
		when(httpHeaders.contains("name")).thenReturn(true);
		
		assertTrue(collection.contains("name"));
	}
	
	@Test
	public void testNameSet() {
		Set<String> nameSet = new HashSet<>();
		HttpHeaders httpHeaders = mock(HttpHeaders.class);		
		HttpHeaderCollection collection = new HttpHeadersWrapper(httpHeaders);
		when(httpHeaders.names()).thenReturn(nameSet);
		
		assertEquals(nameSet, collection.nameSet());
		
		
	}
		
}