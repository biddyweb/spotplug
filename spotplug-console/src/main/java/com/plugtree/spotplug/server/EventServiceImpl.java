package com.plugtree.spotplug.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.plugtree.spotplug.client.EventService;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements EventService {

	@Override
	public List<VisualRule> getRules() {
		
		List<VisualRule> rules = new LinkedList<VisualRule>();
		VisualRule rule1 = new VisualRule();
		rule1.setRuleName("Strange Volume Transaction");
		
		VisualRule rule2 = new VisualRule();
		rule2.setRuleName("Incorrect Message Order");
		
		rules.add(rule1);
		rules.add(rule2);
		
		return rules;
	}

	@Override
	public List<Long> getRuleActivations(String ruleName) {
		
		List<Long> listLong = new LinkedList<Long>();
		
		for(int i = 0; i < 24; i++) {
			listLong.add(new Long(1));
		}
		
		return listLong;
	}

	@Override
	public List<VisualRule> getRules(Date date) {
		
		List<VisualRule> rules = new LinkedList<VisualRule>();
		VisualRule rule1 = new VisualRule();
		rule1.setRuleName("Strange Volume Transaction");
		
		VisualRule rule2 = new VisualRule();
		rule2.setRuleName("Incorrect Message Order");
		
		rules.add(rule1);
		rules.add(rule2);
		
		return rules;
	}

	@Override
	public List<VisualEvent> getRuleEvents(VisualRule rule) {
		
		List<VisualEvent> events = new LinkedList<VisualEvent>();
		VisualEvent event1 = new VisualEvent();
		event1.setEventName("Event1");
        event1.addAttribute("type","Alert");
		event1.addAttribute("priority","10");

		VisualEvent event2 = new VisualEvent();
	    event2.setEventName("Event2");
        event2.addAttribute("type","Warn");
        event2.addAttribute("priority","5");


		events.add(event1);
		events.add(event2);
		
		return events;
	}

	@Override
	public List<String> getEventNames() {
		
		List<String> eventNames = new LinkedList<String>();
		
		eventNames.add("event1");
		eventNames.add("event2");
		
		return eventNames;
	}
}
