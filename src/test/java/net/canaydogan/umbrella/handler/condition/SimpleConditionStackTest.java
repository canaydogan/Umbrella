package net.canaydogan.umbrella.handler.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

import org.junit.Test;

public class SimpleConditionStackTest extends AbstractCondition {

	@Override
	public Map<Condition, HttpHandlerContext> newValidData() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();
		HttpHandlerContext context = new HttpHandlerContext(null, null);
		
		//With no condition
		ConditionStack stack1 = new SimpleConditionStack();			
		
		//With one valid condition
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(true);
		ConditionStack stack2 = new SimpleConditionStack();			
		stack2.addCondition(condition);
		
		data.put(stack1, context);
		data.put(stack2, context);
		
		return data;
	}

	@Override
	public Map<Condition, HttpHandlerContext> newInvalidData() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();
		HttpHandlerContext context = new HttpHandlerContext(null, null);
		//Valid Condition
		Condition validCondition = mock(Condition.class);
		when(validCondition.isValid(context)).thenReturn(true);
		
		//Invalid Condition
		Condition invalidCondition = mock(Condition.class);
		when(invalidCondition.isValid(context)).thenReturn(false);
		
		//Stack with one invalid condition
		ConditionStack stack1 = new SimpleConditionStack();
		stack1.addCondition(invalidCondition);
		
		//Stack with one valid and one invalid condition
		ConditionStack stack2 = new SimpleConditionStack();			
		stack2.addCondition(validCondition);
		stack2.addCondition(invalidCondition);
		
		data.put(stack1, context);
		data.put(stack2, context);
		
		return data;
	}

	@Test
	public void testRemove() {		
		HttpHandlerContext context = new HttpHandlerContext(null, null);
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(false);
		
		ConditionStack stack = new SimpleConditionStack();
		stack.addCondition(condition);
		
		assertFalse(stack.isValid(context));		
		stack.removeCondition(condition);
		assertTrue(stack.isValid(context));
	}
	
	
}
