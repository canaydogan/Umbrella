package net.canaydogan.umbrella.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpRequest.Method;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.HttpResponse.Status;
import net.canaydogan.umbrella.handler.exception.ConfigurationException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.restful.Resource;
import net.canaydogan.umbrella.router.Route;
import net.canaydogan.umbrella.router.RouteMatch;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;

import org.junit.Before;
import org.junit.Test;

public class RestfulHandlerTest {

	private RestfulHandler handler;
	
	@Before
	public void setUp() {
		//Route
		RouteMatch routeMatch = new RouteMatch();
		Route route = mock(Route.class);		
		when(route.match(any(HttpRequest.class))).thenReturn(routeMatch);
		
		handler = new RestfulHandler(null);		
	}
	
	public HttpHandlerContext newContext(Method method) {
		return newContext(method, new RouteMatch());
	}
	
	public HttpHandlerContext newContext(Method method, RouteMatch routeMatch) {
		//Request
		HttpRequest request = mock(HttpRequest.class);
		when(request.getMethod()).thenReturn(method);
		when(request.getRouteMatch()).thenReturn(routeMatch);
		
		//Response
		HttpResponse response = mock(HttpResponse.class);
		
		//Context
		HttpHandlerContext context = new DefaultHttpHandlerContext(request, response);
		
		return context;
	}
	
	@Test
	public void testCreation() {
		assertEquals("id", handler.getIdentifierName());
	}
	
	@Test
	public void testSetterAndGetter() {
		Resource resource = mock(Resource.class);
		
		handler.setIdentifierName("new name");
		handler.setResource(resource);
		
		assertEquals("new name", handler.getIdentifierName());
		assertSame(resource, handler.getResource());
	}
	
	@Test
	public void testHandleHttpRequestForDelete() throws Exception {
		//Context
		HttpHandlerContext context = newContext(Method.DELETE);
		
		//Resource
		Resource resource = mock(Resource.class);
		when(resource.delete(context)).thenReturn("delete");
		
		handler.setResource(resource);		
		assertFalse(handler.handleHttpRequest(context));
		verify(resource, times(1)).delete(context);
		verify(context.getResponse(), times(1)).setContent("delete");
	}
	
	@Test
	public void testHandleHttpRequestForUpdate() throws Exception {
		//Context
		HttpHandlerContext context = newContext(Method.PUT);
		
		//Resource
		Resource resource = mock(Resource.class);
		when(resource.update(context)).thenReturn("update");
		
		handler.setResource(resource);		
		assertFalse(handler.handleHttpRequest(context));
		verify(resource, times(1)).update(context);
		verify(context.getResponse(), times(1)).setContent("update");
	}
	
	@Test
	public void testHandleHttpRequestForCreate() throws Exception {
		//Context
		HttpHandlerContext context = newContext(Method.POST);
		
		//Resource
		Resource resource = mock(Resource.class);
		when(resource.create(context)).thenReturn("create");
		
		handler.setResource(resource);		
		assertFalse(handler.handleHttpRequest(context));
		verify(resource, times(1)).create(context);
		verify(context.getResponse(), times(1)).setContent("create");
		verify(context.getResponse(), times(1)).setStatus(Status.CREATED);
	}
	
	@Test
	public void testHandleHttpRequestForGetList() throws Exception {
		//Context
		HttpHandlerContext context = newContext(Method.GET);
		
		//Resource
		Resource resource = mock(Resource.class);
		when(resource.getList(context)).thenReturn("list");
		
		handler.setResource(resource);		
		assertFalse(handler.handleHttpRequest(context));
		verify(resource, times(1)).getList(context);
		verify(context.getResponse(), times(1)).setContent("list");
	}
	
	@Test
	public void testHandleHttpRequestForGet() throws Exception {
		//Route match
		RouteMatch routeMatch = new RouteMatch();
		routeMatch.set("myId", "12");
		
		//Context
		HttpHandlerContext context = newContext(Method.GET, routeMatch);					
		
		//Resource
		Resource resource = mock(Resource.class);
		when(resource.get(context)).thenReturn("get");
		
		//Route		
		Route route = mock(Route.class);		
		when(route.match(context.getRequest())).thenReturn(routeMatch);
		
		handler.setIdentifierName("myId");
		handler.setResource(resource);		
		assertFalse(handler.handleHttpRequest(context));
		verify(resource, times(1)).get(context);
		verify(context.getResponse(), times(1)).setContent("get");
	}
	
	@Test(expected = MethodNotAllowedException.class)
	public void testHandleHttpRequestWithUnsupportedMethod() throws Exception {
		HttpHandlerContext context = newContext(Method.OPTIONS);
		
		handler.handleHttpRequest(context);
	}
	
	@Test(expected = ConfigurationException.class)
	public void testHandleHttpRequestWithEmptyRoute() throws Exception {
		HttpHandlerContext context = newContext(Method.GET);
		
		when(context.getRequest().getRouteMatch()).thenReturn(null);		
		handler.handleHttpRequest(context);
	}
	
}