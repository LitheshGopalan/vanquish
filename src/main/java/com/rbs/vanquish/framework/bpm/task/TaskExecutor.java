package com.rbs.vanquish.framework.bpm.task;

import com.rbs.vanquish.framework.bpm.common.TaskExecutionContext;
/** --------------------------------------------------------------------------------------------------------
 * Description    : A task executer interface used by all the java classes used in the bsuiness process.
 *                : developers should implement this interface for framework to delegate the exceution
 *                : to the code written by the developer
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface TaskExecutor {
	
	public void executeTask(TaskExecutionContext aExecutionContext);

}
