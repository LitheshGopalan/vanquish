package com.rbs.vanquish.framework.bpm.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rbs.vanquish.framework.bpm.config.MessageProperties;
import com.rbs.vanquish.framework.bpm.entities.ProcessInstanceDetails;
import com.rbs.vanquish.framework.bpm.entities.repository.ProcessInstanceDetailsRepository;
import com.rbs.vanquish.framework.bpm.exception.VanquishApplicationException;
import com.rbs.vanquish.framework.bpm.exception.VanquishRuntimeException;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Service implementation to manage process pool across all the workflow nodes; running instances
 *                : are tracked using a database table and is used by all the active nodes running in the cloud
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class ProcessInstanceDetailsServiceImpl implements ProcessInstanceDetailsService {
	
	@Autowired
	private ProcessInstanceDetailsRepository processInstanceDetailsRepository;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void createProcessInstanceDetails(String aProcessWorkflowName, String aBusinessKey, String aRunningOn) 
	{
		try
		{
			ProcessInstanceDetails loProcessInstanceDetails = new ProcessInstanceDetails();
			loProcessInstanceDetails.setProcessWorkflowName(aProcessWorkflowName);
			loProcessInstanceDetails.setBusinessKey(aBusinessKey);
			loProcessInstanceDetails.setRunningOn(aRunningOn);
			processInstanceDetailsRepository.save(loProcessInstanceDetails);
		}
		catch (Exception aException)
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(MessageProperties.unableToStoreToDatabase, aException);
		}
	}//eof createProcessInstanceDetails

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateProcessInstanceDetails(String aBusinessKey, String aRunningOn) 
	{
		try
		{
			List<ProcessInstanceDetails> loInstanceList = processInstanceDetailsRepository.findByBusinessKey(aBusinessKey);
			if (loInstanceList == null) throw new VanquishApplicationException(MessageProperties.unableToFindProcessBasedOnBusinessKey);
			if (loInstanceList.size() > 1) throw new VanquishApplicationException(MessageProperties.tooManyProcessBasedOnBusinessKey);
			
			ProcessInstanceDetails loProcessInstanceDetails = loInstanceList.get(0);
			loProcessInstanceDetails.setRunningOn(aRunningOn);
			processInstanceDetailsRepository.save(loProcessInstanceDetails);
		}
		catch (Exception aException)
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(MessageProperties.unableToStoreToDatabase, aException);
		}

	}//eof updateProcessInstanceDetails

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteProcessInstanceDetails(String aBusinessKey) 
	{
		try
		{
			List<ProcessInstanceDetails> loInstanceList = processInstanceDetailsRepository.findByBusinessKey(aBusinessKey);
			if (loInstanceList == null) throw new VanquishApplicationException(MessageProperties.unableToFindProcessBasedOnBusinessKey);
			if (loInstanceList.size() > 1) throw new VanquishApplicationException(MessageProperties.tooManyProcessBasedOnBusinessKey);
			
			ProcessInstanceDetails loProcessInstanceDetails = loInstanceList.get(0);
			Long loProcessID = loProcessInstanceDetails.getId();
			processInstanceDetailsRepository.deleteById(loProcessID);
		}
		catch (Exception aException)
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(MessageProperties.unableToDeleteFromDatabase, aException);
		}
	}//eof deleteProcessInstanceDetails

	@Override
	@Transactional(readOnly = true)
	public Integer getProcessInstanceCount(String aProcessWorkflowName) 
	{
		Integer loCount = new Integer(0);
		try
		{
			List<ProcessInstanceDetails> loInstanceList = processInstanceDetailsRepository.findByProcessWorkflowName(aProcessWorkflowName);
			if (loInstanceList == null) throw new VanquishApplicationException(MessageProperties.unableToFindProcessBasedOnProcessWorkflowName);
			
			loCount = loInstanceList.size();
		}
		catch (Exception aException)
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(MessageProperties.unableToReadFromDatabase, aException);
		}
		return loCount;
	}//eof getProcessInstanceCount

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Boolean reserveAndBlockProcessInstance(String aProcessWorkflowName, String aBusinessKey, String aRunningOn, Integer aMaximumInstanceCount) 
	{
		Boolean loStatus = false;
		Integer loCurrentKount = -1;
		try
		{
			
			ProcessInstanceDetails loProcessInstanceDetails = new ProcessInstanceDetails();
			loProcessInstanceDetails.setProcessWorkflowName(aProcessWorkflowName);
			loProcessInstanceDetails.setBusinessKey(aBusinessKey);
			loProcessInstanceDetails.setRunningOn(aRunningOn);
			
			//delete if entry exists for this business key (Business key can't be duplicate) switch over scenario from one node to another
			//process instance moved from other node to this node.
			List<ProcessInstanceDetails> loProcessInstanceDetailsList = processInstanceDetailsRepository.findByBusinessKey(aBusinessKey);
			if (loProcessInstanceDetailsList != null)
			{
				if (loProcessInstanceDetailsList.size() > 0) 
				{
					Iterator<ProcessInstanceDetails> loIter = loProcessInstanceDetailsList.iterator();
					while (loIter.hasNext()) 
					{
						ProcessInstanceDetails loProcessInstanceDetailsOld = loIter.next();
						processInstanceDetailsRepository.deleteById(loProcessInstanceDetailsOld.getId());
					}//eof while
				}///exists
			}//eof not null
			
			List<ProcessInstanceDetails> loInstanceList = processInstanceDetailsRepository.findByProcessByWorkflowNameAndNode(aProcessWorkflowName, aRunningOn);
			if (loInstanceList == null)  loCurrentKount=0;
			if (loInstanceList != null)  loCurrentKount=loInstanceList.size();
			
			if (loCurrentKount < aMaximumInstanceCount) 
			{
				processInstanceDetailsRepository.save(loProcessInstanceDetails);
				loStatus = true;
			}
		}
		catch (Exception aException)
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(MessageProperties.unableToReadFromDatabase, aException);
		}
		return loStatus;
	}//eof reserveAndBlockProcessInstance

	@Override
	@Transactional(readOnly = true)
	public Integer getProcessInstanceCountOnaNode(String aProcessWorkflowName, String aRunningOn) {
		Integer loCurrentKount = -1;
		try
		{
			List<ProcessInstanceDetails> loInstanceList = processInstanceDetailsRepository.findByProcessByWorkflowNameAndNode(aProcessWorkflowName, aRunningOn);
			if (loInstanceList == null)  loCurrentKount=0;
			if (loInstanceList != null)  loCurrentKount=loInstanceList.size();
		}
		catch (Exception aException)
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(MessageProperties.unableToReadFromDatabase, aException);
		}
		return loCurrentKount;
	}//eof getProcessInstanceCountOnaNode

	@Override
	public Map<String, Boolean> getProcessInstanceStatus(Map<String, Boolean> aStatusMap, String aAddress) 
	{
		if (aStatusMap == null) aStatusMap = new HashMap<String,Boolean>();
		Map<String, Boolean> loResponseMap = new HashMap<String,Boolean>();
		
		try
		{
			Iterator<String> loKeyIter = aStatusMap.keySet().iterator();
			while (loKeyIter.hasNext()) 
			{
				String lsBusinessKey = loKeyIter.next();
				Boolean loStatus = new Boolean(false);
				List<ProcessInstanceDetails> loInstanceList = processInstanceDetailsRepository.findByProcessByBusinessKeyAndNode(lsBusinessKey, aAddress);
				if (loInstanceList.size() < 1) loStatus = new Boolean(true);
				loResponseMap.put(lsBusinessKey, loStatus);
			}//eof while
		}
		catch (Exception aException)
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(MessageProperties.unableToDeleteFromDatabase, aException);
		}
		return loResponseMap;
	}//eof getProcessInstanceStatus

}
