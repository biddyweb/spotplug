package com.plugtree.spotplug.impl;

import java.util.LinkedList;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.EventInput;

public class EventInputManager {

	private LinkedList<EventInput> eventInputList = new LinkedList<EventInput>();
	private LinkedList<Thread> threads = new LinkedList<Thread>();
	private Engine engine;
	
	public EventInputManager() {
	}
	
	public void addEventInput(EventInput eventInput){
		
		eventInputList.add(eventInput);
	}
	
	public void start(){
		Thread t = null;
		
		//Comenzamos a Correr todos los Eventos
		for (EventInput event : eventInputList){
			
			t = new Thread(event);
			threads.add(t);
		    t.start();
		}
		
		//Esperamos a que terminen todos los Threads
		for (Thread thread : threads){
			
			try{
				thread.join();
				
			} catch (InterruptedException ignore) {}
		}

		engine.stop();
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
