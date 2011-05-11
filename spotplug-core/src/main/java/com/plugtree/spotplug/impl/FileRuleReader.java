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
package com.plugtree.spotplug.impl;

import java.net.URL;

import com.plugtree.spotplug.Engine;
import com.plugtree.spotplug.RuleReader;

public class FileRuleReader implements RuleReader {

	/* (non-Javadoc)
	 * @see com.plugtree.spotplug.SpotPlugRuleReader#loadRules(com.plugtree.spotplug.SpotPlugEngine)
	 */
	@Override
    public URL loadRules(Engine engine) {
		// TODO Auto-generated method stub
		return this.getClass().getClassLoader().getResource("fraud.drl");
		
	}

}
