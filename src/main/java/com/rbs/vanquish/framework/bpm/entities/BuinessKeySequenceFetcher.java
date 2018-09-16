package com.rbs.vanquish.framework.bpm.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Class used to fetch business key (An unique id for each instance of BPM workflow); these
 *                : unique ID is used as business key and is required for correlating the business process 
 *                : during asynchronous processing.
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
@Entity
public class BuinessKeySequenceFetcher {
	
	public BuinessKeySequenceFetcher() {
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Payment_BusinessKey_Sequence")
    @SequenceGenerator(name = "Payment_BusinessKey_Sequence", sequenceName = "PAYMENT_BUSINESS_KEY_SEQ")
    private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
