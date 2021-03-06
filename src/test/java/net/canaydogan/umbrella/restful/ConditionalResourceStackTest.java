package net.canaydogan.umbrella.restful;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.condition.Condition;
import net.canaydogan.umbrella.restful.exception.InvalidRequestException;
import net.canaydogan.umbrella.restful.exception.ResourceNotFoundException;
import net.canaydogan.umbrella.router.RouteMatch;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConditionalResourceStackTest extends AbstractResourceStack {

	protected ConditionalResourceStack stack;
	protected HttpHandlerContext context;
	
	@Before
	public void setUp() {
		stack = new ConditionalResourceStack();				
		context = new DefaultHttpHandlerContext(mock(HttpRequest.class), null);
		when(context.getRequest().getRouteMatch()).thenReturn(new RouteMatch());
	}
	
	@Test
	public void testDefaultValues() {
		assertEquals("resource", stack.getIdentifierName());
	}
	
	@Test
	public void testGetAndGetListAndCreateAndUpdateAndDeleteWithValidCondition() throws Exception {		
		//Condition
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(true);
		
		//Resources
		Resource resource1 = newResource(context, "1");
		Resource resource2 = newResource(context, "2");		
		stack.addResource("1", resource1, condition);
		stack.addResource("2", resource2, condition);
		
		context.getRequest().getRouteMatch().set("resource", "1");
		assertEquals("get 1", stack.get(context));
		assertEquals("get list 1", stack.getList(context));
		assertEquals("create 1", stack.create(context));
		assertEquals("update 1", stack.update(context));
		assertEquals("delete 1", stack.delete(context));
		
		context.getRequest().getRouteMatch().set("resource", "2");
		assertEquals("get 2", stack.get(context));
		assertEquals("get list 2", stack.getList(context));
		assertEquals("create 2", stack.create(context));
		assertEquals("update 2", stack.update(context));
		assertEquals("delete 2", stack.delete(context));
	}
	
	/**
	 * Tests for invalid request 
	 */
	
	@Test(expected = InvalidRequestException.class)
	public void testGetWithInvalidCondition() throws Exception {
		//Condition
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(false);
		
		//Resource
		Resource resource1 = newResource(context, "1");		
		stack.addResource("1", resource1, condition);
		
		context.getRequest().getRouteMatch().set("resource", "1");
		stack.get(context);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testGetListWithInvalidCondition() throws Exception {
		//Condition
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(false);
		
		//Resource
		Resource resource1 = newResource(context, "1");		
		stack.addResource("1", resource1, condition);
		
		context.getRequest().getRouteMatch().set("resource", "1");
		stack.getList(context);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testCreateWithInvalidCondition() throws Exception {
		//Condition
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(false);
		
		//Resource
		Resource resource1 = newResource(context, "1");		
		stack.addResource("1", resource1, condition);
		
		context.getRequest().getRouteMatch().set("resource", "1");
		stack.create(context);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testUpdateWithInvalidCondition() throws Exception {
		//Condition
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(false);
		
		//Resource
		Resource resource1 = newResource(context, "1");		
		stack.addResource("1", resource1, condition);
		
		context.getRequest().getRouteMatch().set("resource", "1");
		stack.update(context);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testDeleteWithInvalidCondition() throws Exception {
		//Condition
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(false);
		
		//Resource
		Resource resource1 = newResource(context, "1");		
		stack.addResource("1", resource1, condition);
		
		context.getRequest().getRouteMatch().set("resource", "1");
		stack.delete(context);
	}
	
	/**
	 * Tests for resource not found
	 */
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGetWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().set("resource", "undefined");
		stack.get(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGeListtWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().set("resource", "undefined");
		stack.getList(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testCreateWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().set("resource", "undefined");
		stack.create(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().set("resource", "undefined");
		stack.update(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteWithUndefinedResourceName() throws Exception {
		context.getRequest().getRouteMatch().set("resource", "undefined");
		stack.delete(context);
	}
	
}