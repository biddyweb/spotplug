package com.plugtree.spotplug.actuator;

import java.util.LinkedList;
import java.util.List;


public class EventActuator {
   private LinkedList<String> eventNames = new LinkedList<String>();

   public void addEventName(String eventName){
          if(!eventNames.contains(eventName)){
                 eventNames.add(eventName);
          }
   }

    public List<String> getEventNames(){
      return eventNames;
    }

}
