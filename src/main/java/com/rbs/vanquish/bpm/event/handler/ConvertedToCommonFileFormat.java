package com.rbs.vanquish.bpm.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.Message;
//import org.springframework.messaging.Message;

import com.rbs.vanquish.framework.bpm.common.MessageExecutionContext;
import com.rbs.vanquish.framework.bpm.message.MessageExecutor;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Core class which executes on events happen through messaging; this class will be invoked
 *                : By framework during execution. This class should be registered in config file.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class ConvertedToCommonFileFormat implements MessageExecutor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertedToCommonFileFormat.class);

	@Override
	public void executeMessage(MessageExecutionContext aMessageExecutionContext, Message aMessage)
	{
		try
		{
			logMessage("Inside executeMessage method of the class - "+this.getClass().getName());
			
			logMessage("MessageName      -> "+aMessageExecutionContext.getMessageName());
			logMessage("BusinessKey      -> "+aMessageExecutionContext.getBusinessKey());
			aMessageExecutionContext.setVariable("NewKeyInserted", "NewValueInserted");
			
			//Inspect your pay-load and set the process variable to influence the process flow
			//you can also perform any business functions required before proceeding with
			//process instance
			
			logMessage("\n\n IAM GOING TO PROCESS MESSAGE FROM  ConvertedToCommonFileFormat \n\n");
		}
		catch(Exception aException) 
		{
			aException.printStackTrace();
			LOGGER.error(aException.fillInStackTrace().toString());
		}
	}//eof executeMessage
	
	private void logMessage(String lsMessage) {
		LOGGER.info(lsMessage);
	}

}
