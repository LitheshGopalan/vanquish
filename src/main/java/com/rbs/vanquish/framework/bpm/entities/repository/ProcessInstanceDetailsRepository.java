package com.rbs.vanquish.framework.bpm.entities.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rbs.vanquish.framework.bpm.entities.ProcessInstanceDetails;

public interface ProcessInstanceDetailsRepository extends CrudRepository<ProcessInstanceDetails, Long>
{
	
	 @Query("SELECT pid FROM ProcessInstanceDetails pid WHERE pid.businessKey=:businessKey")
	 List<ProcessInstanceDetails> findByBusinessKey(@Param("businessKey") String aBusinessKey);
	 
	 
	 @Query("SELECT pid FROM ProcessInstanceDetails pid WHERE pid.processWorkflowName=:workflowName")
	 List<ProcessInstanceDetails> findByProcessWorkflowName(@Param("workflowName") String aProcessWorkflowName);
	 
	 @Query("SELECT pid FROM ProcessInstanceDetails pid WHERE pid.processWorkflowName=:workflowName and pid.runningOn=runningOn")
	 List<ProcessInstanceDetails> findByProcessByWorkflowNameAndNode(@Param("workflowName") String aProcessWorkflowName, @Param("runningOn") String aRunningOn);

	 @Query("SELECT pid FROM ProcessInstanceDetails pid WHERE pid.businessKey=:businessKey and pid.runningOn=runningOn")
	 List<ProcessInstanceDetails> findByProcessByBusinessKeyAndNode(@Param("businessKey") String aBusinessKey, @Param("runningOn") String aRunningOn);
}//eof ProcessInstanceDetailsRepository
