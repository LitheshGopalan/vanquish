package com.rbs.vanquish.framework.bpm.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** --------------------------------------------------------------------------------------------------------
 * Description    : Core  Java class implementation will be provided to the message implementation class as an
 *                : argument this is used to set getter and setter variables to exchange during process flow. 
 *                : execution.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class MessageExceutionContextImpl implements MessageExecutionContext {
	
	private Map<String,Object> aVariablesMap = new HashMap<String,Object>();
	private String aMessageName = "";
	private String aBusinessKey = "";
	
	public MessageExceutionContextImpl(Map<String,Object> aVariablesMap, String aMessageName, String aBusinessKey){
		this.aVariablesMap = aVariablesMap;
		this.aMessageName = aMessageName;
		this.aBusinessKey = aBusinessKey;
	}

	@Override
	public void setVariable(String aKey, Object aValue){
		aVariablesMap.put(aKey, aValue);
	}

	@Override
	public Map<String, Object> getVariables() {
		return aVariablesMap;
	}

	@Override
	public Set<String> getKeys() {
		return aVariablesMap.keySet();
	}

	@Override
	public Object getValue(String aKey) {
		return aVariablesMap.get(aKey);
	}

	@Override
	public String getMessageName() {
		return aMessageName;
	}

	@Override
	public String getBusinessKey() {
		return aBusinessKey;
	}


}
