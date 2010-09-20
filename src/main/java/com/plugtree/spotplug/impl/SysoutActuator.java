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

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.plugtree.spotplug.Actuator;
import com.plugtree.spotplug.EventInput;
import com.plugtree.spotplug.impl.EventLog;

public class SysoutActuator implements Actuator {
	private String msg;
	private LinkedList<EventLog> eventLogs = new LinkedList<EventLog>();
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
	public void writeOutPut() {
		System.out.println(getMsg());
	}
	
	public void logEvent(EventLog eventLog){
		this.eventLogs.add(eventLog);		
	}
	
	
	
	
	
	
}
