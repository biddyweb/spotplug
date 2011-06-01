package com.plugtree.spotplug.impl;

import org.drools.WorkingMemory;
import org.drools.event.AfterActivationFiredEvent;
import org.drools.event.DefaultAgendaEventListener;
import org.drools.runtime.rule.Activation;

public class ActivatedRuleListener extends DefaultAgendaEventListener {

@Override
public void afterActivationFired(AfterActivationFiredEvent event, WorkingMemory workingMemory) {
    super.afterActivationFired(event, workingMemory);
    Activation activation = event.getActivation();
    activation.getRule();
}

}


