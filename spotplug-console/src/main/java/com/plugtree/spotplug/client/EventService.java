package com.plugtree.spotplug.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

public interface EventService extends RemoteService {

	List<String> getRulesName();
	
	List<Long> getRuleActivations(String ruleName);
}
