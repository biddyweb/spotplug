package com.plugtree.spotplug;

import java.util.List;

import com.plugtree.spotplug.Event;

public interface Bus {

	public void addEvent(Event event);
	
	public List<Event> getEvents();
}
