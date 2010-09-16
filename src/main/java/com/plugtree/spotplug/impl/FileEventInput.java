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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.StringTokenizer;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.Event;
import com.plugtree.spotplug.EventInput;

public class FileEventInput implements EventInput {

	private Engine engine;
	
	//TODO: Se debe pasar desde la configuracion
	private final String path = "src/main/resources/events.txt";
	
	public FileEventInput(){		
	}
	
	public void setEngine(Engine engine) {

		this.engine = engine;		 
	}

	@Override
	public void run(){

		File file = new File(path);
		BufferedReader fileReader = null;

		try {

			fileReader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = fileReader.readLine()) != null){

				Event event = createEvent(line);
				engine.processEvent(event);
			}

		} catch (Exception exception) {

			exception.printStackTrace();

		} finally {
			try {

				if (fileReader != null) {
					fileReader.close();
				}
				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		} 
	}
	
	public Event createEvent(String line){
		
		
		StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
				
		//Name
		String name = stringTokenizer.nextToken();
		//Amount
		int amount = Integer.parseInt(stringTokenizer.nextToken());
		
		//Event Start Time Stamp
		Date startTimeStamp = new Date(); 
				
		//Duration
		long duration = Long.parseLong(stringTokenizer.nextToken());
		
		//Event Current Sequential ID - If there is an event sequence
		long sequentialID = Long.parseLong(stringTokenizer.nextToken());
		
		//Event Current Sequential ID - 0 ? There is No Next Event : There is Next Event
		long nextSequentialID = Long.parseLong(stringTokenizer.nextToken());
				
		GenericEvent event = new GenericEvent(name, amount,startTimeStamp,duration,sequentialID,nextSequentialID);

		return event;
	}

}
