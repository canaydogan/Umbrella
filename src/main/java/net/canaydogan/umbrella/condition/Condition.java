package net.canaydogan.umbrella.condition;

import net.canaydogan.umbrella.HttpHandlerContext;

public interface Condition {

	public boolean isValid(HttpHandlerContext context);
	
}