/**
 * Copyright 2010 Plugtree LLC 
Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License. 
 */
package com.plugtree.spotplug.actuator;

import com.plugtree.spotplug.impl.User;
import org.drools.runtime.rule.RuleContext;

import com.plugtree.spotplug.Actuator;

public class SysoutActuator implements Actuator {
	
	@Override
	public void writeOutput(RuleContext ruleContext, User user) {
		System.out.println(ruleContext.getRule().getName() + " - " + user.getId() + " - " + user.getFraudProbability());
	}	
}
