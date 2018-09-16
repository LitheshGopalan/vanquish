package com.rbs.vanquish.bpm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rbs.vanquish.framework.bpm.common.TaskExecutionContext;
import com.rbs.vanquish.framework.bpm.constant.VanquishConstants;
import com.rbs.vanquish.framework.bpm.task.TaskExecutor;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Core class Java class for services task associated with BPM activity; framework will
 *                : invoke this java class during runtime. This class should be registered in config file. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class PerformDuplicateFileFound implements TaskExecutor {
	private static final Logger LOGGER = LoggerFactory.getLogger(PerformDuplicateFileFound.class);
	
	public PerformDuplicateFileFound(){
	}
	
	
	@Override
	public void executeTask(TaskExecutionContext aExecutionContext)
	{
		try
		{
			logMessage("Inside executeTask method of the class - "+this.getClass().getName());
			
			logMessage("TaskID      -> "+aExecutionContext.getTaskID());
			logMessage("Process ID  -> "+aExecutionContext.getProcessInstanceID());
			logMessage("Keys        -> "+aExecutionContext.getKeys());
			logMessage("BusinessKey -> "+aExecutionContext.getProcessBusinessKey());
			
			String lsProcesses = (String) aExecutionContext.getValue(VanquishConstants.PROCESS_COMPLETED);
			lsProcesses = lsProcesses.trim();
			if (lsProcesses.length()==0)
			{
				lsProcesses = lsProcesses+"Duplicate File Found";
			}
			else
			{
				lsProcesses = lsProcesses+", Duplicate File Found";
			}
			aExecutionContext.setVariable(VanquishConstants.PROCESS_COMPLETED, lsProcesses);
			
			logMessage("\n\n IAM GOING TO PERFORM DUPLICATE FILE FOUND HERE \n\n");
		}
		catch(Exception aException) 
		{
			aException.printStackTrace();
			LOGGER.error(aException.fillInStackTrace().toString());
		}
	}
	
	private void logMessage(String lsMessage) {
		LOGGER.info(lsMessage);
	}

}
