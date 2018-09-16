package com.rbs.vanquish.framework.bpm.pool;

import java.util.Map;
/** --------------------------------------------------------------------------------------------------------
 * Description    : This class is used by the framework to represent a business process instance excuted in
 *                : the process pool.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class BPMProcess {
	
	private String businessProcessName = null;
	private String uniqueBusinessKey = null;
	private String correlationValue = null;
	private String processInstanceID = null;
	private Map<String,Object> processVaraiablesMap = null;
	
	public BPMProcess() {
		
	}
	
	public BPMProcess (String aBusinessProcessName, String aUniqueBusinessKey, Map<String,Object> aProcessVaraiablesMap,  
	String aCorrelationValue) {
		this.businessProcessName = aBusinessProcessName;
		this.uniqueBusinessKey = aUniqueBusinessKey;
		this.processVaraiablesMap = aProcessVaraiablesMap;
		this.correlationValue = aCorrelationValue;
	}
	
	

	public String getProcessInstanceID() {
		return processInstanceID;
	}



	public void setProcessInstanceID(String processInstanceID) {
		this.processInstanceID = processInstanceID;
	}



	public String getCorrelationValue() {
		return correlationValue;
	}




	public void setCorrelationValue(String correlationValue) {
		this.correlationValue = correlationValue;
	}




	public String getBusinessProcessName() {
		return businessProcessName;
	}




	public void setBusinessProcessName(String businessProcessName) {
		this.businessProcessName = businessProcessName;
	}




	public String getUniqueBusinessKey() {
		return uniqueBusinessKey;
	}



	public void setUniqueBusinessKey(String uniqueBusinessKey) {
		this.uniqueBusinessKey = uniqueBusinessKey;
	}



	public Map<String, Object> getProcessVaraiablesMap() {
		return processVaraiablesMap;
	}



	public void setProcessVaraiablesMap(Map<String, Object> processVaraiablesMap) {
		this.processVaraiablesMap = processVaraiablesMap;
	}

	
	 private static void logMessage(String lsMessage) {
		 System.out.println(lsMessage);
	 }

}
