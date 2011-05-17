package com.plugtree.spotplug.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.plugtree.spotplug.Configuration;
import com.plugtree.spotplug.client.EventService;
import com.plugtree.spotplug.impl.EventInputManager;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements EventService {

	public EventServiceImpl() {
		
		//TODO: Mover de aca.
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/core.xml"});
		
		Configuration configuration = (Configuration) context.getBean("Configuration");
		EventInputManager eventInputManager = configuration.getEventInputManager();
		configuration.configure();
		eventInputManager.start();
	}
	
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
