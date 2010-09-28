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


import java.util.Date;

import com.plugtree.spotplug.Event;

public class GenericEvent implements Event {

	private String userId;
	private int amount;
	private Date callDateTime;
	private long callDuration;
	private long sequentialID;
	private long transactionID;
	private long opCode;

	public GenericEvent(String userId, int amount,Date callDateTime,long callDuration,long sequentialID,long transactionID,long opCode){
	
		this.userId = userId;
		this.amount = amount;
		this.callDateTime = callDateTime;
		this.callDuration = callDuration;
		this.sequentialID = sequentialID;
		this.transactionID = transactionID;
		this.opCode = opCode;
	}

	public int getAmount() {
		return amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setCallDateTime(Date callDateTime) {
		this.callDateTime = callDateTime;		
	}

	public Date getCallDateTime() {
		return callDateTime;
	}

	public void setCallDuration(long callDuration) {
		this.callDuration = callDuration;
	}

	public long getCallDuration() {
		return callDuration;
	}
	
	public void setSequentialID(long sequentialID) {
		this.sequentialID = sequentialID;
	}

	public long getSequentialID() {
		return sequentialID;
	}

	
	public void setTransactionID(long nextSequentialID) {
		this.transactionID = nextSequentialID;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public void setOpCode(long opCode) {
		this.opCode = opCode;
	}

	public long getOpCode() {
		return opCode;
	}
}
