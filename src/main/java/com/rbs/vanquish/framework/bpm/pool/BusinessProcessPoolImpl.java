package com.rbs.vanquish.framework.bpm.pool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.rbs.vanquish.framework.bpm.config.MessageProperties;
import com.rbs.vanquish.framework.bpm.exception.VanquishRuntimeException;
import com.rbs.vanquish.framework.bpm.executor.BusinessProcessExecutorImpl;
import com.rbs.vanquish.framework.bpm.executor.BusinessProcessExecutorService;
import com.rbs.vanquish.framework.bpm.executor.PreProcessableService;
import com.rbs.vanquish.framework.bpm.service.AddressKeyService;
import com.rbs.vanquish.framework.bpm.service.AddressKeyServiceImpl;
import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsService;
import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsServiceImpl;
/** --------------------------------------------------------------------------------------------------------
 * Description    : This class is used by the framework to represent a business process process pool.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class BusinessProcessPoolImpl implements BusinessProcessPoolService {
	
	private static int MAXIMUM_POOL_SIZE = 0;
	private static int DEFAULT_POOL_SIZE = 20;
	private static int DEFAULT_SLEEP_DELAY = 100;
	
	private String businessProcessName = "";
	private String nodeAddress = null;
	private Boolean shutdownInintiated = false;
	
	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	List<BusinessProcessExecutorImpl> executorPoolList = new LinkedList<BusinessProcessExecutorImpl>();
	
	private static BusinessProcessPoolImpl businessProcessPoolImpl = null;
	private static AddressKeyService addressKeyService = null;
	
	
	private BusinessProcessPoolImpl(String aBusinessProcessName, int aPoolMaximumSize) 
	{
			businessProcessName = aBusinessProcessName;
			MAXIMUM_POOL_SIZE = aPoolMaximumSize;
			if (MAXIMUM_POOL_SIZE <= 0) MAXIMUM_POOL_SIZE = DEFAULT_POOL_SIZE;
			addressKeyService = new AddressKeyServiceImpl();
			nodeAddress = buildNodeAddress();
			for (int count=0; count<=MAXIMUM_POOL_SIZE;count++)
			{
				BusinessProcessExecutorImpl loBusinessProcessExecutor = new  BusinessProcessExecutorImpl(businessProcessName, 
			    null, nodeAddress, MAXIMUM_POOL_SIZE);
				executorPoolList.add(loBusinessProcessExecutor);
			}//eof for
	}//eof BusinessProcessPool
	
	public static BusinessProcessPoolService getInstance(String aBusinessProcessName, int aPoolMaximumSize) 
	{
		if (businessProcessPoolImpl == null) 
		{
			lock.writeLock().lock();
			businessProcessPoolImpl = new BusinessProcessPoolImpl(aBusinessProcessName, aPoolMaximumSize);
			lock.writeLock().unlock();
		}//eof if
		return businessProcessPoolImpl;
	}//eof getInstance
	

	private String buildNodeAddress() 
	{
			String loNodeAddress = "";
			try 
			{
				loNodeAddress = addressKeyService.fetchNewAddressKey().toString();
			} 
			catch (Exception aException)
			{
				aException.printStackTrace();
				throw new VanquishRuntimeException(aException);
			}
			return loNodeAddress;
	}//eof buildNodeAddress
	
	/* (non-Javadoc)
	 * @see com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolService#executeProcessInstance(com.rbs.vanquish.framework.bpm.pool.BPMProcess, com.rbs.vanquish.framework.bpm.executor.PreProcessable)
	 */
	@Override
	public void executeProcessInstance(BPMProcess aBPMProcess, PreProcessableService aPreProcessable) 
	{
		try
		{
			lock.writeLock().lock();
			
			if (shutdownInintiated == true) 
			{
				throw new VanquishRuntimeException(MessageProperties.cantProcessNewInstanceShutdownInintiated);
			}//eof shutdown

			BusinessProcessExecutorService loBusinessProcessExecutor = getBusinessProcessExecutor(nodeAddress);
			
			String lsUniqueBusinessKey = aBPMProcess.getUniqueBusinessKey();
			loBusinessProcessExecutor.setBusinessKey(lsUniqueBusinessKey);
			
			loBusinessProcessExecutor.executeBusinessProcessInstance(aBPMProcess,aPreProcessable);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}//eof executeProcessInstance
	
	/**
	 * 
	 * @return
	 */
	private BusinessProcessExecutorService getBusinessProcessExecutor(String aAddress) 
	{
		BusinessProcessExecutorService loBPExecutor = null;
		try
		{
			//update process BusinessProcessExecutorService state
			updateBusinessProcessExecutorServiceState(aAddress);
			boolean flag = false;
			while (flag == false)
			{
				Iterator<BusinessProcessExecutorImpl> loBusinessProcessExecutorIter = executorPoolList.iterator(); 
				while (loBusinessProcessExecutorIter.hasNext()) 
				{
					BusinessProcessExecutorService loExecutor = loBusinessProcessExecutorIter.next();
					
					Poolable loPoolable = (Poolable) loExecutor;
					if (loPoolable.isFreeToProcess()) 
					{
						loBPExecutor = loExecutor;
						flag = true;
						return loBPExecutor;
					}//eof if
				}//eof while
				Thread.sleep(DEFAULT_SLEEP_DELAY);
			}//eof while
		}
		catch (InterruptedException aInterruptedException) 
		{
			aInterruptedException.printStackTrace();
			throw new VanquishRuntimeException(aInterruptedException);
		}
		return loBPExecutor;
	}//eof getBusinessProcessExecutor
	
	/**
	 * 
	 */
	private void updateBusinessProcessExecutorServiceState(String aAddress)
	{
		Iterator<BusinessProcessExecutorImpl> loBusinessProcessExecutorIter = executorPoolList.iterator(); 
		Map<String, Boolean> loKeyStatusMap = new HashMap<String,Boolean>();
		while (loBusinessProcessExecutorIter.hasNext()) 
		{
			BusinessProcessExecutorService loExecutor = loBusinessProcessExecutorIter.next();
			loKeyStatusMap.put(loExecutor.getBusinessKey(), new Boolean(false));
		}//eof while
		
		//Fetching the new status of the Business Key because it might have ended
		//Every time this is verified before allocating a new object.
		ProcessInstanceDetailsService loProcessInstanceDetailsService = new ProcessInstanceDetailsServiceImpl();
		Map<String,Boolean> loLatestStatusMap = loProcessInstanceDetailsService.getProcessInstanceStatus(loKeyStatusMap, aAddress);
		
		loBusinessProcessExecutorIter = executorPoolList.iterator(); 
		while (loBusinessProcessExecutorIter.hasNext()) 
		{
			BusinessProcessExecutorService loExecutor = loBusinessProcessExecutorIter.next();
			Boolean loNewStatus = loLatestStatusMap.get(loExecutor.getBusinessKey());
			Poolable loPoolable = (Poolable) loExecutor;
			loPoolable.setFreeToProcess(loNewStatus);
		}//eof while
	}//eof updateBusinessProcessExecutorServiceState
	

	
	/* (non-Javadoc)
	 * @see com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolService#performCleanShutdown()
	 */
	@Override
	public void performCleanShutdown() 
	{
		logMessage ( "  >> performCleanShutdown ()" );
		shutdownInintiated = true;
		try 
		{
			//let us inform all ongoing processes that shutdown invoked!
			Iterator<BusinessProcessExecutorImpl> loBusinessProcessExecutorIter = executorPoolList.iterator(); 
			while (loBusinessProcessExecutorIter.hasNext()) 
			{
				BusinessProcessExecutorService loExecutor = loBusinessProcessExecutorIter.next();
				Poolable loPoolable = (Poolable) loExecutor;
				loPoolable.stopProcessExceution();
			}//eof while
			
			
			//let us wait till all the ongoing processes are gets completed!
			boolean bFlag = false;
			while (bFlag == false)
			{
				loBusinessProcessExecutorIter = executorPoolList.iterator(); 
				boolean bNewStatus = true;
				while (loBusinessProcessExecutorIter.hasNext()) 
				{
					BusinessProcessExecutorService loExecutor = loBusinessProcessExecutorIter.next();
					Poolable loPoolable = (Poolable) loExecutor;
					bNewStatus = bNewStatus & loPoolable.isFreeToProcess();
				}//eof while
				bFlag = bNewStatus;
			}//eof while
			
	    } 
		catch (Exception aException) 
		{
			aException.printStackTrace();
			throw new VanquishRuntimeException(aException);
		} 
		finally 
		{
	        lock.writeLock().unlock();
	    }
		logMessage ( "  << performCleanShutdown ()" );
	}//eof performCleanShutdown
	

	private void logMessage(String aMessage) {
		System.out.println(aMessage);
	}

	@Override
	public void updateProcessInstanceBasedOnKey(String aBusinessKey) 
	{
		try
		{
			lock.writeLock().lock();
		
			if (shutdownInintiated == true) 
			{
				throw new VanquishRuntimeException(MessageProperties.cantProcessNewInstanceShutdownInintiated);
			}//eof shutdown

			BusinessProcessExecutorService loBusinessProcessExecutor = getBusinessProcessExecutor(nodeAddress);
			
			loBusinessProcessExecutor.setBusinessKey(aBusinessKey);
			loBusinessProcessExecutor.setBusinessProcessName(businessProcessName);
			loBusinessProcessExecutor.setNodeAddress(nodeAddress);
			loBusinessProcessExecutor.setMaxPoolSize(MAXIMUM_POOL_SIZE);
			
			//Both Object are passed as null because it is a switch over scenario no need to start 
			//business process
			
			loBusinessProcessExecutor.executeBusinessProcessInstance(null,null);
		}
		finally
		{
			lock.writeLock().unlock();
		}
		
	}//eof updateProcessInstanceBasedOnKey
	
}//eof class
