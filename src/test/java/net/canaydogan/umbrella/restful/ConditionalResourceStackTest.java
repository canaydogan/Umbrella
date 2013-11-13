package net.canaydogan.umbrella.restful;

import net.canaydogan.umbrella.condition.Condition;
import net.canaydogan.umbrella.handler.HttpHandlerContext;
import net.canaydogan.umbrella.restful.exception.InvalidRequestException;
import net.canaydogan.umbrella.restful.exception.ResourceNotFoundException;
import net.canaydogan.umbrella.router.RouteMatch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConditionalResourceStackTest {

	private ConditionalResourceStack stack;
	private HttpHandlerContext context;
	
	@Before
	public void setUp() {
		stack = new ConditionalResourceStack();				
		context = new HttpHandlerContext(null, null);
		context.setRouteMatch(new RouteMatch());		
	}
	
	@Test
	public void testDefaultValues() {
		assertEquals("controller", stack.getIdentifierName());
	}
	
	public Resource newResource(HttpHandlerContext context, String output) throws Exception {
		Resource resource = mock(Resource.class);
		when(resource.get(context)).thenReturn("get " + output);
		when(resource.getList(context)).thenReturn("get list " + output);
		when(resource.create(context)).thenReturn("create " + output);
		when(resource.update(context)).thenReturn("update " + output);
		when(resource.delete(context)).thenReturn("delete " + output);
		
		return resource;
	}
	
	@Test
	public void testSetterAndGetter() {
		stack.setIdentifierName("new name");
		
		assertEquals("new name", stack.getIdentifierName());
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
		
		context.getRouteMatch().setParam("controller", "1");
		assertEquals("get 1", stack.get(context));
		assertEquals("get list 1", stack.getList(context));
		assertEquals("create 1", stack.create(context));
		assertEquals("update 1", stack.update(context));
		assertEquals("delete 1", stack.delete(context));
		
		context.getRouteMatch().setParam("controller", "2");
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
		
		context.getRouteMatch().setParam("controller", "1");
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
		
		context.getRouteMatch().setParam("controller", "1");
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
		
		context.getRouteMatch().setParam("controller", "1");
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
		
		context.getRouteMatch().setParam("controller", "1");
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
		
		context.getRouteMatch().setParam("controller", "1");
		stack.delete(context);
	}
	
	/**
	 * Tests for resource not found
	 */
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGetWithUndefinedResourceName() throws Exception {
		context.getRouteMatch().setParam("controlller", "undefined");
		stack.get(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGeListtWithUndefinedResourceName() throws Exception {
		context.getRouteMatch().setParam("controlller", "undefined");
		stack.getList(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testCreateWithUndefinedResourceName() throws Exception {
		context.getRouteMatch().setParam("controlller", "undefined");
		stack.create(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateWithUndefinedResourceName() throws Exception {
		context.getRouteMatch().setParam("controlller", "undefined");
		stack.update(context);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteWithUndefinedResourceName() throws Exception {
		context.getRouteMatch().setParam("controlller", "undefined");
		stack.delete(context);
	}
	
	
}