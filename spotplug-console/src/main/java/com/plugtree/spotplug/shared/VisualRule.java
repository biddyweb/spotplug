package com.plugtree.spotplug.shared;

import com.google.gwt.user.client.rpc.IsSerializable;


public class VisualRule implements IsSerializable {
    private String ruleName;
    private long timestamp;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
