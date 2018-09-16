package com.rbs.vanquish.framework.bpm.service;

import java.math.BigInteger;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rbs.vanquish.framework.bpm.entities.AddressKeySequenceFetcher;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Service implementation to fetch address key for a node while it starts-up.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class AddressKeyServiceImpl implements AddressKeyService {

	@Autowired
	private EntityManager entityManager = null;
	
	@Override
	public BigInteger fetchNewAddressKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private  BigInteger getNextValue() 
	{
		AddressKeySequenceFetcher loAddressKeySequenceFetcher = new AddressKeySequenceFetcher();
        entityManager.persist(loAddressKeySequenceFetcher);
        entityManager.flush();
        return BigInteger.valueOf(loAddressKeySequenceFetcher.getId());
    }//eof getNextValue

}
