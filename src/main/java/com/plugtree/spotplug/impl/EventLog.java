package com.plugtree.spotplug.impl;

public class EventLog {
	private String userId;
	private String fraudPattern;
	private String fraudPercentage;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setFraudPattern(String fraudPattern) {
		this.fraudPattern = fraudPattern;
	}
	public String getFraudPattern() {
		return fraudPattern;
	}
	public void setFraudPercentage(String fraudPercentage) {
		this.fraudPercentage = fraudPercentage;
	}
	public String getFraudPercentage() {
		return fraudPercentage;
	}
}
