package Chop.model;

/**CategoryId INTEGER AUTO_INCREMENT,
  CategoryName ENUM ('Work', 'School', 'Social', 'Other'),
  ShareCategory_huh BOOLEAN DEFAULT FALSE,
  UserName VARCHAR(255)*/

public class Category {
	protected int categoryId;
	protected String categoryName;
	protected boolean shareCategory_huh;
	protected Users users;
	
	// This constructor can be used for reading records from MySQL, where we have all fields,
	// including the PostId.
	public Category(int categoryId, String categoryName, boolean shareCategory_huh, Users users) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.shareCategory_huh = shareCategory_huh;
		this.users = users;
	}
	
	public Category(String categoryName, boolean shareCategory_huh, Users users) {
		this.categoryName = categoryName;
		this.shareCategory_huh = shareCategory_huh;
		this.users = users;
	}
	
	public Category(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean getShareCategory_huh() {
		return shareCategory_huh;
	}

	public void setShareCategory_huh(boolean shareCategory_huh) {
		this.shareCategory_huh = shareCategory_huh;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}