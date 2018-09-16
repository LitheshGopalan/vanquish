package com.rbs.vanquish.framework.bpm.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/** --------------------------------------------------------------------------------------------------------
 * Description    : JMS Properties configuration class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Configuration
@ConfigurationProperties("vanquish.mq")
public class JmsProperties {

	 @Value("${vanquish.mq.host}")
	 public static String host;
	 @Value("${vanquish.mq.port}")
	 public static Integer port;
	 @Value("${vanquish.mq.ccsid}")
	 public static Integer ccsid;
	 @Value("${vanquish.mq.queuemanager}")
	 public static String queuemanager;
	 @Value("${vanquish.mq.channel}")
	 public static String channel;
	 @Value("${vanquish.mq.username}")
	 public static String username;
	 @Value("${vanquish.mq.password}")
	 public static String password;
	 @Value("${vanquish.mq.receivetimeout}")
	 public static long receiveTimeout;
	 @Value("${vanquish.mq.cachesize}")
	 public static int cachesize;

	 
	 //queue details
	 @Value("${vanquish.mq.process.main-queue.queue-name}")
	 public static String Main_Queue_Name; 
	 
	 @Value("${vanquish.mq.process.main-queue.concurrency}")
	 public static int Main_Queue_Concurrency; 
	 
	 @Value("${vanquish.mq.process.internal-queues}")
	 public static List<Map<String,String>> Internal_Queue_List; 
	 
	 
	 
	 
}
