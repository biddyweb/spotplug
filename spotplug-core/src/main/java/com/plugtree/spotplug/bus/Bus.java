package com.plugtree.spotplug.bus;

import java.util.List;

import com.plugtree.spotplug.model.GenericEvent;
import com.plugtree.spotplug.model.RuleEvent;

public interface Bus {

	public void addEvent(GenericEvent event);
	
	public void addEvent(RuleEvent event);
	
	public List<GenericEvent> getEvents();

    public List<RuleEvent> getRuleEventList();
}
