package com.rbs.vanquish.bpm.example;

import org.springframework.beans.factory.annotation.Autowired;

import com.rbs.vanquish.bpm.message.creator.ExampleMessageCreator;
import com.rbs.vanquish.framework.bpm.message.VanquishMessageCreator;
import com.rbs.vanquish.framework.bpm.service.MessageSender;
/** --------------------------------------------------------------------------------------------------------
 * Description    : An class to be written by the devloper to dispatch a message to the queue used
 *                : in vanquish application. During execution framework will execute this class.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class ExampleMessageSender {
	
	@Autowired
	MessageSender messageSender;
	
	public void dispatchMessage(String aBusinessKey, Object aMessageObject) 
	{
		VanquishMessageCreator loExampleMessageCreator = new ExampleMessageCreator(aBusinessKey,aMessageObject);
		messageSender.sendMessage("VANQUISH.DUPLICATE.CHECK.RESPONSE.DEV", loExampleMessageCreator);
	}//eof dispatchMessage

}//eof class
