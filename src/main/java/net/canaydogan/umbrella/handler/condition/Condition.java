package net.canaydogan.umbrella.handler.condition;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

public interface Condition {

	public boolean isValid(HttpHandlerContext context);
	
}