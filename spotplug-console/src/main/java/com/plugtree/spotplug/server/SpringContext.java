package com.plugtree.spotplug.server;

import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.plugtree.spotplug.Configuration;
import com.plugtree.spotplug.bus.Bus;
import com.plugtree.spotplug.impl.EventInputManager;

public class SpringContext {

	private static SpringContext instance = null;
    private ApplicationContext context;
    
	public static SpringContext getInstance() {
		
		if (instance == null) {
			instance = new SpringContext();
		}
		
		return instance;
	}
	
	public SpringContext() {
		
		context = new ClassPathXmlApplicationContext(new String[] {"/core.xml"});
		
		Configuration configuration = (Configuration) context.getBean("Configuration");;

        EventInputManager eventInputManager = configuration.getEventInputManager();
		configuration.configure();
		eventInputManager.start();
	}
	
	public Bus getBus() {
		return (Bus)context.getBean("Bus");
	}
	
	public StatefulKnowledgeSession getKnowledgeSession() {
		return (StatefulKnowledgeSession)context.getBean("ksession1");
	}
}
