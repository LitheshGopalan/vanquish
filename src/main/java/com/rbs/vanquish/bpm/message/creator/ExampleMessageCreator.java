package com.rbs.vanquish.bpm.message.creator;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.rbs.vanquish.framework.bpm.message.VanquishMessageCreator;
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
public class ExampleMessageCreator extends VanquishMessageCreator{

	public ExampleMessageCreator(String aBusinessKey, Object aMessageObject) {
		super(aBusinessKey, aMessageObject);
	}
	
    protected Message createMessageAndDispatch(Session aSession) throws JMSException
    {
    	TextMessage loTextMessage = aSession.createTextMessage();
    	
    	//This is really important to set BUSINESS_KEY as correlation ID framework use 
    	//JMSCorrelationID to awake the business process
    	loTextMessage.setJMSCorrelationID(this.BUSINESS_KEY);
    	loTextMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
    	loTextMessage.setJMSExpiration(-1);
    	loTextMessage.setText(createPayload());
		return loTextMessage;
	}//eof createMessageAndDispatch
    
    private String createPayload() 
    {
    	//create pay-load of your message according to your requirements you can transform
    	//MESSAGE_OBJECT to required JSON and dispatch based on your requirements.
    	return this.MESSAGE_OBJECT.toString();
    }//eof createPayload

}
