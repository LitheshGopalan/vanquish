package com.rbs.vanquish.framework.bpm.conf;

/** --------------------------------------------------------------------------------------------------------
 * Description    : Camunda ID Generation Configuration Java class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
import org.camunda.bpm.engine.impl.db.DbIdGenerator;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.CamundaHistoryLevelAutoHandlingConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Use {@link StrongUuidGenerator} to avoid potential problems in cluster environments with {@link DbIdGenerator}
 */
@Configuration
public class CamundaIdGeneratorConfiguration extends AbstractCamundaConfiguration implements CamundaHistoryLevelAutoHandlingConfiguration {

  private  final Logger LOGGER = LoggerFactory.getLogger(CamundaIdGeneratorConfiguration.class);
  @Override
  public void preInit(SpringProcessEngineConfiguration configuration) {
    configuration.setIdGenerator(new StrongUuidGenerator());
  }
  
  private  void logMessage(String lsMessage) {
		 LOGGER.info(lsMessage);
	 }
}
