package com.plugtree.spotplug.impl;

import java.util.Date;
import java.util.StringTokenizer;

import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.core.client.impl.ClientConsumerImpl;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.EventInput;

public class JmsEventInput implements EventInput {

	private Engine engine;
	private	ClientConsumerImpl consumer;
	final String propName = "myprop";
	
	@Override
	public void setEngine(Engine engine) {
		// TODO Auto-generated method stub
		this.engine = engine;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(consumer != null){
				ClientMessage messageReceived = consumer.receive(1000);
				if(consumer != null){
					System.out.println("Received Message:" + messageReceived.getStringProperty(propName));
					GenericEvent event = createEvent(messageReceived.getStringProperty(propName));
					engine.processEvent(event);
				}
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	public void setConsumer(ClientConsumerImpl consumer) {
		this.consumer = consumer;
	}

	public ClientConsumerImpl getConsumer() {
		return consumer;
	}

	public GenericEvent createEvent(String line){
		
		
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
