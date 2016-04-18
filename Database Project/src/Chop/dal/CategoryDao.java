package Chop.dal;
import Chop.model.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** CategoryId INTEGER AUTO_INCREMENT,
  CategoryName ENUM ('Work', 'School', 'Social', 'Other'),
  ShareCategory_huh BOOLEAN DEFAULT FALSE,
  UserName VARCHAR(255) */

public class CategoryDao {
	protected ConnectionManager connectionManager;

	private static CategoryDao instance = null;
	protected CategoryDao() {
		connectionManager = new ConnectionManager();
	}
	public static CategoryDao getInstance() {
		if(instance == null) {
			instance = new CategoryDao();
		}
		return instance;
	}

	public Category create(Category category) throws SQLException {
		String insertCategory =
			"INSERT INTO Category(CategoryName,ShareCategory_huh,UserName) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// Category has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertCategory,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, category.getCategoryName());
			insertStmt.setBoolean(2, category.getShareCategory_huh());
			insertStmt.setString(3, category.getUsers().getUserName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int categoryId = -1;
			if(resultKey.next()) {
				categoryId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			category.setCategoryId(categoryId);
			
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	/**
	 * Update the ShareCategory_huh of the Category instance.
	 * This runs a UPDATE statement.
	 */
	/** CategoryId INTEGER AUTO_INCREMENT,
	  CategoryName ENUM ('Work', 'School', 'Social', 'Other'),
	  ShareCategory_huh BOOLEAN DEFAULT FALSE,
	  UserName VARCHAR(255) */
	
	public Category updateShareCategory(Category category, boolean newShareCategory) throws SQLException {
		String updateShareCategory = "UPDATE Category SET ShareCategory_huh=? WHERE CategoryId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateShareCategory);
			updateStmt.setBoolean(1, newShareCategory);
			updateStmt.setInt(2, category.getCategoryId());
			updateStmt.executeUpdate();

			// Update the calendar param before returning to the caller.
			category.setShareCategory_huh(newShareCategory);
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
		

	/**
	 * Delete the Calendar instance.
	 * This runs a DELETE statement.
	 */
	public Category delete(Category category) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE SET NULL. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteCategory = "DELETE FROM Category WHERE CategoryId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCategory);
			deleteStmt.setInt(1, category.getCategoryId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogPosts instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	
	public int getCategoryIDByUserNameCategoryName(String userName, String categoryName) throws SQLException {
		String selectCategoryId =
			"SELECT CategoryId " +
			"FROM Category " +
			"WHERE UserName=? AND CategoryName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCategoryId);
			selectStmt.setString(1, userName);
			selectStmt.setString(2, categoryName);
			results = selectStmt.executeQuery();
		//	UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int categoryId= results.getInt("CategoryId");
				
			//	Users user = usersDao.getUserFromUserName(userName);
				return categoryId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return 0;
	}
	

	//getCategoryByCategoryId(categoryId)
	/** CategoryId INTEGER AUTO_INCREMENT,
	  CategoryName ENUM ('Work', 'School', 'Social', 'Other'),
	  ShareCategory_huh BOOLEAN DEFAULT FALSE,
	  UserName VARCHAR(255) */

	public Category getCategoryByCategoryId(int categoryId) throws SQLException {
		String selectCategory =
			"SELECT CategoryId,CategoryName,ShareCategory_huh,UserName " +
			"FROM Category " +
			"WHERE CategoryId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCategory);
			selectStmt.setInt(1, categoryId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultCategoryId= results.getInt("CategoryId");
				String categoryName = results.getString("CategoryName");
				boolean shareCategory_huh = results.getBoolean("ShareCategory_huh");
				String userName = results.getString("UserName");
				
				Users user = usersDao.getUserFromUserName(userName);
				Category category = new Category(resultCategoryId, categoryName,
						shareCategory_huh, user);
				return category;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	
	/** CategoryId INTEGER AUTO_INCREMENT,
	  CategoryName ENUM ('Work', 'School', 'Social', 'Other'),
	  ShareCategory_huh BOOLEAN DEFAULT FALSE,
	  UserName VARCHAR(255) */
	
	public List<Category> getCategoriesForUser(String userName) throws SQLException {
		List<Category> categories = new ArrayList<Category>();
		String selectCategory =
			"SELECT CategoryId,CategoryName,ShareCategory_huh,UserName " +
			"FROM Category " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCategory);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int categoryId = results.getInt("CategoryId");
				String categoryName = results.getString("CategoryName");
				boolean shareCategory_huh = results.getBoolean("ShareCategory_huh");
				Users user = usersDao.getUserFromUserName(userName);
				Category category = new Category(categoryId, categoryName, shareCategory_huh,
						user);
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return categories;
	}
}
