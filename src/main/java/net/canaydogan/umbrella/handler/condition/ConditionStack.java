package net.canaydogan.umbrella.handler.condition;

public interface ConditionStack extends Condition {

	public ConditionStack addCondition(Condition condition);
	public ConditionStack removeCondition(Condition condition);	
	
}