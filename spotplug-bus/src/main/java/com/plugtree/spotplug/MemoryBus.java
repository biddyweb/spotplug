package com.plugtree.spotplug;

import java.util.LinkedList;
import java.util.List;

import com.plugtree.spotplug.Event;

public class MemoryBus implements Bus {

	private List<Event> eventList = new LinkedList<Event>();
	
	@Override
	public void addEvent(Event event) {
		eventList.add(event);
	}

	@Override
	public List<Event> getEvents() {
		return eventList;
	}
}
