package com.plugtree.spotplug.impl;

import java.util.List;

import org.drools.definition.rule.Rule;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.FactHandle;

public class ActivatedRuleListener extends DefaultAgendaEventListener {

	@Override
	public void afterActivationFired(AfterActivationFiredEvent event) {
		super.afterActivationFired(event);
		
		Activation activation = event.getActivation();
		
		Rule ruleActivated = activation.getRule();
		String ruleName = ruleActivated.getName();
		
		List<? extends FactHandle> factHandleList = activation.getFactHandles();
		
		//TODO : Do something with the ruleName and factHandleList
	}
}


