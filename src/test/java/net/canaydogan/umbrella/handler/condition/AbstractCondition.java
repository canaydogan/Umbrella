package net.canaydogan.umbrella.handler.condition;

import static org.junit.Assert.*;

import java.util.Map;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

import org.junit.Test;

abstract public class AbstractCondition {

	abstract public Map<Condition, HttpHandlerContext> newValidData();
	abstract public Map<Condition, HttpHandlerContext> newInvalidData();
	
	@Test
	public void testIsValidForSuccess() {
		Map<Condition, HttpHandlerContext> data = newValidData();
		
		for (Condition condition : data.keySet()) {
			assertTrue(condition.isValid(data.get(condition)));
		}
	}
	
	@Test
	public void testIsValidForUnsuccess() {
		Map<Condition, HttpHandlerContext> data = newInvalidData();
		
		for (Condition condition : data.keySet()) {
			assertFalse(condition.isValid(data.get(condition)));
		}
	}
		
}
