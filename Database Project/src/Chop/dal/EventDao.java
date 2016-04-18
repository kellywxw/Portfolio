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
//Users user, Category category, Date startDate, Date endDate)

public class EventDao extends ItemsDao {
		private static EventDao instance = null;
		protected EventDao() {
			super();
		}
		public static EventDao getInstance() {
			if(instance == null) {
				instance = new EventDao();
			}
			return instance;
		}

	
	public Event create(Event event) throws SQLException {
		//Insert into the superclass table first.
		Items newItem = create(new Items(event.getDescription(), event.getShareItems_huh(), event.getDateCreated(),
				event.getUser(), event.getCategory()));
						
		String insertEvent =
			"INSERT INTO Event(ItemId,StartDate,EndDate) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		//ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertEvent);
			insertStmt.setInt(1, newItem.getItemId());
			insertStmt.setTimestamp(2, new Timestamp(event.getStartDate().getTime()));
			insertStmt.setTimestamp(3, new Timestamp(event.getEndDate().getTime()));
			insertStmt.executeUpdate();
			return event;
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

	/**
	 * Delete the Event instance.
	 * This runs a DELETE statement.
	 */
	public Event delete(Event event) throws SQLException {
		String deleteEvent = "DELETE FROM Event WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteEvent);
			deleteStmt.setInt(1, event.getItemId());
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

	public Event updateStartDate(Event event, Date newStartDate) throws SQLException {
		String updateDueDate = "UPDATE Event SET StartDate=? WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateDueDate);
			updateStmt.setTimestamp(1, new Timestamp(newStartDate.getTime()));
			updateStmt.setInt(2, event.getItemId());
			updateStmt.executeUpdate();

			event.setStartDate(newStartDate);
			return event;
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
	
	public Event updateEndDate(Event event, Date newEndDate) throws SQLException {
		String updateDueDate = "UPDATE Event SET EndDate=? WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateDueDate);
			updateStmt.setTimestamp(1, new Timestamp(newEndDate.getTime()));
			updateStmt.setInt(2, event.getItemId());
			updateStmt.executeUpdate();

			event.setEndDate(newEndDate);
			return event;
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
	 * Get the Events by Item ID
	 */
	public Event getEventByItemId(int itemId) throws SQLException {
		String selectEvent =
			"SELECT Event.ItemId AS ItemId,Description,ShareItems_huh,DateCreated,UserName,CategoryId,StartDate,EndDate " +
			"FROM Event INNER JOIN Items " +
			"  ON Event.ItemId = Items.ItemId " +
			"WHERE Event.ItemId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEvent);
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
				Timestamp startDate = results.getTimestamp("StartDate");
				Timestamp endDate = results.getTimestamp("EndDate");
				
				Users user = usersDao.getUserFromUserName(userName);
				Category category = categoryDao.getCategoryByCategoryId(categoryId);
				Event event = new Event(resultItemId, description, shareItems_huh,
						dateCreated, user, category, startDate, endDate);
				return event;
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
	 * Get the all the Events for a user.
	 */
	public List<Event> getEventsByUserName(String userName) throws SQLException {
		List<Event> events = new ArrayList<Event>();
		String selectEvents =
			"SELECT * " +
			"FROM Event INNER JOIN Items " +
			"  ON Event.ItemId = Items.ItemId " +
			"WHERE Items.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEvents);
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
				Timestamp startDate = results.getTimestamp("StartDate");
				Timestamp endDate = results.getTimestamp("EndDate");

				Users user = usersDao.getUserFromUserName(resultuserName);
				Category category = categoryDao.getCategoryByCategoryId(categoryId);
				Event event = new Event(itemId, description, shareItems_huh, dateCreated, user,
						category,startDate,endDate);
				events.add(event);
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
		return events;
	}
}
