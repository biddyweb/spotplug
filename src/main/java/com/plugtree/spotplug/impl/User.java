package com.plugtree.spotplug.impl;

public class User {

	private int id;
	
	private int fraudProbabily;
	
	public User(int id){
		this.id = id;
		this.fraudProbabily = 0;
	}

	public void increaseFraudProbabily(int fraudProbabily) {
		this.fraudProbabily += fraudProbabily;
	}
	
	public int getId() {
		return id;
	}
	
	public int getFraudProbabily() {
		return fraudProbabily;
	}
}
