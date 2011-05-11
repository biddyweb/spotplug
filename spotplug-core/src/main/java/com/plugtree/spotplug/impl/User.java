package com.plugtree.spotplug.impl;

public class User {

	private String id;
	
	private int fraudProbability;
	
	public User(String id){
		this.id = id;
		this.fraudProbability = 0;
	}

	public void increaseFraudProbability(int fraudProbability) {
		this.fraudProbability += fraudProbability;
	}
	
	public String getId() {
		return id;
	}
	
	public int getFraudProbability() {
		return fraudProbability;
	}
}
