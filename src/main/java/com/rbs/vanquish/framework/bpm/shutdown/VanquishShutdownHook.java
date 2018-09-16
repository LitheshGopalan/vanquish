package com.rbs.vanquish.framework.bpm.shutdown;

import com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolService;
/** --------------------------------------------------------------------------------------------------------
 * Description    : A background thread to hold processing of any new messages from any of the underlying 
 *                : queues and also to garcefuly complete all the in-flight process instance to complete. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class VanquishShutdownHook extends Thread{
	
	private BusinessProcessPoolService businessProcessPoolService = null;
	
	public VanquishShutdownHook(BusinessProcessPoolService aBusinessProcessPoolService) 
	{
		this.businessProcessPoolService = aBusinessProcessPoolService;
	}

	@Override
	public void run() 
	{
		businessProcessPoolService.performCleanShutdown();
	}//eof run

}//eof class
