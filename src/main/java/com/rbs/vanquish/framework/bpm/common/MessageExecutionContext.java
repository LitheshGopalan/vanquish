package com.rbs.vanquish.framework.bpm.common;

import java.util.Map;
import java.util.Set;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Core Java class interface will be provided to the message implementation class as an
 *                : argument this is used to set getter and setter variables to exchange during process flow. 
 *                : execution.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface MessageExecutionContext {
	public void setVariable(String aKey, Object aValue);
	public Map<String,Object> getVariables();
	public Set<String> getKeys();
	public Object getValue(String aKey);
	public String getMessageName();
	public String getBusinessKey();
}
