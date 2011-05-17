package com.plugtree.spotplug.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.plugtree.spotplug.client.RuleService;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

@SuppressWarnings("serial")
public class RuleServiceImpl extends RemoteServiceServlet implements RuleService {

	@Override
	public List<Long> getRuleHistory(String ruleName) {
		
		List<Long> ruleHistory = new LinkedList<Long>();
		
		return ruleHistory;
	}

	@Override
	public List<VisualRule> getRules(String name, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRuleNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VisualEvent> getRuleActivations(VisualRule visualRule) {
		// TODO Auto-generated method stub
		return null;
	}

}
