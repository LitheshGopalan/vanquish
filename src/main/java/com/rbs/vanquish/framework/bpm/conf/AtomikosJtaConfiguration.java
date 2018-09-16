package com.rbs.vanquish.framework.bpm.conf;

import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

//import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.ibm.mq.jms.MQXAQueueConnectionFactory;

/** --------------------------------------------------------------------------------------------------------
 * Description    : Atomikos JTA Configuration  class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Configuration
public class AtomikosJtaConfiguration {
	
	@Resource (name = "mqXAQueueConnectionFactory")
	private MQXAQueueConnectionFactory mqXAQueueConnectionFactory;
	
	private final int VANQUISH_GLOBAL_TIMEOUT_MIILISECS = 10000; // 10 Seconds 1 Sec is 1000 MilliSec
	
	public void tailorProperties(Properties properties) {
		properties.setProperty( "hibernate.transaction.manager_lookup_class",  TransactionManagerLookup.class.getName());
	}
	
	@Bean(name = "userTransaction")
	public UserTransaction userTransaction() throws Throwable 
	{
		UserTransactionImp userTransactionImp = new UserTransactionImp();
	    userTransactionImp.setTransactionTimeout(VANQUISH_GLOBAL_TIMEOUT_MIILISECS);
	    return userTransactionImp;
	}//eof userTransaction

	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	public TransactionManager atomikosTransactionManager() throws Throwable 
	{
		UserTransactionManager userTransactionManager = new UserTransactionManager();
	    userTransactionManager.setForceShutdown(false);
	    return userTransactionManager;
	}//eof atomikosTransactionManager

	@Bean(name = "transactionManager")
	@DependsOn({ "userTransaction", "atomikosTransactionManager" })
	public PlatformTransactionManager transactionManager() throws Throwable 
	{
		UserTransaction userTransaction = userTransaction();
	    TransactionManager atomikosTransactionManager = atomikosTransactionManager();
	    return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
	}//eof transactionManager
	
	@Bean(name = "connectionFactory", initMethod = "init", destroyMethod = "close")
	public ConnectionFactory connectionFactory() 
	{
		AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
		atomikosConnectionFactoryBean.setUniqueResourceName("vanquish");
		atomikosConnectionFactoryBean.setLocalTransactionMode(false);
		atomikosConnectionFactoryBean.setXaConnectionFactory(mqXAQueueConnectionFactory);
		return atomikosConnectionFactoryBean;
	}//eof connectionFactory
	
}//eof AtomikosJtaConfiguration
