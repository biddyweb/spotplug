package com.plugtree.spotplug.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.plugtree.spotplug.client.RuleService;
import com.plugtree.spotplug.model.GenericEvent;
import com.plugtree.spotplug.model.RuleEvent;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

@SuppressWarnings("serial")
public class RuleServiceImpl extends RemoteServiceServlet implements RuleService {

    SpringContext springContext;

    public RuleServiceImpl(){
         springContext = SpringContext.getInstance();
    }

	@Override
	public List<Long> getRuleHistory(String ruleName) {
		
		List<Long> ruleHistory = new LinkedList<Long>();
		
		return ruleHistory;
	}

	@Override
	public List<VisualRule> getRules(String name, Date date) {
	List<RuleEvent> ruleList = springContext.getBus().getRuleEventList();
	List<VisualRule> visualRuleList = new LinkedList<VisualRule>();


		    for(RuleEvent rule : ruleList) {
                if(rule.getRuleName().equals(name) && rule.getCallDateTime().getHours() == date.getHours()){
			        VisualRule visualRule = new VisualRule();
			        visualRule.setTimestamp(rule.getCallDateTime().getTime());
			        visualRule.setRuleName(rule.getRuleName());
                    visualRuleList.add(visualRule);
                }
		    }

		return visualRuleList;
	}

	@Override
	public List<String> getRuleNames() {
        List<String> rulesNames = new LinkedList<String>();
		for (RuleEvent rule : springContext.getBus().getRuleEventList()){
                 if (!rulesNames.contains(rule.getRuleName())){
                    rulesNames.add(rule.getRuleName());    
                 }
        }
        return rulesNames;
	}

	@Override
	public List<VisualEvent> getRuleActivations(VisualRule visualRule) {
		// TODO Auto-generated method stub
		return null;
	}

}
