package com.rbs.vanquish.framework.bpm.entities;
/** --------------------------------------------------------------------------------------------------------
 * Description    : Class used to fetch address (An unique id for each instance of BPM application); these
 *                : unique ID is used for maintaining the maximum pool size across all the work-flow nodes;
 *                : A process instance can switch over or fail over to any other node in cloud and the same 
 *                : is taken care by the framework
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AddressKeySequenceFetcher {
	
	public AddressKeySequenceFetcher() {
		
	}
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Adress_Key_Sequence")
    @SequenceGenerator(name = "Adress_Key_Sequence", sequenceName = "PAYMENT_BUSINESS_KEY_SEQ")
    private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
