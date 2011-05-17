package com.plugtree.spotplug.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.plugtree.spotplug.shared.VisualEvent;

public interface EventService extends RemoteService {

	/**
	 * @param eventName
	 * @return the activations per hour.
	 */
	public List<Long> getEventHistory(String eventName);

	/**
	 * @param date
	 * @return the events given an specific date.
	 */
	public List<VisualEvent> getEvents(Date date);
	
	/**
	 * @return the visual events name.
	 */
	public List<String> getEventNames();
}
