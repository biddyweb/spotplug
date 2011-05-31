package com.plugtree.spotplug.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.plugtree.spotplug.client.EventService;
import com.plugtree.spotplug.model.GenericEvent;
import com.plugtree.spotplug.shared.VisualEvent;

@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements EventService {

	SpringContext springContext;
	
	public EventServiceImpl() {
		
		springContext = SpringContext.getInstance();
	}

	@Override
	public List<Long> getEventHistory(String eventName) {
		
		List<GenericEvent> eventList = springContext.getBus().getEvents();
		List<Long> historyList = new LinkedList<Long>();
		
		for(int i = 0; i < 24; i++) historyList.add(new Long(0));
		
		for(GenericEvent event : eventList) {

             if(event.getEventType().equals(eventName)){
			    @SuppressWarnings("deprecation")
                int index = event.getCallDateTime().getHours();//TODO: remove warning.
			    historyList.set(index, historyList.get(index) + 1);
             }
         }
		
		return historyList;
	}

	@Override
	public List<VisualEvent> getEvents(String eventName, Date date) {
		
		List<GenericEvent> eventList = springContext.getBus().getEvents();
		List<VisualEvent> visualEventList = new LinkedList<VisualEvent>();


		    for(GenericEvent event : eventList) {
                if(event.getEventType().equals(eventName) && event.getCallDateTime().getHours() == date.getHours()){
			        VisualEvent visualEvent = new VisualEvent();
			        visualEvent.setTimestamp(event.getCallDateTime().getTime());
			        visualEvent.setEventName(event.getEventType());
                    visualEvent.setAttributesMap(event.getAttributes());
			        visualEventList.add(visualEvent);
                }
		    }

		return visualEventList;
	}

	@Override
	public List<String> getEventNames() {

        List<String> eventNames = new LinkedList<String>();
		       	    
        StatefulKnowledgeSession ksession = springContext.getKnowledgeSession();

        for(WorkingMemoryEntryPoint entryPoint  :  ksession.getWorkingMemoryEntryPoints()){
            
        	String entryPointName = entryPoint.getEntryPointId();
        	
        	if (entryPointName.equals("GenericEventEntryPoint") || entryPointName.equals("DEFAULT")) {
        		continue;
        	}
        	
        	eventNames.add(entryPointName);
        }

        return eventNames;
    }
}
