package Chop.model;

import java.util.Date;

/**  Accomplished_huh BOOLEAN DEFAULT FALSE,
   DueDate DATETIME,
   ItemId INT, */

public class Task extends Items{
	protected boolean accomplished_huh;
	protected Date dueDate;

	
	public Task(int itemId, String description, boolean shareItems_huh, Date dateCreated, 
			Users user, Category category, boolean accomplished_huh, Date dueDate) {
		super(itemId,description,shareItems_huh,dateCreated,user,category);
		this.accomplished_huh = accomplished_huh;
		this.dueDate = dueDate;
	}
	
	public Task(String description, boolean shareItems_huh, Date dateCreated, 
			Users user, Category category, boolean accomplished_huh, Date dueDate) {
		super(description,shareItems_huh,dateCreated,user,category);
		this.accomplished_huh = accomplished_huh;
		this.dueDate = dueDate;
	}
	
	public Task(int itemId) {
		super(itemId);
	}
	

	public boolean getAccomplished_huh() {
		return accomplished_huh;
	}

	public void setAccomplished_huh(boolean accomplished_huh) {
		this.accomplished_huh = accomplished_huh;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


}
