package com.plugtree.spotplug.impl;

public class User {

	private String id;
	
	private int fraudProbabily;
	
	public User(String id){
		this.id = id;
		this.fraudProbabily = 0;
	}

	public void increaseFraudProbabily(int fraudProbabily) {
		this.fraudProbabily += fraudProbabily;
	}
	
	public String getId() {
		return id;
	}
	
	public int getFraudProbabily() {
		return fraudProbabily;
	}
}
