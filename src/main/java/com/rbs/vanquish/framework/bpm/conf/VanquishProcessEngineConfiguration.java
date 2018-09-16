package com.rbs.vanquish.framework.bpm.conf;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Camunda Process Engine Configuration Java class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Configuration
@ComponentScan("com.rbs.vanquish")
@EnableTransactionManagement
public class VanquishProcessEngineConfiguration {
	
	 @Resource (name = "vanquishDataSource")
	 DataSource vanquishDataSource;
	
	 @Resource (name = "atomikosTransactionManager")
	 PlatformTransactionManager  platformTransactionManager; 
	
	 @Bean
	 public DataSource camundaDataSource() 
	 {
	     // Use a JNDI data source or read the properties from
	     // env or a properties file.
	     // Note: The following shows only a simple data source
	     // for In-Memory H2 database.
	    return vanquishDataSource;
	  }
	 
	  @Bean
	  public PlatformTransactionManager transactionManager() {
	    return platformTransactionManager;
	  }

	  @Bean
	  public SpringProcessEngineConfiguration processEngineConfiguration() {
	    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
	    

	    config.setDataSource(camundaDataSource());
	    config.setTransactionManager(transactionManager());

	    config.setDatabaseSchemaUpdate("true");
	    config.setHistory("audit");
	    config.setJobExecutorActivate(true);
	    config.setHistory(ProcessEngineConfiguration.HISTORY_FULL);
	    
	    //setting event handler to adjusty pool size

	    return config;
	  }
	  
	  @Bean
	  public ProcessEngineFactoryBean processEngine() {
	    ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
	    factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
	    return factoryBean;
	  }

	  @Bean
	  public RepositoryService repositoryService(ProcessEngine processEngine) {
	    return processEngine.getRepositoryService();
	  }

	  @Bean
	  public RuntimeService runtimeService(ProcessEngine processEngine) {
	    return processEngine.getRuntimeService();
	  }
	  
	  @Bean
	  public TaskService taskService(ProcessEngine processEngine) {
	    return processEngine.getTaskService();
	  }
	    
}
