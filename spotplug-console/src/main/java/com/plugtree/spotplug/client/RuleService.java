package com.plugtree.spotplug.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

public interface RuleService extends RemoteService {

	/**
	 * @param eventName
	 * @return the activations per hour.
	 */
	public List<Long> getRuleHistory(String ruleName);

	/**
	 * @param date
	 * @return the rules activated given an specific date.
	 */
	public List<VisualRule> getRules(Date date);
	
	/**
	 * @return the ruleEvents name.
	 */
	public List<String> getRuleNames();
	
	/**
	 * @return the events that activated certain rule.
	 */
	public List<VisualEvent> getRuleActivations(VisualRule visualRule);
}
