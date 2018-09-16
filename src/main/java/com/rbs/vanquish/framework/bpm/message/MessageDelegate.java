package com.rbs.vanquish.framework.bpm.message;

import java.util.Map;

import  javax.jms.Message;
//import org.springframework.messaging.Message;

import com.rbs.vanquish.framework.bpm.exception.VanquishApplicationException;

/** --------------------------------------------------------------------------------------------------------
 * Description    : An interface used for doing message delegation during business process execution  
 *                : Asynchronous messaging to awake a business process form its life cycle.
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface MessageDelegate {
	void processMessageWithBusinessKey(String aQueueName, Message aMessage, String aProcessBusinessKey) throws VanquishApplicationException;
	void processMessageWithVariable(String aQueueName, Message aMessage, String aProcessVariableName, Object aProcessVariableValue) throws VanquishApplicationException;
	void processMessageWithVariableMap(String aQueueName, Message aMessage, Map<String, Object> aProcessVariableMap) throws VanquishApplicationException;
}