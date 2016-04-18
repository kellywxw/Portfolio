package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


/** protected Date dateCreated;
	protected Blob calendarBackground;
	protected boolean isPublic_huh;
	protected Users users; */


public class CalendarDao {
	protected ConnectionManager connectionManager;

	private static CalendarDao instance = null;
	protected CalendarDao() {
		connectionManager = new ConnectionManager();
	}
	public static CalendarDao getInstance() {
		if(instance == null) {
			instance = new CalendarDao();
		}
		return instance;
	}

	public Calendar create(Calendar calendar) throws SQLException {
		String insertCalendar =
			"INSERT INTO Calendar(DateCreated,CalendarBackground,IsPublic_huh,UserName) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCalendar);
			insertStmt.setTimestamp(1, new Timestamp(calendar.getDateCreated().getTime()));
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setNull(2, Types.BLOB);
			insertStmt.setBoolean(3, calendar.getIsPublic_huh());
			insertStmt.setString(4, calendar.getUsers().getUserName());
			insertStmt.executeUpdate();
			
			return calendar;
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
	 * Update the IsPublic_huh of the Calendar instance.
	 * This runs a UPDATE statement.
	 */
	public Calendar updateCalendarIsPublic(Calendar calendar, boolean newIsPublic) throws SQLException {
		String updateCalendarIsPublic = "UPDATE Calendar SET IsPublic_huh=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCalendarIsPublic);
			updateStmt.setBoolean(1, newIsPublic);
			updateStmt.setString(2, calendar.getUsers().getUserName());
			updateStmt.executeUpdate();

			// Update the calendar param before returning to the caller.
			calendar.setIsPublic_huh(newIsPublic);
			return calendar;
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
	 * Update the CalendarBackground of the Calendar instance.
	 * This runs a UPDATE statement.
	 */
	public Calendar updateCalendarBackground(Calendar calendar, String newBackground) throws SQLException {
		String updateCalendarIsPublic = "UPDATE Calendar SET CalendarBackground=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCalendarIsPublic);
			updateStmt.setString(1, newBackground);
			updateStmt.setString(2, calendar.getUsers().getUserName());
			updateStmt.executeUpdate();

			calendar.setCalendarBackground(newBackground);
			return calendar;
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
	public Calendar delete(Calendar calendar) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE SET NULL. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteCalendar = "DELETE FROM Calendar WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCalendar);
			deleteStmt.setString(1, calendar.getUsers().getUserName());
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

	/**
	 * Get the Calendar for a user.
	 */
	/** protected Date dateCreated;
	protected Blob calendarBackground;
	protected boolean isPublic_huh;
	protected Users users; */

	public Calendar getCalendarForUser(String userName) throws SQLException {
		String selectCalendar =
			"SELECT DateCreated,CalendarBackground,IsPublic_huh,UserName " +
			"FROM Calendar " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCalendar);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				Timestamp dateCreated = results.getTimestamp("DateCreated");
				String calendarBackground = results.getString("CalendarBackground");
				boolean isPublic_huh = results.getBoolean("IsPublic_huh");
				Users user = usersDao.getUserFromUserName(userName);
				
				Calendar calendar = new Calendar(dateCreated, calendarBackground, isPublic_huh, user);
				return calendar;
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


}
