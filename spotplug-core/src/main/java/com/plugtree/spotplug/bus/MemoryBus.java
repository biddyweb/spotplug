package com.plugtree.spotplug.bus;

import java.util.LinkedList;
import java.util.List;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.model.GenericEvent;

public class MemoryBus implements Bus {

	private List<GenericEvent> eventList = new LinkedList<GenericEvent>();
	private Engine engine;
	
	@Override
	public void addEvent(GenericEvent event) {
		eventList.add(event);
		engine.processEvent(event);
	}

	@Override
	public List<GenericEvent> getEvents() {
		return eventList;
	}
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
