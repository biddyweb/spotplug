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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plugtree.spotplug.EventInput;
import com.plugtree.spotplug.bus.Bus;
import com.plugtree.spotplug.model.GenericEvent;

public class FileEventInput implements EventInput {

	private Bus bus;
	final static Logger logger = LoggerFactory.getLogger(FileEventInput.class);
	
	//TODO: Se debe pasar desde la configuracion
	private final String path = "src/main/resources/events.txt";
	
	public FileEventInput(){		
	}

	@Override
	public void run(){

		File file = new File(path);
		BufferedReader fileReader = null;

		try {

			fileReader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = fileReader.readLine()) != null){

				GenericEvent event = createEvent(line);
				bus.addEvent(event);
			}

		} catch (Exception exception) {

			logger.error("File not found");

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
	
	public GenericEvent createEvent(String line){
		// Deprecated: line format : UserID,Amount,Duration,TransactionID,SequentialID,OperationCode
		// 


		StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
				
		//Name
		String name = stringTokenizer.nextToken();
		//Amount
		int amount = Integer.parseInt(stringTokenizer.nextToken());
		
		//Event Start Time Stamp
		Date startTimeStamp = new Date(); 
				
		//Duration
		long duration = Long.parseLong(stringTokenizer.nextToken());
		
		//Current Event Sequential ID - If there is an event sequence
		long sequentialID = Long.parseLong(stringTokenizer.nextToken());
		
		//TransactionID of the Current Event
		long transactionID = Long.parseLong(stringTokenizer.nextToken());
		
		//Operation Code of the Current Event
		long opCode = Long.parseLong(stringTokenizer.nextToken());
		
		//Date and Time that comes with the Current Event
		//Date innerDate = new Date(stringTokenizer.nextToken());
		
		GenericEvent event = new GenericEvent(name, amount,startTimeStamp,duration,sequentialID,transactionID,opCode);

		return event;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}
}
