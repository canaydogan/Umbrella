package net.canaydogan.umbrella.condition;

public interface ConditionStack extends Condition {

	public ConditionStack addCondition(Condition condition);
	public ConditionStack removeCondition(Condition condition);	
	
}