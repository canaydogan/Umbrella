package net.canaydogan.umbrella.restful;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.restful.exception.ResourceNotFoundException;
import net.canaydogan.umbrella.router.RouteMatch;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;

import org.junit.Before;
import org.junit.Test;

public class SimpleResourceStackTest extends AbstractResourceStack {

	private SimpleResourceStack stack;
	
	private HttpHandlerContext context;
	
	@Before
	public void setUp() {
		stack = new SimpleResourceStack();
		context = new DefaultHttpHandlerContext(mock(HttpRequest.class), null);
		when(context.getRequest().getRouteMatch()).thenReturn(new RouteMatch());
	}
	
	@Test
	public void testCreation() {
		assertEquals("resource", stack.getIdentifierName());
	}
	
	@Test
	public void testSetterAndGetter() {
		stack.setIdentifierName("new controller name");
		
		assertEquals("new controller name", stack.getIdentifierName());
	}
	
	@Test
	public void testGetAndGetListAndCreateAndUpdateAndDeleteWithDefinedResource() throws Exception {
		Resource resource1 = newResource(context, "1");
		Resource resource2 = newResource(context, "2");
		
		stack.addResource("1", resource1);
		stack.addResource("2", resource2);
		
		context.getRequest().getRouteMatch().setParam("resource", "1");
		assertEquals("get 1", stack.get(context));
		assertEquals("get list 1", stack.getList(context));
		assertEquals("create 1", stack.create(context));
		assertEquals("update 1", stack.update(context));
		assertEquals("delete 1", stack.delete(context));
		
		context.getRequest().getRouteMatch().setParam("resource", "2");
		assertEquals("get 2", stack.get(context));
		assertEquals("get list 2", stack.getList(context));
		assertEquals("create 2", stack.create(context));
		assertEquals("update 2", stack.update(context));
		assertEquals("delete 2", stack.delete(context));
	}
	
	/**
	 * Tests for resource not found
	 */
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGetWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().setParam("controlller", "undefined");
		stack.get(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGeListtWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().setParam("controlller", "undefined");
		stack.getList(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testCreateWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().setParam("controlller", "undefined");
		stack.create(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().setParam("controlller", "undefined");
		stack.update(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().setParam("controlller", "undefined");
		stack.delete(context);
	}
	
}