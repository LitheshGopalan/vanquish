package com.rbs.vanquish.framework.bpm.exception;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Runtime Exception Class used to throw the critical error; please note that throwing
 *                : this exception will result into roll-back of underlying transaction and will result into
 *                : roll-back of the underlying transaction.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class VanquishRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VanquishRuntimeException() {
		super();
	}
	
	
	public VanquishRuntimeException(String aMessage){
		super(aMessage);
		this.message = aMessage;
	}
	
	public VanquishRuntimeException(String aMessage, Throwable aCause){
		super(aMessage, aCause);
		this.message = aMessage;
	}
	
	public VanquishRuntimeException(String aMessage, Throwable aCause, boolean aEnableSuppression, boolean aWritableStackTrace){
		super(aMessage, aCause, aEnableSuppression, aWritableStackTrace);
		this.message = aMessage;
	}
	
	public VanquishRuntimeException(Throwable aCause){
		super(aCause);
	}

}
