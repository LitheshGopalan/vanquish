package com.rbs.vanquish.framework.bpm.service;

import java.util.Map;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Service interface to manage process pool across all the workflow nodes; running instances
 *                : are tracked using a database table and is used by all the active nodes running on cloud
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface ProcessInstanceDetailsService {
	
	public void createProcessInstanceDetails(String aProcessWorkflowName, String  aBusinessKey, String aRunningOn);
	public Boolean reserveAndBlockProcessInstance(String aProcessWorkflowName, String  aBusinessKey, String aRunningOn, Integer aMaximumInstanceCount);
	public void updateProcessInstanceDetails(String  aBusinessKey, String aRunningOn);
	public void deleteProcessInstanceDetails(String  aBusinessKey);
	public Integer getProcessInstanceCount(String aProcessWorkflowName);
	public Integer getProcessInstanceCountOnaNode(String aProcessWorkflowName, String aRunningOn);
	public Map<String,Boolean> getProcessInstanceStatus(Map<String,Boolean> aStatusMap, String aAddress);

}
