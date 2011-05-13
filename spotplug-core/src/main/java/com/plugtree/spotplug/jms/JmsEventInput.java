package com.plugtree.spotplug.jms;

import java.util.Date;
import java.util.StringTokenizer;

import com.plugtree.spotplug.impl.GenericEvent;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.core.client.impl.ClientConsumerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.EventInput;

public class JmsEventInput implements EventInput {

	private Engine engine;
	private	ClientConsumerImpl consumer;
	final String propName = "data";
	
	final static Logger logger = LoggerFactory.getLogger(JmsEventInput.class);
	
	@Override
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void run() {

		try{
			if( consumer != null){
				
				ClientMessage messageReceived = consumer.receive(1000);
				
				while(messageReceived != null){
					System.out.println("Received Message:" + messageReceived.getStringProperty(propName));
					GenericEvent event = createEvent(messageReceived.getStringProperty(propName));
					engine.processEvent(event);
					messageReceived = consumer.receive(1000);
				}
			}
		} catch(Exception ex) {
			
			logger.error("Message not received");			
		}
	}

	public void setConsumer(ClientConsumerImpl consumer) {
		this.consumer = consumer;
	}

	public ClientConsumerImpl getConsumer() {
		return consumer;
	}

	public GenericEvent createEvent(String line){
		
		//line format : UserID,Amount,Duration,TransactionID,SequentialID,OperationCode
				
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


}
