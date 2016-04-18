package Chop.model;

import java.util.Date;

/**
ItemId INTEGER AUTO_INCREMENT,
Description LONGTEXT,
ShareItems_huh BOOLEAN DEFAULT FALSE,
DateCreated DATETIME,
UserName VARCHAR(255),
CategoryId INTEGER, */

public class Items {
	protected int itemId;
	protected String description;
	protected boolean shareItems_huh;
	protected Date dateCreated;
	protected Users user;
	protected Category category;
	
	public Items(int itemId, String description, boolean shareItems_huh, Date dateCreated, Users user, Category category) {
		this.itemId = itemId;
		this.description = description;
		this.shareItems_huh = shareItems_huh;
		this.dateCreated = dateCreated;
		this.user = user;
		this.category = category;
	}
	
	public Items(String description, boolean shareItems_huh, Date dateCreated, Users user, Category category) {
		this.description = description;
		this.shareItems_huh = shareItems_huh;
		this.dateCreated = dateCreated;
		this.user = user;
		this.category = category;
	}
	
	public Items(int itemId) {
		this.itemId = itemId;
	}
		
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getShareItems_huh() {
		return shareItems_huh;
	}

	public void setShareItems_huh(boolean shareItems_huh) {
		this.shareItems_huh = shareItems_huh;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
