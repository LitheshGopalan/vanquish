package com.rbs.vanquish.framework.bpm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/** --------------------------------------------------------------------------------------------------------
 * Description    : DataSource Properties configuration class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Configuration
@ConfigurationProperties("vanquish.datasource")
public class DataSourceProperties {
	
	 @Value("${vanquish.datasource.url}")
	 public  String url;
	 
	 @Value("${vanquish.datasource.username}")
	 public  String username;
	 
	 public  String password;
	 
	 @Value("${vanquish.datasource.driverclass}")
	 public  String driverclass;
}
