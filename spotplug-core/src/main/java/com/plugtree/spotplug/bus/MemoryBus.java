package com.plugtree.spotplug.bus;

import java.util.LinkedList;
import java.util.List;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.model.GenericEvent;
import com.plugtree.spotplug.model.RuleEvent;

public class MemoryBus implements Bus {

	private List<GenericEvent> eventList = new LinkedList<GenericEvent>();
	private List<RuleEvent> ruleEventList = new LinkedList<RuleEvent>();
	private Engine engine;
	
	@Override
	public void addEvent(GenericEvent event) {
		eventList.add(event);
		engine.processEvent(event);
	}
	
	@Override
	public void addEvent(RuleEvent ruleEvent) {
		getRuleEventList().add(ruleEvent);
	}

	@Override
	public List<GenericEvent> getEvents() {
		return eventList;
	}
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public List<RuleEvent> getRuleEventList() {
		return ruleEventList;
	}
}
