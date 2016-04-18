package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class AnalyticsFeedbackDao {
	protected ConnectionManager connectionManager;

	private static AnalyticsFeedbackDao instance = null;
	protected AnalyticsFeedbackDao() {
		connectionManager = new ConnectionManager();
	}
	public static AnalyticsFeedbackDao getInstance() {
		if(instance == null) {
			instance = new AnalyticsFeedbackDao();
		}
		return instance;
	}
	
	

	public AnalyticsFeedback create(AnalyticsFeedback analyticsFeedback) throws SQLException {
		String insertAnalyticsFeedback =
			"INSERT INTO AnalyticsFeedback(Title,AllowAnalytics,CompletionPercentage,HowMuchUsed,Chart,UserName) VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertAnalyticsFeedback);
			insertStmt.setString(1, analyticsFeedback.getTitle());
			insertStmt.setBoolean(2, analyticsFeedback.isAllowAnalytics());
			insertStmt.setDouble(3, analyticsFeedback.getCompletionPercentage());
			insertStmt.setInt(4, analyticsFeedback.getHowMuchUsed());
			insertStmt.setNull(5, Types.BLOB);
			insertStmt.setString(6, analyticsFeedback.getUser().getUserName());
			insertStmt.executeUpdate();
			return analyticsFeedback;
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
	
	public AnalyticsFeedback  getAnalyticsFeedbackByUserName(String userName) throws SQLException {
		String selectAnalyticsFeedback =
			"SELECT Title,AllowAnalytics,CompletionPercentage,HowMuchUsed,Chart,UserName " +
			"FROM AnalyticsFeedback " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAnalyticsFeedback);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				String title = results.getString("Title");
				boolean allowAnalytics = results.getBoolean("AllowAnalytics");
				double completionPercentage = results.getDouble("CompletionPercentage");
				int howMuchUsed = results.getInt("HowMuchUsed");
				String chart =  results.getString("Chart");
				String resultUserName = results.getString("UserName");
				Users user = usersDao.getUserFromUserName(resultUserName);
				AnalyticsFeedback analyticsFeedback = new AnalyticsFeedback(title, allowAnalytics,completionPercentage, howMuchUsed,chart, user);
				return analyticsFeedback;
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

	
	
	public AnalyticsFeedback updateAnalyticsFeedback(AnalyticsFeedback analyticsFeedback, String newTitle, boolean newAllowAnalytics, double newCompletionPercentage, int newUsage, String newChart) throws SQLException {
		String updateAnalyticsFeedback  = "UPDATE AnalyticsFeedback SET Title=?, " +
				 "AllowAnalytics=?, CompletionPercentage=?, HowMuchUsed=?, Chart=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAnalyticsFeedback);
			updateStmt.setString(1, newTitle);
			updateStmt.setBoolean(2, newAllowAnalytics);
			updateStmt.setDouble(3, newCompletionPercentage);
			updateStmt.setInt(4, newUsage);
			updateStmt.setString(5, newChart);
			updateStmt.setString(6,analyticsFeedback.getUser().getUserName());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			analyticsFeedback.setTitle(newTitle);
			analyticsFeedback.setAllowAnalytics(newAllowAnalytics);
			analyticsFeedback.setCompletionPercentage(newCompletionPercentage);
			analyticsFeedback.setHowMuchUsed(newUsage);
			analyticsFeedback.setChart(newChart);
			return analyticsFeedback;
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
	 * Delete the BlogPosts instance.
	 * This runs a DELETE statement.
	 */
	public AnalyticsFeedback delete(AnalyticsFeedback analyticsFeedback) throws SQLException {
		String deleteAnalyticsFeedback = "DELETE FROM AnalyticsFeedback WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAnalyticsFeedback);
			deleteStmt.setString(1, analyticsFeedback.getUser().getUserName());
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
