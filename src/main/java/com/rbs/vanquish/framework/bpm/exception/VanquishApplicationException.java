package com.rbs.vanquish.framework.bpm.exception;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Application Exception Class used to throw the validation error; please note that throwing
 *                : this exception won't result into roll-back of any transaction but it stops the execution
 *                : as a business outcome.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class VanquishApplicationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VanquishApplicationException() {
		super();
	}
	
	public VanquishApplicationException(String aMessage) {
		super();
		this.message = aMessage;
	}
	
	
	public VanquishApplicationException(String aMessage, Throwable aCause){
		super(aCause);
		this.message = aMessage;
	}
	
	public VanquishApplicationException(String aMessage, Throwable aCause, boolean aEnableSuppression, boolean aWritableStackTrace, String aMessage1){
		super(aMessage, aCause, aEnableSuppression, aWritableStackTrace);
		this.message = aMessage;
	}
	
	public VanquishApplicationException(Throwable aCause, String aMessage){
		super(aCause);
		this.message = aMessage;
	}

}
