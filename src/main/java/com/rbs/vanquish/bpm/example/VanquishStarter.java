package com.rbs.vanquish.bpm.example;

import javax.annotation.PreDestroy;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rbs.vanquish.framework.bpm.pool.BusinessProcessPoolService;

@SpringBootApplication
@EnableAutoConfiguration
@EnableProcessApplication
@EnableJpaRepositories("com.rbs.vanquish")
/** --------------------------------------------------------------------------------------------------------------
 * Description    : A starter class written by the framework this class will initialize all the objects required.
 *                : to run the workflow application. It is important to configure the property file and config
 *                : file according to your workflow requirements; Any mistake in configuration will lead to an error 
 *                : and your declared java classes may not be exceuted.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
----------------------------------------------------------------------------------------------------------------- **/
public class VanquishStarter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VanquishStarter.class);
	
	private static BusinessProcessPoolService businessProcessPoolService = null;
	
	
	/**public static void main(String[] args) 
	{
		//Let us see how to create a new process pool and start submitting the process.
        businessProcessPoolService = BusinessProcessPoolImpl.getInstance(ProcessPoolProperties.ProcessWorkflowName, 
        ProcessPoolProperties.MaximumPoolSize);
    
        //Let us register shutdown hook for clean shutdown of the application
        Runtime.getRuntime().addShutdownHook(new VanquishShutdownHook(businessProcessPoolService));
        
		SpringApplication.run(VanquishStarter.class, args);
		
	}//eof main**/
	 
	 @PreDestroy
	 public void stopProcess() 
	 {
		 logMessage(" >> stopProcess () "+ VanquishStarter.class.getName());
		 businessProcessPoolService.performCleanShutdown();
		 logMessage(" << stopProcess () "+ VanquishStarter.class.getName());
	 }//eof stopProcess
	 
	 @EventListener
	 private void processPostDeploy(PostDeployEvent event) throws InterruptedException 
	 {
		 logMessage(" >> processPostDeploy () "+ VanquishStarter.class.getName());
		 setupListeners();
		 logMessage(" << processPostDeploy () "+ VanquishStarter.class.getName());
	 }//eof processPostDeploy
	 
	 private void setupListeners() 
	 {
		 logMessage(" >> setupListeners () "+ VanquishStarter.class.getName());
		 logMessage(" << setupListeners () "+ VanquishStarter.class.getName());
	 }//eof setupListeners
	 
	 private static void logMessage(String lsMessage) {
		 LOGGER.info(lsMessage);
	 }


}//eof main
