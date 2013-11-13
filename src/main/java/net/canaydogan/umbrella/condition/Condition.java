package net.canaydogan.umbrella.condition;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

public interface Condition {

	public boolean isValid(HttpHandlerContext context);
	
}