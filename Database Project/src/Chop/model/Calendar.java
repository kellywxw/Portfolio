package Chop.model;

import java.util.Date;

/** DateCreated DATETIME NOT NULL, 
  CalendarBackground LONGBLOB,
  IsPublic_huh BOOLEAN DEFAULT FALSE,
  UserName VARCHAR(255),*/

public class Calendar {
	protected Date dateCreated;
	protected String calendarBackground;
	protected boolean isPublic_huh;
	protected Users users;
	
	// This constructor can be used for reading records from MySQL, where we have all fields,
	// including the PostId.
	public Calendar(Date dateCreated, String calendarBackground, boolean isPublic_huh, Users users) {
		this.dateCreated = dateCreated;
		this.calendarBackground = calendarBackground;
		this.isPublic_huh = isPublic_huh;
		this.users = users;
	}
	

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setPostId(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCalendarBackground() {
		return calendarBackground;
	}

	public void setCalendarBackground(String calendarBackground) {
		this.calendarBackground = calendarBackground;
	}

	public boolean getIsPublic_huh() {
		return isPublic_huh;
	}

	public void setIsPublic_huh(boolean isPublic_huh) {
		this.isPublic_huh = isPublic_huh;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}