package com.infy.ProductMS.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infy.ProductMS.utility.CustomerPK;

@Entity
@Table(name = "subscribed_product")
public class SubscribedProduct {
	
	@EmbeddedId
	private CustomerPK customId;

	public CustomerPK getCustomId() {
		return customId;
	}

	public void setCustomId(CustomerPK customId) {
		this.customId = customId;
	}
	
	
}
