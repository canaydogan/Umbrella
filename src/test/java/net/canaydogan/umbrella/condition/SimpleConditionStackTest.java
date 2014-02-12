package net.canaydogan.umbrella.condition;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.condition.Condition;
import net.canaydogan.umbrella.condition.ConditionStack;
import net.canaydogan.umbrella.condition.SimpleConditionStack;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;

import org.junit.Test;

public class SimpleConditionStackTest extends AbstractCondition {
	
	protected Condition newCondition(HttpHandlerContext context, boolean status) {
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(status);
		
		return condition;
	}
	
	@Test
	public void testAddAndRemoveCondition() throws Exception {
		ConditionStack stack = new SimpleConditionStack();
		Set<Condition> data = (Set<Condition>) stack.getClass().getDeclaredField("stack").get(stack);
		
		stack.addCondition(null);
		assertEquals(1, data.size());
		
		stack.removeCondition(null);
		assertEquals(0, data.size());
	}

	@Override
	public Map<Condition, HttpHandlerContext> createValidData() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();
		HttpHandlerContext context = new DefaultHttpHandlerContext(null, null);
		
		//With no condition
		ConditionStack stack1 = new SimpleConditionStack();			
		
		//With one valid condition
		ConditionStack stack2 = new SimpleConditionStack();			
		stack2.addCondition(newCondition(context, true));
		
		data.put(stack1, context);
		data.put(stack2, context);
		
		return data;
	}

	@Override
	public Map<Condition, HttpHandlerContext> createInvalidData() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();
		HttpHandlerContext context = new DefaultHttpHandlerContext(null, null);
		
		//Stack with one invalid condition
		ConditionStack stack1 = new SimpleConditionStack();
		stack1.addCondition(newCondition(context, false));
		
		//Stack with one valid and one invalid condition
		ConditionStack stack2 = new SimpleConditionStack();			
		stack2.addCondition(newCondition(context, true));
		stack2.addCondition(newCondition(context, false));
		
		data.put(stack1, context);
		data.put(stack2, context);
		
		return data;
	}	
	
	
}
