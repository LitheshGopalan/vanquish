package com.rbs.vanquish.framework.bpm.executor;
/** --------------------------------------------------------------------------------------------------------
 * Description    : An interface used  for doing some pre-processing during the execution of a new business 
 *                : process; such as sanity test; storing in DB etc
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface PreProcessableService {
	
	public void perfromPreProcess();

}
