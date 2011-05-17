package com.plugtree.spotplug.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.plugtree.spotplug.Configuration;
import com.plugtree.spotplug.bus.Bus;
import com.plugtree.spotplug.client.EventService;
import com.plugtree.spotplug.impl.EventInputManager;
import com.plugtree.spotplug.model.GenericEvent;
import com.plugtree.spotplug.shared.VisualEvent;

@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements EventService {

	private Bus bus;
	
	public EventServiceImpl() {
		
		//TODO: Mover de aca.
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/core.xml"});
		
		Configuration configuration = (Configuration) context.getBean("Configuration");
		bus = (Bus) context.getBean("Bus");
		
		EventInputManager eventInputManager = configuration.getEventInputManager();
		configuration.configure();
		eventInputManager.start();
	}

	@Override
	public List<Long> getEventHistory(String eventName) {
		
		List<GenericEvent> eventList = bus.getEvents();
		List<Long> historyList = new LinkedList<Long>();
		
		for(int i = 0; i < 24; i++) historyList.add(new Long(0));
		
		//TODO: Filter by type event, right now we only have one type.
		for(GenericEvent event : eventList) {
			@SuppressWarnings("deprecation")
			int index = event.getCallDateTime().getHours();//TODO: remove warning.
			historyList.set(index, historyList.get(index) + 1);
		}
		
		return historyList;
	}

	@Override
	public List<VisualEvent> getEvents(Date date) {
		
		List<GenericEvent> eventList = bus.getEvents();
		List<VisualEvent> visualEventList = new LinkedList<VisualEvent>();
		
		for(GenericEvent event : eventList) {
			
			VisualEvent visualEvent = new VisualEvent();
			visualEvent.setTimestamp(event.getCallDateTime().getTime());
			visualEvent.setEventName("Generic Event");
			
			visualEventList.add(visualEvent);
		}
		
		return visualEventList;
	}

	@Override
	public List<String> getEventNames() {
		
		List<String> eventNames = new LinkedList<String>();
		eventNames.add("Generic Event");
		
		return eventNames;
	}
}
