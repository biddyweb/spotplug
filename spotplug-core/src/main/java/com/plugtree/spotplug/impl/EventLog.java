package com.plugtree.spotplug.impl;

public class EventLog {
	
	private String userId;
	private String fraudPattern;
	private int fraudPercentage;
	
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
	
	public void setFraudPercentage(int fraudPercentage) {
		this.fraudPercentage = fraudPercentage;
	}
	
	public int getFraudPercentage() {
		return fraudPercentage;
	}
}
