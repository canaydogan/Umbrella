package net.canaydogan.umbrella.handler.condition;

import static org.junit.Assert.*;

import java.util.Collection;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

import org.junit.Test;

abstract public class AbstractCondition {

	abstract public Condition newValidCondition();
	abstract public Condition newInvalidCondition();
	abstract public Collection<HttpHandlerContext> getValidCollection();
	abstract public Collection<HttpHandlerContext> getInvalidCollection();
	
	@Test
	public void testIsValidForSuccess() {
		Condition condition = newValidCondition();
		Collection<HttpHandlerContext> collection = getValidCollection();
		
		for (HttpHandlerContext context : collection) {
			assertTrue(condition.isValid(context));
		}
	}
	
	@Test
	public void testIsValidForUnsuccess() {
		Condition condition = newInvalidCondition();
		Collection<HttpHandlerContext> collection = getInvalidCollection();
		
		for (HttpHandlerContext context : collection) {
			assertFalse(condition.isValid(context));
		}
	}
		
}
