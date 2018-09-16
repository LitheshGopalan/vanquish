package com.rbs.vanquish.framework.bpm.pool;
/** --------------------------------------------------------------------------------------------------------
 * Description    : This class is used by the framework to represent a poolable interface.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface Poolable {
	
	public boolean isFreeToProcess();
	public void setFreeToProcess(Boolean aStatus);
	public void stopProcessExceution();
	public void resumeProcessExceution();

}
