package com.rbs.vanquish.bpm.preprocess;

import com.rbs.vanquish.framework.bpm.executor.PreProcessableService;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Core class is used to create a new message for dispatching this class will be invoked
 *                : By framework during execution. This class should be written according to your
 *                : requirements.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class PaymentPreProcess implements PreProcessableService{

	@Override
	public void perfromPreProcess() 
	{
		logMessage("Inside perfromPreProcess method of the class - "+this.getClass().getName());
	}//eof perfromPreProcess
	
	private  void logMessage(String lsMessage) {
		System.out.println(lsMessage);
	}

}//eof PaymentPreProcess
