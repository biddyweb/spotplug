package com.plugtree.spotplug.impl;

import java.util.LinkedList;
import java.util.List;

import org.drools.definition.rule.Rule;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.FactHandle;

public class ActivatedRuleListener extends DefaultAgendaEventListener {

	private List<String> ruleNameList = new LinkedList<String>();
	
	@Override
	public void afterActivationFired(AfterActivationFiredEvent event) {
		
		super.afterActivationFired(event);
		
		Activation activation = event.getActivation();
		
		Rule ruleActivated = activation.getRule();
		String ruleName = ruleActivated.getName();
		
		List<? extends FactHandle> factHandleList = activation.getFactHandles();
		
		if (ruleName.equals("Convert GenericEvent into a bank-event")) {
			return;
		}																																												
		
		ruleNameList.add(ruleName);
		System.out.println("Rule name: " + ruleName);
		
		// TODO : No meter varios con el mismo nombre
		// "Convert GenericEvent into a bank-event" filtrar
		System.out.println("Fact size: " + factHandleList.size());
		
		for(FactHandle factHander : factHandleList) {
			System.out.println(factHander.toString());
			User user = (User)event.getKnowledgeRuntime().getObject(factHander);
			System.out.println(user.getId());
		}
	}
}


