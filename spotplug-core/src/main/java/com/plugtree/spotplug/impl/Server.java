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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.plugtree.spotplug.Configuration;
import com.plugtree.spotplug.bus.Bus;

public class Server {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/core.xml"});//, "/connection.xml"
		
		Configuration configuration = (Configuration) context.getBean("Configuration");
		
		EventInputManager eventInputManager = configuration.getEventInputManager();
		configuration.configure();
		eventInputManager.start();
	}
}
