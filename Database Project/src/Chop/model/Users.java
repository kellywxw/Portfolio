package Chop.model;

import java.sql.Timestamp;


public class Users extends Persons {
	protected String profilePicture;
	protected String about;
	protected int age;
	protected Timestamp lastLogin;
	protected String gender;
	
	
	public Users(String userName, String password, String firstName, String lastName, String email,
			String profilePicture, String about,int age,Timestamp lastLogin, String gender) {
		super(userName, password, firstName, lastName, email);
		this.profilePicture = profilePicture;
		this.about = about;
		this.age = age;
		this.lastLogin = lastLogin;
		this.gender = gender;
	}
	
	
	public Users(String userName) {
		super(userName);
	}
	
	
	/** Getters and setters. */

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	

}
