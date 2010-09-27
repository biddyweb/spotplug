package com.plugtree.spotplug.impl;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.EventInput;

public class JmsEventInput implements EventInput {

	private Engine engine;
		
	@Override
	public void setEngine(Engine engine) {
		// TODO Auto-generated method stub
		this.engine = engine;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
