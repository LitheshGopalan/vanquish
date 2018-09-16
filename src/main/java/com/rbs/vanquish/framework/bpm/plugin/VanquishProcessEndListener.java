package com.rbs.vanquish.framework.bpm.plugin;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsService;
import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsServiceImpl;

/** --------------------------------------------------------------------------------------------------------
 * Description    : Listener to be associated with end event; this will ensure the process pool is in synch 
 *                : with the number of business process executed on each nodes; completed process will be
 *                : take out from the DB tables in which it maintains the process count
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class VanquishProcessEndListener implements ExecutionListener 
{
	private  final Logger LOGGER = LoggerFactory.getLogger(VanquishProcessEndListener.class);
	
	ProcessInstanceDetailsService loProcessInstanceDetailsService = new ProcessInstanceDetailsServiceImpl();

	@Override
	public void notify(DelegateExecution execution) throws Exception 
	{
		//System.out.println("Hello world. There was an event '" + execution.getEventName() + "'! It came from activity '"+execution.getCurrentActivityId()+"' for process instance '" + execution.getProcessInstanceId() + "'");
		String lsBusinessKey = execution.getBusinessKey();
		
		logMessage("EventName         -> "+execution.getEventName());
		logMessage("CurrentActivityId -> "+execution.getCurrentActivityId());
		logMessage("ProcessInstanceId -> "+execution.getProcessInstanceId());
		logMessage("BusinessKey       -> "+execution.getBusinessKey());
		
		loProcessInstanceDetailsService.deleteProcessInstanceDetails(lsBusinessKey);
	}//eof notify
	
	 private  void logMessage(String lsMessage) {
		 LOGGER.info(lsMessage);
	 }

}//eof VanquishProcessEndListener
