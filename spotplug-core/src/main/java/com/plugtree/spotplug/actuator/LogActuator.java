package com.plugtree.spotplug.actuator;

import java.util.LinkedList;

import com.plugtree.spotplug.impl.EventLog;
import com.plugtree.spotplug.impl.User;
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

	public LinkedList<EventLog> getEventLogList() {
		return eventLogList;
	}
}
