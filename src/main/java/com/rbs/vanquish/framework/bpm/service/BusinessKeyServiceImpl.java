package com.rbs.vanquish.framework.bpm.service;

import java.math.BigInteger;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rbs.vanquish.framework.bpm.entities.BuinessKeySequenceFetcher;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Service implemenation to fetch business key for a process instance on its starts-up.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Singleton
public class BusinessKeyServiceImpl implements BusinessKeyService {
	
	@Autowired
	private EntityManager entityManager = null;

	@Override
	public BigInteger fetchNewBusinessKey() {
		return getNextValue();
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private  BigInteger getNextValue() 
	{
        BuinessKeySequenceFetcher loSequenceFetcher = new BuinessKeySequenceFetcher();
        entityManager.persist(loSequenceFetcher);
        entityManager.flush();
        return BigInteger.valueOf(loSequenceFetcher.getId());
    }//eof getNextValue

}
