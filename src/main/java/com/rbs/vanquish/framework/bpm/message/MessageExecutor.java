package com.rbs.vanquish.framework.bpm.message;

import javax.jms.Message;
//import org.springframework.messaging.Message;

import com.rbs.vanquish.framework.bpm.common.MessageExecutionContext;
/** --------------------------------------------------------------------------------------------------------
 * Description    : An interface used for doing message processing during business process execution  
 *                : developers can provide implementation of this class and do processing before 
 *                : Executing the business process (activity to do after receiving the internal message).
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface MessageExecutor {
	
	public void executeMessage(MessageExecutionContext aMessageExecutionContext, Message aMessage) ;

}
