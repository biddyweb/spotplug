package com.plugtree.spotplug.server;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.plugtree.spotplug.client.EventService;

@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements EventService {

	@Override
	public List<String> getRulesName() {
		
		List<String> rulesName = new LinkedList<String>();
		rulesName.add("Strange Volumn Transaction");
		rulesName.add("Incorrect Message Order");
		
		return rulesName;
	}

	@Override
	public List<Long> getRuleActivations(String ruleName) {
		
		List<Long> listLong = new LinkedList<Long>();
		
		for(int i = 0; i < 24; i++) {
			listLong.add(new Long(1));
		}
		
		return listLong;
	}
}
