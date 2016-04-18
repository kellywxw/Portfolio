package Chop.model;

import java.util.Date;

/**
 * Administrators is a simple, plain old java objects (POJO).
 * Well, almost (it extends {@link Persons}).
 */
public class Administrators extends Persons {
	protected Date lastLogin;
	
	public Administrators(String userName, String password, String firstName, String lastName, String email, Date lastLogin) {
		super(userName, password, firstName, lastName, email);
		this.lastLogin = lastLogin;
	}
	
	public Administrators(String userName) {
		super(userName);
	}

	/** Getters and setters. */
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}	
}
