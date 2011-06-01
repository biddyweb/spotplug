package com.plugtree.spotplug.actuator;

import java.util.LinkedList;
import java.util.List;

import com.plugtree.spotplug.bus.Bus;
import com.plugtree.spotplug.model.GenericEvent;


public class EventActuator {

	private LinkedList<String> eventNames = new LinkedList<String>();
	private Bus bus;

	public void addEventName(String eventName) {
		if(!eventNames.contains(eventName)) {
			eventNames.add(eventName);
		}
	}

	public List<String> getEventNames(){
		return eventNames;
	}

	public void insertEventInBus(GenericEvent event){
		bus.addEvent(event);
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Bus getBus() {
		return bus;
	}
}
