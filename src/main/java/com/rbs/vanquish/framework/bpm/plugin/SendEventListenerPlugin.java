package com.rbs.vanquish.framework.bpm.plugin;

import java.util.ArrayList;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** --------------------------------------------------------------------------------------------------------
 * Description    : just needs to be a spring bean in order to get automatically registered in the Spring 
 *                : Boot environment..
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class SendEventListenerPlugin extends AbstractProcessEnginePlugin {
	
	private  final Logger LOGGER = LoggerFactory.getLogger(SendEventListenerPlugin.class);

	@Override
	public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		if (processEngineConfiguration.getCustomPostBPMNParseListeners() == null) {
			processEngineConfiguration.setCustomPostBPMNParseListeners(new ArrayList<BpmnParseListener>());
		}
		processEngineConfiguration.getCustomPostBPMNParseListeners().add(new AddSendEventListenerToBpmnParseListener());

	}
	
	 private  void logMessage(String lsMessage) {
		 LOGGER.info(lsMessage);
	 }

}
