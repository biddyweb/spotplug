package com.plugtree.spotplug.bus;

import java.util.List;

import com.plugtree.spotplug.model.GenericEvent;

public interface Bus {

	public void addEvent(GenericEvent event);
	
	public List<GenericEvent> getEvents();
}
