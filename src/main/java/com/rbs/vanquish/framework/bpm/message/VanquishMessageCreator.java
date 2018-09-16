package com.rbs.vanquish.framework.bpm.message;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;
/** --------------------------------------------------------------------------------------------------------
 * Description    : An starter framework class used to create JMS messages for dispatching to the other
 *                : components asynchronously; developers can extend this class and override the method
 *                : named createMessageAndDispatch to fit with their requirements.
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public abstract class VanquishMessageCreator implements MessageCreator{
	
	protected String BUSINESS_KEY = null;
	protected Object MESSAGE_OBJECT = null;

	public VanquishMessageCreator(String aBusinessKey, Object aMessageObject) {
		this.MESSAGE_OBJECT = aMessageObject;
		this.BUSINESS_KEY = aBusinessKey;
	}
	
	@Override
	public Message createMessage(Session aSession) throws JMSException 
	{
		//delegate the call to subclass which implements the message creation
		//functionality.
		Message loMessage = createMessageAndDispatch(aSession);
		
		//We will set the correlation id and persistence strategy for the message
		loMessage.setJMSCorrelationID(this.BUSINESS_KEY);
		loMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
		
		//Message should never expire
		loMessage.setJMSExpiration(-1);
		
		return loMessage;
	}//eof createMessage
	
    protected abstract Message createMessageAndDispatch(Session aSession) throws JMSException;

}
