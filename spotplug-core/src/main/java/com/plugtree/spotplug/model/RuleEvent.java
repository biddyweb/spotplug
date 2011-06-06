package com.plugtree.spotplug.model;

import com.plugtree.spotplug.Event;
import com.plugtree.spotplug.impl.User;

import java.util.Date;

public class RuleEvent implements Event {
	
	private String ruleName;
	private User user;
    private Date callDateTime;

	public RuleEvent(String ruleName, User user) {
		this.ruleName = ruleName;
		this.user = user;
        this.callDateTime = new Date();
	}

	public User getUser() {
		return user;
	}

	public String getRuleName() {
		return ruleName;
	}

    public Date getCallDateTime() {
        return callDateTime;
    }

    public void setCallDateTime(Date callDateTime) {
        this.callDateTime = callDateTime;
    }
}
