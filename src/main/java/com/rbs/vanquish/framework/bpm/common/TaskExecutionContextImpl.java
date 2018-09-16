package com.rbs.vanquish.framework.bpm.common;

import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Core  Java interface implementation will be provided to the task implementation class as an
 *                : argument this is used to set getter and setter variables to exchange during process flow. 
 *                : execution.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class TaskExecutionContextImpl implements TaskExecutionContext {
	
	private DelegateExecution delegateExecution;
	
	public TaskExecutionContextImpl(DelegateExecution aDelegateExecution){
		delegateExecution = aDelegateExecution;
	}

	@Override
	public void setVariable(String aKey, Object aValue){
		delegateExecution.setVariable(aKey, aValue);
	}

	@Override
	public Map<String, Object> getVariables() {
		return delegateExecution.getVariables();
	}

	@Override
	public Set<String> getKeys() {
		return delegateExecution.getVariableNames();
	}

	@Override
	public Object getValue(String aKey) {
		return delegateExecution.getVariable(aKey);
	}

	@Override
	public String getTaskID()  {
		return delegateExecution.getCurrentActivityId();
	}

	@Override
	public String getProcessInstanceID()  {
		return delegateExecution.getProcessInstanceId();
	}

	@Override
	public String getProcessBusinessKey() {
		return delegateExecution.getProcessBusinessKey();
	}

}
