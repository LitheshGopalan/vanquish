package com.rbs.vanquish.framework.bpm.plugin;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** --------------------------------------------------------------------------------------------------------
 * Description    : In this listener you can do basically everything. You could also implement it as 
 *                : Spring bean.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class SendEventListener implements ExecutionListener {
	private  final Logger LOGGER = LoggerFactory.getLogger(SendEventListener.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("Hello world. There was an event '" + execution.getEventName() + "'! It came from activity '"+execution.getCurrentActivityId()+"' for process instance '" + execution.getProcessInstanceId() + "'");
	}
	
	 private  void logMessage(String lsMessage) {
		 LOGGER.info(lsMessage);
	 }

}
