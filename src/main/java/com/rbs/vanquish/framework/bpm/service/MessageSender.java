package com.rbs.vanquish.framework.bpm.service;

import com.rbs.vanquish.framework.bpm.message.VanquishMessageCreator;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Service interface to send a message from business process javs task class
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface MessageSender {
	public void sendMessage(String aQueueName, VanquishMessageCreator aVanquishMessageCreator);
}
