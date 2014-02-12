package net.canaydogan.umbrella.condition;

import static org.junit.Assert.*;

import java.util.Map;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.condition.Condition;

import org.junit.Test;

abstract public class AbstractCondition {

	abstract public Map<Condition, HttpHandlerContext> createValidData();
	abstract public Map<Condition, HttpHandlerContext> createInvalidData();
	
	@Test
	public void testIsValidForSuccess() {
		Map<Condition, HttpHandlerContext> data = createValidData();
		
		for (Condition condition : data.keySet()) {
			assertTrue(condition.isValid(data.get(condition)));
		}
	}
	
	@Test
	public void testIsValidForUnsuccess() {
		Map<Condition, HttpHandlerContext> data = createInvalidData();
		
		for (Condition condition : data.keySet()) {
			assertFalse(condition.isValid(data.get(condition)));
		}
	}
		
}
