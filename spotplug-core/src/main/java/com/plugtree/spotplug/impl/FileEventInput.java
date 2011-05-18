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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	public GenericEvent createEvent(String line) throws ParseException {
		/**
		 * Format: eventType,dateTime,duration,attribute:value,attribute:value,..
		 * i.e.    bankEvent,18/05/2011 22:14:02,user:peter,amount:10000
		 */

		StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
				
		String eventType = stringTokenizer.nextToken();

		String stringDate = stringTokenizer.nextToken();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
		Date date = formatter.parse(stringDate);
		
		long duration = Long.parseLong(stringTokenizer.nextToken());
		
		GenericEvent genericEvent = new GenericEvent(eventType, date, duration);

		String attributeLine;
		
		while ((attributeLine = stringTokenizer.nextToken()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(attributeLine, ":");
			String key = tokenizer.nextToken();
			String value = tokenizer.nextToken();
			
			genericEvent.addAttribute(key, value);
		}
		
		return genericEvent;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}
}
