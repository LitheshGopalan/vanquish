package com.rbs.vanquish.framework.bpm.message;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rbs.vanquish.bpm.preprocess.PaymentPreProcess;
import com.rbs.vanquish.framework.bpm.config.ProcessPoolProperties;
import com.rbs.vanquish.framework.bpm.constant.VanquishConstants;
import com.rbs.vanquish.framework.bpm.pool.BPMProcess;
import com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolImpl;
import com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolService;
import com.rbs.vanquish.framework.bpm.service.BusinessKeyService;
/** --------------------------------------------------------------------------------------------------------
 * Description    : An starter class used by the framework to create a new buisness process pool and start 
 *                : business process for the application; Only one main queue should exists for an instance
 *                : of this application and each message on the main queue will result into a business process
 *                : instance creation.
 *                : Java API
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Component
public class VanquishBusinessProcessLauncher implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(VanquishBusinessProcessLauncher.class);
	
	private static BusinessProcessPoolService businessProcessPoolService = null;
	
	@Autowired
	private static BusinessKeyService businessKeyService;
	
	@Override
	@Transactional
	public void onMessage(Message aMessage) 
	{
		try
		{
			logMessage("Inside executeMessage method of the class - "+this.getClass().getName());
			if (businessProcessPoolService == null) 
			{
				BusinessProcessPoolImpl.getInstance(ProcessPoolProperties.ProcessWorkflowName, 
				ProcessPoolProperties.MaximumPoolSize);
			}//eof if
			triggerBusinessProcess();
		}
		catch(Exception aException) 
		{
			aException.printStackTrace();
			LOGGER.error(aException.fillInStackTrace().toString());
		}
	}//eof class
	
	
	private void triggerBusinessProcess() 
	{
		logMessage(" >> triggerBusinessProcess () "+ this.getClass().getName());
        PaymentPreProcess loPaymentPreProcess = new PaymentPreProcess();
        BPMProcess loBPMProcess = new BPMProcess();
        loBPMProcess.setBusinessProcessName(ProcessPoolProperties.ProcessWorkflowName);
        String lsUUID = getUuid();
        loBPMProcess.setUniqueBusinessKey(lsUUID);
        loBPMProcess.setCorrelationValue(lsUUID);
        
        Map<String,Object> loVariables = new HashMap<String,Object>();
        loVariables.put(VanquishConstants.PROCESS_WORKFLOW_NAME, ProcessPoolProperties.ProcessWorkflowName);
        loVariables.put(VanquishConstants.PROCESS_COMPLETED, "");
	    loVariables.put("duplicate", false);
	    loVariables.put(VanquishConstants.UNIQUE_BUSINESS_KEY,  lsUUID);
	    loVariables.put(VanquishConstants.UNIQUE_REFERENCE_KEY, lsUUID);
        loBPMProcess.setProcessVaraiablesMap(loVariables);
        businessProcessPoolService.executeProcessInstance(loBPMProcess, loPaymentPreProcess);
        logMessage(" << triggerBusinessProcess () "+ this.getClass().getName());
	}//eof triggerBusinessProcess
	
	
	private String getUuid() 
	{
		logMessage(" >> getUuid () "+ this.getClass().getName());
		BigInteger loNewID = businessKeyService.fetchNewBusinessKey();
		DateFormat loDatTimFormat = new SimpleDateFormat("dd:MM:yyyy-HH:mm:ss:SSSS:");
		Calendar cal = Calendar.getInstance();
        String lsID = loDatTimFormat.format(cal.getTime());
        String lsNewID = new StrongUuidGenerator().getNextId();
        lsID = lsID + loNewID.toString() + ":"+ lsNewID;
        logMessage(" << getUuid () "+ this.getClass().getName());
        return lsID;
	}//eof getUuid
	
	private void logMessage(String lsMessage) {
		LOGGER.info(lsMessage);
	}
}
