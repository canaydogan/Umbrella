package net.canaydogan.umbrella.wrapper;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.QueryStringDecoder;

import org.junit.Before;
import org.junit.Test;

import net.canaydogan.umbrella.HttpQuery;


public class QueryStringDecoderWrapperTest {

	protected Map<String, List<String>> parameters;
	
	protected HttpQuery query;
	
	@Before
	public void setUp() {
		parameters = new HashMap<>();
		List<String> param1 = new ArrayList<>();
		param1.add("value1");
		
		List<String> param2 = new ArrayList<>();
		param2.add("value1");
		param2.add("value2");
		
		parameters.put("param1", param1);
		parameters.put("param2", param2);
		
		QueryStringDecoder decoder = mock(QueryStringDecoder.class);
		when(decoder.parameters()).thenReturn(parameters);
		query = new QueryStringDecoderWrapper(decoder);
	}
	
	@Test
	public void testHasParam() {
		assertFalse(query.hasParam("undefined"));
		assertTrue(query.hasParam("param1"));
		assertTrue(query.hasParam("param2"));
	}
	
	@Test
	public void testGetParam() {
		assertEquals("value1", query.getParam("param1"));
		assertEquals("value1", query.getParam("param2"));
		assertNull(query.getParam("undefined"));
	}
	
	@Test
	public void testGetParams() {
		assertEquals(1, query.getParamList("param1").size());
		assertEquals(2, query.getParamList("param2").size());
		assertNull(query.getParamList("undefined"));
	}
		
}