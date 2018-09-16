package com.rbs.vanquish.framework.bpm.message;

import javax.jms.Message;
//import org.springframework.messaging.Message;

import com.rbs.vanquish.framework.bpm.common.MessageExecutionContext;
import com.rbs.vanquish.framework.bpm.exception.VanquishApplicationException;
/** --------------------------------------------------------------------------------------------------------
 * Description    : An interface used for doing message processing during business process execution  
 *                : Asynchronous messaging to awake a business process form its life cycle.
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface MessageDelegateProcessor {
	
	public void executeMessage(MessageExecutionContext aMessageExecutionContext, Message aMessage) throws VanquishApplicationException;

}
