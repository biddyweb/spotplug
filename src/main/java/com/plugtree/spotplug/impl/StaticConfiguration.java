/**
 * Copyright 2010 Plugtree LLC 
Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License. 
 */
 
package com.plugtree.spotplug.impl;

import com.plugtree.spotplug.Configuration;
import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.EventInput;

public class StaticConfiguration implements Configuration {

	private Engine engine;
	private EventInputManager eventInputManager;
	private EventInput fileEventInput;
	//private EventInput jmsEventInput;
	
	public StaticConfiguration(){
	}

	public void setEngine(Engine engine){
		this.engine = engine;
	}
	
	public Engine getEngine(){
		return engine;
	}
	
	public EventInputManager getEventInputManager() {
		return eventInputManager;
	}
	
	public void setEventInputManager(EventInputManager eventInputManager) {
		this.eventInputManager = eventInputManager;
	}

	@Override
	public void configure() {
		//eventInputManager.addEventInput(jmsEventInput);
		eventInputManager.addEventInput(fileEventInput);
		engine.configure();
	}

	public void setFileEventInput(EventInput eventInput) {
		this.fileEventInput = eventInput;
	}

	public EventInput getFileEventInput() {
		return fileEventInput;
	}
}

	
