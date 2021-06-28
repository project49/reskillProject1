package com.infy.UserMS.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infy.UserMS.utility.CustomPK;

@Entity
@Table(name = "wishlist")
public class Wishlist {
	
	@EmbeddedId
	private CustomPK customId;

	public CustomPK getCustomId() {
		return customId;
	}

	public void setCustomId(CustomPK customId) {
		this.customId = customId;
	}

}
