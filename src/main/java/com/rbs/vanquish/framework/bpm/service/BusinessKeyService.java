package com.rbs.vanquish.framework.bpm.service;

import java.math.BigInteger;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Service interface to fetch business key for a process instance on its starts-up.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public interface BusinessKeyService {
	
	public BigInteger fetchNewBusinessKey();

}
