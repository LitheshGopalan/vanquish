package com.rbs.vanquish.framework.bpm.executor;

import com.rbs.vanquish.framework.bpm.pool.BPMProcess;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Core interface which is used for executes the business process instances using Camunda 
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface BusinessProcessExecutorService {

	String getBusinessKey();
	String getBusinessProcessName();
	void setBusinessKey(String businessKey);
	void setBusinessProcessName(String businessProcessName);
    String getNodeAddress(); 
    void setNodeAddress(String nodeAddress);
    Integer getMaxPoolSize();
	void setMaxPoolSize(Integer maxPoolSize);
	void executeBusinessProcessInstance(BPMProcess aBPMProcess, PreProcessableService aPreProcessable);

}