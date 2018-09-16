package com.rbs.vanquish.framework.bpm.config;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Message Properties configuration class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:message.properties")
public class MessageProperties {
	
	@Value("${message.unabletoloadfromdb}")
	public static String unableToReadFromDatabase;
	
	@Value("${message.unabletostoretodb}")
	public static String unableToStoreToDatabase;
	
	@Value("${message.unabletodeletefromdb}")
	public static String unableToDeleteFromDatabase;
	
	@Value("${message.unabletofindprocessbasedonbusinesskey}")
	public static String unableToFindProcessBasedOnBusinessKey;
	
	@Value("${message.toomanyprocessbasedonbusinesskey}")
	public static String tooManyProcessBasedOnBusinessKey;
	
	@Value("${message.unabletofindprocessbasedonprocessworkflowname}")
	public static String unableToFindProcessBasedOnProcessWorkflowName;
	
	@Value("${message.processnamecantbenullforthepool}")
	public static String processNameCantbeNullForThePool;
	
	@Value("${message.cantprocessnewinstanceshutdowninintiated}")
	public static String cantProcessNewInstanceShutdownInintiated;
	

}
