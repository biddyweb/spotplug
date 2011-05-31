package com.plugtree.spotplug.actuator;

import com.plugtree.spotplug.bus.Bus;
import com.plugtree.spotplug.model.GenericEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedList;
import java.util.List;


public class EventActuator {
   private LinkedList<String> eventNames = new LinkedList<String>();
   private ApplicationContext context;
   private Bus bus;

   void EventActuator(){
       context = new ClassPathXmlApplicationContext(new String[] {"/core.xml"});
       bus = (Bus) context.getBean("Bus");
   }


   public void addEventName(String eventName){
          if(!eventNames.contains(eventName)){
                 eventNames.add(eventName);
          }
   }

    public List<String> getEventNames(){
      return eventNames;
    }

     public void insertEventInBus(GenericEvent event){
       bus.addEvent(event); 

    }

}
