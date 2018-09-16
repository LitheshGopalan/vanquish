package com.rbs.vanquish.framework.bpm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;

import com.rbs.vanquish.framework.bpm.message.VanquishMessageCreator;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Service implementation to send a message from business process javs task class
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class MessageSenderImpl implements MessageSender{

	@Autowired
	JmsOperations jmsOperations;
	
	public MessageSenderImpl() {
		
	}
	
	@Override
	public void sendMessage(String aQueueName, VanquishMessageCreator aVanquishMessageCreator) {
		MessageCreator loMessageCreator = (MessageCreator) aVanquishMessageCreator;
		jmsOperations.convertAndSend(aQueueName, loMessageCreator);
	}
	
}
