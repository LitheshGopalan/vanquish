package com.rbs.vanquish.framework.bpm.pool;

import com.rbs.vanquish.framework.bpm.executor.PreProcessableService;
/** --------------------------------------------------------------------------------------------------------
 * Description    : This class is used by the framework to represent a business process process pool interface.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface BusinessProcessPoolService {

	void executeProcessInstance(BPMProcess aBPMProcess, PreProcessableService aPreProcessable);
	
	void updateProcessInstanceBasedOnKey(String aBusinessKey);

	void performCleanShutdown();

}