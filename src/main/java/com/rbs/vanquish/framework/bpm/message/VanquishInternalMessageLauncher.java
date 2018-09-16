package com.rbs.vanquish.framework.bpm.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.camunda.bpm.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.rbs.vanquish.framework.bpm.config.ProcessPoolProperties;
import com.rbs.vanquish.framework.bpm.exception.VanquishApplicationException;
import com.rbs.vanquish.framework.bpm.exception.VanquishRuntimeException;
import com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolImpl;
import com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolService;
/** --------------------------------------------------------------------------------------------------------
 * Description    : An starter class used by the framework to create a new business process pool and start 
 *                : business process for the application; internal queues should exists for each instance
 *                : of this application and each message on this queue will result into a business process
 *                : instance activation.
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
//@Component
public class VanquishInternalMessageLauncher implements MessageListener  {

	private static final Logger LOGGER = LoggerFactory.getLogger(VanquishInternalMessageLauncher.class);
	
	private static BusinessProcessPoolService businessProcessPoolService = null;
	private String QUEUE_NAME = null;
	
	@Autowired
	private ProcessEngine processEngine;
	
	public VanquishInternalMessageLauncher(String aQueueName) {
		QUEUE_NAME = aQueueName;
	}
	
	
	@Override
	@Transactional
	public void onMessage(Message aMessage) 
	{
		try
		{
			logMessage("Inside executeMessage method of the class - "+this.getClass().getName());
			if (businessProcessPoolService == null) 
			{
				businessProcessPoolService = BusinessProcessPoolImpl.getInstance(ProcessPoolProperties.ProcessWorkflowName, 
				ProcessPoolProperties.MaximumPoolSize);
			}//eof if
			performMessageProcessing(aMessage);
		}
		catch(Exception aException) 
		{
			aException.printStackTrace();
			LOGGER.error(aException.fillInStackTrace().toString());
			throw new VanquishRuntimeException(aException);
		}
	}//eof class
	
	
	private void performMessageProcessing(Message aMessage) throws JMSException, VanquishApplicationException 
	{
		logMessage(" >> performMessageProcessing () "+ this.getClass().getName());
		String lsMessageCorrelationID = aMessage.getJMSCorrelationID();
		businessProcessPoolService.updateProcessInstanceBasedOnKey(lsMessageCorrelationID);
		MessageDelegate loMessageDelegate = new MessageDelegateImpl(processEngine);
		
		//should be remove while testing
		//org.springframework.messaging.Message loMsg = (org.springframework.messaging.Message) aMessage;
		
		loMessageDelegate.processMessageWithBusinessKey(QUEUE_NAME, aMessage, lsMessageCorrelationID);
        logMessage(" << performMessageProcessing () "+ this.getClass().getName());
	}//eof performMessageProcessing
	
	
	private void logMessage(String lsMessage) {
		LOGGER.info(lsMessage);
	}
}
