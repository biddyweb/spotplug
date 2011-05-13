package com.plugtree.spotplug.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.HashMap;
import java.util.Map;


public class VisualEvent implements IsSerializable {
    private String eventName;
    private long timestamp;
    private Map<String,String> attributesMap = new HashMap<String,String>();

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void addAttribute(String key,String value){
         attributesMap.put(key,value);

    }

    public String getAttribute(String key){
        return attributesMap.get(key);
    }

}
