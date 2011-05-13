package com.plugtree.spotplug.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

public interface EventService extends RemoteService {

	/**
	 * @return all the rules.
	 */
	public List<VisualRule> getRules();

	/**
	 * @param ruleName
	 * @return the activations per hour.
	 */
	public List<Long> getRuleActivations(String ruleName);

	/**
	 * @param date
	 * @return the rules activated given an specific date.
	 */
	public List<VisualRule> getRules(Date date);

	/**
	 * 
	 * @param rule
	 * @return the events that activated the rule.
	 */
	public List<VisualEvent> getRuleEvents(VisualRule rule);
}
