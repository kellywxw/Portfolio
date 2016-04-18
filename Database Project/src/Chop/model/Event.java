package Chop.model;

import java.util.Date;

/**StartDate  DATETIME,
EndDate  DATETIME,
ItemId INT, */

public class Event extends Items{
	protected Date startDate;
	protected Date endDate;

	
	public Event(int itemId, String description, boolean shareItems_huh, Date dateCreated, 
			Users user, Category category, Date startDate, Date endDate) {
		super(itemId,description,shareItems_huh,dateCreated,user,category);
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Event(String description, boolean shareItems_huh, Date dateCreated, 
			Users user, Category category, Date startDate, Date endDate) {
		super(description,shareItems_huh,dateCreated,user,category);
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Event(int itemId) {
		super(itemId);
	}
	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


}
