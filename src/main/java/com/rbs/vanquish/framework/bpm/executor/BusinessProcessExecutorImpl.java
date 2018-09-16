package com.rbs.vanquish.framework.bpm.executor;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.rbs.vanquish.framework.bpm.config.MessageProperties;
import com.rbs.vanquish.framework.bpm.constant.VanquishConstants;
import com.rbs.vanquish.framework.bpm.exception.VanquishRuntimeException;
import com.rbs.vanquish.framework.bpm.pool.BPMProcess;
import com.rbs.vanquish.framework.bpm.pool.Poolable;
import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsService;
import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsServiceImpl;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Core class which executes the business process instances using Camunda Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class BusinessProcessExecutorImpl implements Poolable, BusinessProcessExecutorService{
	
	private static int DEFAULT_SLEEP_DELAY = 100;
	private ProcessInstanceDetailsService loProcessInstanceDetailsService = null;
	private String businessProcessName = null;
	private String nodeAddress = null;
	private Integer maxPoolSize = null;
	private String businessKey = null;
	private boolean isFreeToServe = true;
	private Boolean stopProcessingFlag = new Boolean(false);
	ReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Autowired
	private ProcessEngine processEngine;
	
	public BusinessProcessExecutorImpl(String aBusinessProcessName, 
    String aBusinessKey, String aNodeAddress, Integer aMaximumPoolSize) 
	{
		setBusinessProcessName(aBusinessProcessName);
		setNodeAddress(aNodeAddress);
		setMaxPoolSize(aMaximumPoolSize);
		setBusinessKey(aBusinessKey);
		loProcessInstanceDetailsService = new ProcessInstanceDetailsServiceImpl();
	}//eof BusinessProcessExecutor
	
	

	private Boolean getStopProcessingFlag() {
		return stopProcessingFlag;
	}

	private void setStopProcessingFlag(Boolean stopProcessingFlag) {
		this.stopProcessingFlag = stopProcessingFlag;
	}

	private boolean isFreeToServe() {
		return isFreeToServe;
	}


	private void setFreeToServe(boolean isFreeToServe) {
		this.isFreeToServe = isFreeToServe;
	}


	/* (non-Javadoc)
	 * @see com.rbs.vanquish.framework.bpm.executor.BusinessProcessExecutor#getBusinessKey()
	 */
	@Override
	public String getBusinessKey() {
		return businessKey;
	}


	/* (non-Javadoc)
	 * @see com.rbs.vanquish.framework.bpm.executor.BusinessProcessExecutor#setBusinessKey(java.lang.String)
	 */
	@Override
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}


	public String getBusinessProcessName() {
		return businessProcessName;
	}

	public void setBusinessProcessName(String businessProcessName) {
		this.businessProcessName = businessProcessName;
	}

	public String getNodeAddress() {
		return nodeAddress;
	}

	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	
	/* (non-Javadoc)
	 * @see com.rbs.vanquish.framework.bpm.executor.BusinessProcessExecutor#executeBusinessProcessInstance(com.rbs.vanquish.framework.bpm.pool.BPMProcess, com.rbs.vanquish.framework.bpm.executor.PreProcessable)
	 */
	@Override
	public void executeBusinessProcessInstance(BPMProcess aBPMProcess, PreProcessableService aPreProcessable) 
	{
		try
		{
			if (getStopProcessingFlag() == true) 
			{
				throw new VanquishRuntimeException(MessageProperties.cantProcessNewInstanceShutdownInintiated);
			}//eof shutdown
			
			lock.writeLock().lock();
			setFreeToServe(false);
			
			//Checking no of process instance is reached maximum count or not; if reached waiting till getting 
			//a slot Status become true once slot is booked.
			Boolean loStatus = loProcessInstanceDetailsService.reserveAndBlockProcessInstance(getBusinessProcessName(), 
			getBusinessKey(), getNodeAddress(), getMaxPoolSize());
			while (loStatus == false) 
			{
					Thread.sleep(DEFAULT_SLEEP_DELAY);
					loStatus = loProcessInstanceDetailsService.reserveAndBlockProcessInstance(getBusinessProcessName(), 
					getBusinessKey(), getNodeAddress(), getMaxPoolSize());
			}//eof while
			if (aPreProcessable != null) aPreProcessable.perfromPreProcess();
			
			//Execute Business Process
			if (aBPMProcess != null) executeBusinessProcess(aBPMProcess);
		}
		catch (InterruptedException aInterruptedException) 
		{
			aInterruptedException.printStackTrace();
			throw new VanquishRuntimeException(aInterruptedException);
		}
		finally
		{
			lock.writeLock().unlock();
			//setFreeToServe(true);
		}
	}//eof executeBusinessProcessInstance
	
	/**
	 * 
	 * @param aBPMProcess
	 */
	private void executeBusinessProcess(BPMProcess aBPMProcess)
	{
		 String lsProcessName = aBPMProcess.getBusinessProcessName();
		 String lsBusinessKey = aBPMProcess.getUniqueBusinessKey();
		 String lsCorrelationvalue = aBPMProcess.getCorrelationValue();
		 Map<String,Object> loVaraiablesMap = aBPMProcess.getProcessVaraiablesMap();
		 loVaraiablesMap.put(VanquishConstants.UNIQUE_REFERENCE_KEY, lsBusinessKey);
		 loVaraiablesMap.put(VanquishConstants.UNIQUE_CORRELATION_KEY, lsCorrelationvalue);
		 setupUser();
		 ProcessInstance loProcessInstance =  processEngine.getRuntimeService().startProcessInstanceByKey(lsProcessName, lsBusinessKey, loVaraiablesMap);
		 String lsProcessID = loProcessInstance.getProcessInstanceId();
		 aBPMProcess.setProcessInstanceID(lsProcessID);
	}//eof executeBusinessProcess
	
	/**
	 * 
	 */
	private void setupUser() {
	if (processEngine.getIdentityService().createUserQuery().userId("LitheshAdmin").count() == 0) 
	{
		User user = processEngine.getIdentityService().newUser("LitheshAdmin");
		user.setFirstName("Lithesh");
		user.setLastName("Anargha");
		user.setPassword("LitheshAdmin");
		user.setEmail("Lithesh.Anargha@rbs.co.uk");
		processEngine.getIdentityService().saveUser(user);

		Group group = processEngine.getIdentityService().newGroup(Groups.CAMUNDA_ADMIN);
		group.setName("Administrators");
		group.setType(Groups.GROUP_TYPE_SYSTEM);
		processEngine.getIdentityService().saveGroup(group);

		for (Resource resource : Resources.values()) 
		{
			Authorization auth = processEngine.getAuthorizationService().createNewAuthorization(AUTH_TYPE_GRANT);
		    auth.setGroupId(Groups.CAMUNDA_ADMIN);
		    auth.addPermission(ALL);
		    auth.setResourceId(ANY);
		    auth.setResource(resource);
		    processEngine.getAuthorizationService().saveAuthorization(auth);
		 }
		 processEngine.getIdentityService().createMembership("LitheshAdmin", Groups.CAMUNDA_ADMIN);
    }//eof if
	}//eof setupUser


	@Override
	public boolean isFreeToProcess() {
		boolean status = false;
		try
		{
			lock.readLock().lock();
			status = isFreeToServe();
			return status;
			
		}
		finally
		{
			lock.readLock().unlock();
		}
	}//eof is isFreeToProcess

	@Override
	public void stopProcessExceution() {
		try
		{
			lock.writeLock().lock();
			setStopProcessingFlag(true);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}//eof stopProcessExceution

	@Override
	public void resumeProcessExceution() {
		try
		{
			lock.writeLock().lock();
			setStopProcessingFlag(false);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}//eof resumeProcessExceution



	@Override
	public void setFreeToProcess(Boolean aStatus) {
		isFreeToServe = aStatus;
	}

}//eof BusinessProcessExecutor
