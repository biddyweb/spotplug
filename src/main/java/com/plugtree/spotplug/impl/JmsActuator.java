package com.plugtree.spotplug.impl;

import org.drools.runtime.rule.RuleContext;
import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSession.QueueQuery;
import org.hornetq.core.client.impl.ClientProducerImpl;

import com.plugtree.spotplug.Actuator;

public class JmsActuator implements Actuator {

	private	ClientProducerImpl producer;
	final String propName = "Fraud";
	private ClientSession session;
	@Override
	public void writeOutput(RuleContext ruleContext, User user) {
		// TODO Auto-generated method stub
		ClientMessage message = getSession().createMessage(false);
		message.putStringProperty(propName,ruleContext.getRule().getName()+"-"+ user.getFraudProbability());	
		try{
			producer.send(message);			
		}catch(Exception ex){
		}
	}

	public void setProducer(ClientProducerImpl producer) {
		this.producer = producer;
	}

	public ClientProducerImpl getProducer() {
		return producer;
	}

	public void setSession(ClientSession session) {
		this.session = session;
	}

	public ClientSession getSession() {
		return session;
	}

}
