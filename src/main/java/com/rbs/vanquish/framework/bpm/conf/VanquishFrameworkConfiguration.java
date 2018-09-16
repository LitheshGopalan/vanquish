package com.rbs.vanquish.framework.bpm.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.rbs.vanquish.framework.bpm.service.AddressKeyService;
import com.rbs.vanquish.framework.bpm.service.AddressKeyServiceImpl;
import com.rbs.vanquish.framework.bpm.service.BusinessKeyService;
import com.rbs.vanquish.framework.bpm.service.BusinessKeyServiceImpl;
import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsService;
import com.rbs.vanquish.framework.bpm.service.ProcessInstanceDetailsServiceImpl;

@Configuration
@ComponentScan("com.rbs.vanquish")
@EnableTransactionManagement
/** --------------------------------------------------------------------------------------------------------
 * Description    : Vnquish framework Configuration Java class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class VanquishFrameworkConfiguration {
	
	  @Bean(name = "businessKeyService")
	  public BusinessKeyService businessKeyService() {
		  return new BusinessKeyServiceImpl();
	  }
	 
	  @Bean(name = "addressKeyService")
	  public AddressKeyService addressKeyService() {
		  return new AddressKeyServiceImpl();
	  }
	 
	  @Bean(name = "addressKeyService")
	  public ProcessInstanceDetailsService processInstanceDetailsService() {
		  return new ProcessInstanceDetailsServiceImpl();
	  }

}
