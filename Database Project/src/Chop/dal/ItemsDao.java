package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
ItemId INTEGER AUTO_INCREMENT,
Description LONGTEXT,
ShareItems_huh BOOLEAN DEFAULT FALSE,
DateCreated DATETIME,
UserName VARCHAR(255),
CategoryId INTEGER, */

public class ItemsDao {
	protected ConnectionManager connectionManager;

	private static ItemsDao instance = null;
	protected ItemsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ItemsDao getInstance() {
		if(instance == null) {
			instance = new ItemsDao();
		}
		return instance;
	}

	public Items create(Items item) throws SQLException {
		String insertItem =
			"INSERT INTO Items(Description,ShareItems_huh,DateCreated,UserName,CategoryId) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertItem,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, item.getDescription());
			insertStmt.setBoolean(2, item.getShareItems_huh());
			insertStmt.setTimestamp(3, new Timestamp(item.getDateCreated().getTime()));
			insertStmt.setString(4, item.getUser().getUserName());
			insertStmt.setInt(5, item.getCategory().getCategoryId());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int itemId = -1;
			if(resultKey.next()) {
				itemId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			item.setItemId(itemId);
			
			return item;
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

	public Items updateShareItem(Items item, boolean newIsPublic) throws SQLException {
		String updateShareItem = "UPDATE Items SET ShareItems_huh=? WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateShareItem);
			updateStmt.setBoolean(1, newIsPublic);
			updateStmt.setInt(2, item.getItemId());
			updateStmt.executeUpdate();

			item.setShareItems_huh(newIsPublic);
			return item;
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
	
	public Items updateItemDescription(Items item, String newItemDesc) throws SQLException {
		String updateItemDescription = "UPDATE Items SET Description=? WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateItemDescription);
			updateStmt.setString(1, newItemDesc);
			updateStmt.setInt(2, item.getItemId());
			updateStmt.executeUpdate();

			item.setDescription(newItemDesc);
			return item;
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
	 * Get the BlogComments record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single BlogComments instance.
	 * Note that we use BlogPostsDao and BlogUsersDao to retrieve the referenced
	 * BlogPosts and BlogUsers instances.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the BlogComments, BlogPosts, BlogUsers tables and then build each object.
	 */
	
	/**
	ItemId INTEGER AUTO_INCREMENT,
	Description LONGTEXT,
	ShareItems_huh BOOLEAN DEFAULT FALSE,
	DateCreated DATETIME,
	UserName VARCHAR(255),
	CategoryId INTEGER, */
	public Items getItemByItemId(int itemId) throws SQLException {
		String selectItem =
			"SELECT itemId,Description,ShareItems_huh,DateCreated,UserName,CategoryId " +
			"FROM Items " +
			"WHERE itemId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectItem);
			selectStmt.setInt(1, itemId);
			results = selectStmt.executeQuery();
			CategoryDao categoryDao = CategoryDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultItemId = results.getInt("ItemId");
				String description = results.getString("Description");
				Boolean shareItems_huh =  results.getBoolean("ShareItems_huh");
				Date dateCreated = results.getDate("DateCreated");
				String userName = results.getString("UserName");
				int categoryId = results.getInt("CategoryId");
				
				Users user = usersDao.getUserFromUserName(userName);
				Category category = categoryDao.getCategoryByCategoryId(categoryId);
				Items item = new Items(resultItemId, description, shareItems_huh, dateCreated, user, category);
				return item;
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

	/**
	 * Get the all the BlogComments for a user.
	 */

	public List<Items> getItemsByUserName(String userName) throws SQLException {
		List<Items> items = new ArrayList<Items>();
		String selectItems =
			"SELECT ItemId,Description,ShareItems_huh,DateCreated,userName,CategoryId " +
			"FROM Items " +
			"WHERE userName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectItems);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			CategoryDao categoryDao = CategoryDao.getInstance();
			while(results.next()) {
				int itemId = results.getInt("ItemId");
				String description = results.getString("Description");
				Boolean shareItems_huh =  results.getBoolean("ShareItems_huh");
				Date dateCreated = results.getDate("DateCreated");
				String resultuserName = results.getString("UserName");
				int categoryId = results.getInt("CategoryId");

				Users user = usersDao.getUserFromUserName(resultuserName);
				Category category = categoryDao.getCategoryByCategoryId(categoryId);
				Items item = new Items(itemId, description, shareItems_huh, dateCreated, user, category);
				items.add(item);
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
		return items;
	}
	
	/**
	 * This runs a DELETE statement.
	 */
	public Items delete(Items item) throws SQLException {
		String deleteItem = "DELETE FROM Items WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteItem);
			deleteStmt.setInt(1, item.getItemId());
			deleteStmt.executeUpdate();

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
}
