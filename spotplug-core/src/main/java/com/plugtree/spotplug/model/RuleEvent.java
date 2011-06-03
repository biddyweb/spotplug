package com.plugtree.spotplug.model;

import com.plugtree.spotplug.Event;
import com.plugtree.spotplug.impl.User;

public class RuleEvent implements Event {
	
	private String ruleName;
	private User user;

	public RuleEvent(String ruleName, User user) {
		this.ruleName = ruleName;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String getRuleName() {
		return ruleName;
	}
}
