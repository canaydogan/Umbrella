package net.canaydogan.umbrella.handler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.HttpRequest.Method;
import net.canaydogan.umbrella.handler.exception.ConfigurationException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.restful.Resource;
import net.canaydogan.umbrella.router.Route;
import net.canaydogan.umbrella.router.RouteMatch;

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
		
		handler = new RestfulHandler(route, null);		
	}
	
	public HttpHandlerContext newContext(Method method) {
		//Request
		HttpRequest request = mock(HttpRequest.class);
		when(request.getMethod()).thenReturn(method);
		
		//Response
		HttpResponse response = mock(HttpResponse.class);
		
		//Context
		HttpHandlerContext context = new HttpHandlerContext(request, response);
		
		return context;
	}
	
	@Test
	public void testCreation() {
		assertEquals("id", handler.getIdentifierName());
	}
	
	@Test
	public void testSetterAndGetter() {
		Route route = mock(Route.class);
		Resource resource = mock(Resource.class);
		
		handler.setIdentifierName("new name");
		handler.setRoute(route);
		handler.setResource(resource);
		
		assertEquals("new name", handler.getIdentifierName());
		assertSame(route, handler.getRoute());
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
		assertTrue(handler.handleHttpRequest(context));
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
		assertTrue(handler.handleHttpRequest(context));
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
		assertTrue(handler.handleHttpRequest(context));
		verify(resource, times(1)).create(context);
		verify(context.getResponse(), times(1)).setContent("create");
	}
	
	@Test
	public void testHandleHttpRequestForGetList() throws Exception {
		//Context
		HttpHandlerContext context = newContext(Method.GET);
		
		//Resource
		Resource resource = mock(Resource.class);
		when(resource.getList(context)).thenReturn("list");
		
		handler.setResource(resource);		
		assertTrue(handler.handleHttpRequest(context));
		verify(resource, times(1)).getList(context);
		verify(context.getResponse(), times(1)).setContent("list");
	}
	
	@Test
	public void testHandleHttpRequestForGet() throws Exception {
		//Context
		HttpHandlerContext context = newContext(Method.GET);					
		
		//Resource
		Resource resource = mock(Resource.class);
		when(resource.get(context)).thenReturn("get");
		
		//Route
		RouteMatch routeMatch = new RouteMatch();
		routeMatch.setParam("myId", "12");
		Route route = mock(Route.class);		
		when(route.match(context.getRequest())).thenReturn(routeMatch);
		
		handler.setIdentifierName("myId");
		handler.setRoute(route);
		handler.setResource(resource);		
		assertTrue(handler.handleHttpRequest(context));
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
		
		handler.setRoute(null);		
		handler.handleHttpRequest(context);
	}
	
	@Test()
	public void testHandleHttpRequestForNullRouteMatch() throws Exception {
		HttpHandlerContext context = newContext(Method.OPTIONS);
		
		when(handler.getRoute().match(context.getRequest())).thenReturn(null);
		assertTrue(handler.handleHttpRequest(context));
	}
	
}