package net.canaydogan.umbrella.condition;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlwaysValidConditionTest {

	protected Condition condition;
	
	@Before
	public void setUp() {
		condition = new AlwaysValidCondition();
	}
	
	@Test
	public void testIsValid() {
		assertTrue(condition.isValid(null));
	}

}
