package com.rbs.vanquish.framework.bpm.config;
/** --------------------------------------------------------------------------------------------------------
 * Description    : ProcessPool Properties configuration class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessPoolProperties {
	
	 @Value("${vanquish.process.pool.maxsize}")
	 public static Integer MaximumPoolSize;
	 

	 @Value("${vanquish.process.workflow.name}")
	 public static String ProcessWorkflowName;

}
