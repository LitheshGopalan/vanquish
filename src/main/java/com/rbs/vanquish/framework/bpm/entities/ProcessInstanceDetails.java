package com.rbs.vanquish.framework.bpm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Class used to maintain records for all the BPM instances executed across multiple nodes
 *                : in the cloud infrastructure. This table is used for calculating the pool size belongs to 
 *                : each node and it also book an entry in this table before exceuting a new process instance.
 *                : Failed over process will also update the address on this table to reflect the pool size 
 *                : across each node.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Entity
@Table(name="TXN_PROCESS_INSTANCE_DETAILS")
public class ProcessInstanceDetails {
	
	
	public ProcessInstanceDetails() {
		
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Process_Instance_Sequence")
    @SequenceGenerator(name = "Process_Instance_Sequence", sequenceName = "PROCESS_INSTANCE_DETAILS_SEQ")
	@Column(name = "PROCESS_ID")
    private Long id;
	
	
	@Column(name = "PROCESS_WORKFLOW_NAME", nullable = false)
    private String processWorkflowName;
	
	@Column(name = "BUSINESS_KEY", nullable = false)
    private String businessKey;
	
	@Column(name = "RUNNING_ON", nullable = false)
    private String runningOn;
	
	
	
	public String getProcessWorkflowName() {
		return processWorkflowName;
	}

	public void setProcessWorkflowName(String processWorkflowName) {
		this.processWorkflowName = processWorkflowName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getRunningOn() {
		return runningOn;
	}

	public void setRunningOn(String runningOn) {
		this.runningOn = runningOn;
	}
	

}
