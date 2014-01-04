package net.canaydogan.umbrella.condition;

import net.canaydogan.umbrella.HttpHandlerContext;

public class AlwaysValidCondition implements Condition {

	@Override
	public boolean isValid(HttpHandlerContext context) {
		return true;
	}

}
