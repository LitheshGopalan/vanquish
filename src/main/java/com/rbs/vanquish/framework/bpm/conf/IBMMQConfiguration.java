package com.rbs.vanquish.framework.bpm.conf;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQXAQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.rbs.vanquish.framework.bpm.config.JmsProperties;
import com.rbs.vanquish.framework.bpm.exception.VanquishRuntimeException;

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
public class IBMMQConfiguration {
	
	
	
	@Bean(name = "mqXAQueueConnectionFactory")
	public MQXAQueueConnectionFactory mqXAQueueConnectionFactory() 
	  {
		  MQXAQueueConnectionFactory mqQueueConnectionFactory = new MQXAQueueConnectionFactory();
	      mqQueueConnectionFactory.setHostName(JmsProperties.host);
	      try 
	      {
	          mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
	          mqQueueConnectionFactory.setCCSID(JmsProperties.ccsid);
	          mqQueueConnectionFactory.setChannel(JmsProperties.channel);
	          mqQueueConnectionFactory.setPort(JmsProperties.port);
	          mqQueueConnectionFactory.setQueueManager(JmsProperties.queuemanager);
	      } 
	      catch (Exception aException) 
	      {
	    	  aException.printStackTrace();
	    	  throw new VanquishRuntimeException(aException);
	      }
	      return mqQueueConnectionFactory;
	  }//EOF mqXAQueueConnectionFactory
	
	@Bean(name = "mqQueueConnectionFactory")
	public MQQueueConnectionFactory mqQueueConnectionFactory() 
	  {
	      MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
	      mqQueueConnectionFactory.setHostName(JmsProperties.host);
	      try 
	      {
	          mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
	          //mqQueueConnectionFactory.setCCSID(JmsProperties.ccsid);
	          mqQueueConnectionFactory.setChannel(JmsProperties.channel);
	          mqQueueConnectionFactory.setPort(JmsProperties.port);
	          mqQueueConnectionFactory.setQueueManager(JmsProperties.queuemanager);
	      } 
	      catch (Exception aException) 
	      {
	    	  aException.printStackTrace();
	    	  throw new VanquishRuntimeException(aException);
	      }
	      return mqQueueConnectionFactory;
	  }//EOF mqQueueConnectionFactory
	  
	  @Bean(name = "userCredentialsConnectionFactoryAdapter")
	  UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) 
	  {
	      UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
	      userCredentialsConnectionFactoryAdapter.setUsername(JmsProperties.username);
	      userCredentialsConnectionFactoryAdapter.setPassword(JmsProperties.password);
	      userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
	      return userCredentialsConnectionFactoryAdapter;
	  }//eof userCredentialsConnectionFactoryAdapter
	  
	  @Bean(name = "userCredentialsXAConnectionFactoryAdapter")
	  UserCredentialsConnectionFactoryAdapter userCredentialsXAConnectionFactoryAdapter(MQXAQueueConnectionFactory aMQXAQueueConnectionFactory) 
	  {
	      UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
	      userCredentialsConnectionFactoryAdapter.setUsername(JmsProperties.username);
	      userCredentialsConnectionFactoryAdapter.setPassword(JmsProperties.password);
	      userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(aMQXAQueueConnectionFactory);
	      return userCredentialsConnectionFactoryAdapter;
	  }//eof userCredentialsConnectionFactoryAdapter
	  
	  
	  
	  //Use @Primary annotation to tell Spring use this bean but not MQQueueConnectionFactory.
	  @Bean(name = "cachingConnectionFactory")
	  //@Primary
	  public CachingConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
	      CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
	      cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
	      cachingConnectionFactory.setSessionCacheSize(JmsProperties.cachesize);
	      cachingConnectionFactory.setReconnectOnException(true);
	      return cachingConnectionFactory;
	  }//eof 
	  
	  @Bean(name = "myFactory")
	  public JmsListenerContainerFactory<?> myFactory(CachingConnectionFactory cachingConnectionFactory,
	  DefaultJmsListenerContainerFactoryConfigurer configurer) 
	  {
	      DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	      factory.setSessionTransacted(true);
	      configurer.configure(factory, cachingConnectionFactory);
	      return factory;
	  }//EOF JmsListenerContainerFactory
	  
	 /** @Bean(name = "jmsTransactionManager")
	  public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) 
	  {
	      JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
	      jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
	      return jmsTransactionManager;
	  }//eof jmsTransactionManager**/
	  
	  @Bean(name = "jmsOperations")
	  public JmsOperations jmsOperations(CachingConnectionFactory cachingConnectionFactory) 
	  {
	      JmsTemplate jmsTemplate = new JmsTemplate(mqXAQueueConnectionFactory());
	      jmsTemplate.setReceiveTimeout(JmsProperties.receiveTimeout);
	      return jmsTemplate;
	  }//eof jmsOperations

}
