package com.plugtree.spotplug.impl;

import java.util.LinkedList;

import org.drools.runtime.rule.RuleContext;

import com.plugtree.spotplug.Actuator;

public class LogActuator implements Actuator {

	private LinkedList<EventLog> eventLogList = new LinkedList<EventLog>();
	
	@Override
	public void writeOutput(RuleContext ruleContext, User user) {
		
		EventLog eventLog = new EventLog();
		
		eventLog.setFraudPattern(ruleContext.getRule().getName());
		eventLog.setFraudPercentage(user.getFraudProbability());
		eventLog.setUserId(user.getId());
		
		eventLogList.add(eventLog);
	}
}
