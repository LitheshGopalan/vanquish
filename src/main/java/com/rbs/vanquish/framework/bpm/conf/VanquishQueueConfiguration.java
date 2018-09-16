package com.rbs.vanquish.framework.bpm.conf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.rbs.vanquish.framework.bpm.config.JmsProperties;
import com.rbs.vanquish.framework.bpm.message.VanquishBusinessProcessLauncher;
import com.rbs.vanquish.framework.bpm.message.VanquishInternalMessageLauncher;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Messaging Queue Configuration Java class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Configuration
@ComponentScan("com.rbs.vanquish")
@ComponentScan(basePackageClasses = {VanquishBusinessProcessLauncher.class})
public class VanquishQueueConfiguration {

	@Resource (name = "cachingConnectionFactory")
	private ConnectionFactory cachingConnectionFactory;
	
	private List<DefaultMessageListenerContainer> internalListernerList = null;
	 
	@Resource
	private VanquishBusinessProcessLauncher vanquishMainListener;
	
	@Resource
	private VanquishInternalMessageLauncher vanquishInternalMessageListener;
	
	@Bean
    public DefaultMessageListenerContainer vanquishMainListenerContainer()
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(cachingConnectionFactory);
        listenerContainer.setDestinationName(JmsProperties.Main_Queue_Name);
        listenerContainer.setMessageListener(vanquishMainListener);
        listenerContainer.setMaxConcurrentConsumers(JmsProperties.Main_Queue_Concurrency);
        listenerContainer.setSessionTransacted(true);
        return listenerContainer;
    }//eof vanquishMainListenerContainer
	
	@Bean
    public List<DefaultMessageListenerContainer> vanquishInternalListenerContainerList()
    {
		internalListernerList = new ArrayList<DefaultMessageListenerContainer>();
		List<Map<String,String>> loInternalQueueList = JmsProperties.Internal_Queue_List;
		Iterator<Map<String,String>> loIter = loInternalQueueList.iterator();
		
		while (loIter.hasNext())
		{
			Map<String,String> loInternalQueueMap = loIter.next();
	        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
	        listenerContainer.setConnectionFactory(cachingConnectionFactory);
	        String lsQueueName = (String) loInternalQueueMap.get("vanquish.mq.process.internal-queues.queue-name");
	        String lsConcurrency = (String) loInternalQueueMap.get("vanquish.mq.process.internal-queues.concurrency");
	        VanquishInternalMessageLauncher loVanquishInternalMessageListener = new VanquishInternalMessageLauncher(lsQueueName);
	        listenerContainer.setDestinationName(lsQueueName);
	        listenerContainer.setMessageListener(loVanquishInternalMessageListener);
	        listenerContainer.setMaxConcurrentConsumers(Integer.parseInt(lsConcurrency));
	        listenerContainer.setSessionTransacted(true);
	        internalListernerList.add(listenerContainer);
		}//eof while
        return internalListernerList;
    }//eof vanquishInternalListenerContainerList
	
	  // Serialize message content to json using TextMessage
	  @Bean
	  public MessageConverter jacksonJmsMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	  }
	

}
