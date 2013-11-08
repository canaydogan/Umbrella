package net.canaydogan.umbrella.handler.condition;

import java.util.LinkedHashSet;
import java.util.Set;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

public class SimpleConditionStack implements ConditionStack {

	protected Set<Condition> stack = new LinkedHashSet<>();
	
	@Override
	public boolean isValid(HttpHandlerContext context) {
		for (Condition condition : stack) {
			if (false == condition.isValid(context)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public ConditionStack addCondition(Condition condition) {
		stack.add(condition);		
		return this;
	}

	@Override
	public ConditionStack removeCondition(Condition condition) {
		stack.remove(condition);
		return this;
	}

}
