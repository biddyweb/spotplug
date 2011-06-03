package com.plugtree.spotplug.impl;

import java.util.List;

import org.drools.definition.rule.Rule;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.FactHandle;

import com.plugtree.spotplug.bus.Bus;
import com.plugtree.spotplug.model.RuleEvent;

public class ActivatedRuleListener extends DefaultAgendaEventListener {

	private Bus bus;
	
	@Override
	public void afterActivationFired(AfterActivationFiredEvent event) {
		
		super.afterActivationFired(event);
		
		Activation activation = event.getActivation();
		
		Rule ruleActivated = activation.getRule();
		String ruleName = ruleActivated.getName();
			
		if (ruleName.equals("Convert GenericEvent into a bank-event")) {
			return;
		}

		List<? extends FactHandle> factHandlerList = activation.getFactHandles();
		
		if (factHandlerList.size() == 1) {

			User user = (User) event.getKnowledgeRuntime().getObject(factHandlerList.get(0));
			
			RuleEvent ruleEvent = new RuleEvent(ruleName, user);
			
			bus.addEvent(ruleEvent);
		}
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}
}


