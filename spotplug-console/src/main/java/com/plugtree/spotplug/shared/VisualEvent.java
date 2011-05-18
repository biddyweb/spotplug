package com.plugtree.spotplug.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;


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
         getAttributesMap().put(key,value);

    }

    public String getAttribute(String key){
        return getAttributesMap().get(key);
    }

    public Set<String> getKeys() {
    	return getAttributesMap().keySet();
    }

    public Map<String, String> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(Map<String, String> attributesMap) {
        this.attributesMap = attributesMap;
    }
}
