package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//int itemId, String description, boolean shareItems_huh, Date dateCreated, 
//Users user, Category category, boolean accomplished_huh, Date dueDate

public class TaskDao extends ItemsDao {
	private static TaskDao instance = null;
	protected TaskDao() {
		super();
	}
	public static TaskDao getInstance() {
		if(instance == null) {
			instance = new TaskDao();
		}
		return instance;
	}

	public Task create(Task task) throws SQLException {
		//Insert into the superclass table first.
		Items newItem = create(new Items(task.getDescription(), task.getShareItems_huh(), task.getDateCreated(),
			task.getUser(), task.getCategory()));
		
		String insertTask =
			"INSERT INTO Task(ItemId,Accomplished_huh,DueDate) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
	//	ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTask);
			insertStmt.setInt(1, newItem.getItemId());
			insertStmt.setBoolean(2, task.getAccomplished_huh());
			insertStmt.setTimestamp(3, new Timestamp(task.getDueDate().getTime()));
			insertStmt.executeUpdate();
			return task;
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
		}
	}
	
	public Task updateAccomplishedTask(Task task, boolean newIsPublic) throws SQLException {
		String updateAccomplishedTask = "UPDATE Task SET Accomplished_huh=? WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAccomplishedTask);
			updateStmt.setBoolean(1, newIsPublic);
			updateStmt.setInt(2, task.getItemId());
			updateStmt.executeUpdate();

			task.setShareItems_huh(newIsPublic);
			return task;
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
	
	public Task updateDueDate(Task task, Date newDueDate) throws SQLException {
		String updateDueDate = "UPDATE Task SET DueDate=? WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateDueDate);
			updateStmt.setTimestamp(1, new Timestamp(newDueDate.getTime()));
			updateStmt.setInt(2, task.getItemId());
			updateStmt.executeUpdate();

			task.setDueDate(newDueDate);
			return task;
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
	 * Delete the Task instance.
	 * This runs a DELETE statement.
	 */
	public Task delete(Task task) throws SQLException {
		String deleteTask = "DELETE FROM TASK WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTask);
			deleteStmt.setInt(1, task.getItemId());
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


	// Get the Task by Item ID.
	public Task getTaskByItemId(int itemId) throws SQLException {
		String selectTask =
			"SELECT Task.ItemId AS ItemId,Description,ShareItems_huh,DateCreated,UserName,CategoryId,Accomplished_huh,DueDate " +
			"FROM Task INNER JOIN Items " +
			"  ON Task.ItemId = Items.ItemId " +
			"WHERE Task.ItemId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTask);
			selectStmt.setInt(1, itemId);
			results = selectStmt.executeQuery();
			CategoryDao categoryDao = CategoryDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultItemId = results.getInt("ItemId");
				String description =  results.getString("Description");
				boolean shareItems_huh = results.getBoolean("ShareItems_huh");
				Date dateCreated = results.getDate("DateCreated");
				String userName = results.getString("UserName");
				int categoryId = results.getInt("CategoryId");
				boolean accomplished_huh = results.getBoolean("Accomplished_huh");
				Date dueDate = results.getDate("DueDate");
				
				Users user = usersDao.getUserFromUserName(userName);
				Category category = categoryDao.getCategoryByCategoryId(categoryId);
				Task task = new Task(resultItemId, description, shareItems_huh,
						dateCreated, user, category, accomplished_huh, dueDate);
				return task;
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


	// Get the all the Tasks for a user.
	public List<Task> getTasksByUserName(String userName) throws SQLException {
		List<Task> tasks = new ArrayList<Task>();
		String selectTasks =
			"SELECT * " +
			"FROM Task INNER JOIN Items " +
			"  ON Task.ItemId = Items.ItemId " +
			"WHERE Items.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTasks);
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
				boolean accomplished_huh = results.getBoolean("Accomplished_huh");
				Timestamp dueDate = results.getTimestamp("DueDate");

				Users user = usersDao.getUserFromUserName(resultuserName);
				Category category = categoryDao.getCategoryByCategoryId(categoryId);
				Task task = new Task(itemId, description, shareItems_huh, dateCreated, user,
						category,accomplished_huh,dueDate);
				tasks.add(task);
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
		return tasks;
	}
}
