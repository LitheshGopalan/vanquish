package com.rbs.vanquish.framework.bpm.conf;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Rest Template Configuration Java class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestConfiguration {
	private  final Logger LOGGER = LoggerFactory.getLogger(RestConfiguration.class);

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public RestTemplate createRestTemplate() 
  {
    RestTemplate restTemplate = new RestTemplate();
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
    jsonMessageConverter.setObjectMapper(objectMapper);
    messageConverters.add(jsonMessageConverter);
    restTemplate.setMessageConverters(messageConverters);
    return restTemplate;
  }//eof createRestTemplate
  
  private  void logMessage(String lsMessage) {
		 LOGGER.info(lsMessage);
	 }
}
