package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class FriendRequestDao {
	protected ConnectionManager connectionManager;

	private static FriendRequestDao instance = null;
	protected FriendRequestDao() {
		connectionManager = new ConnectionManager();
	}
	public static FriendRequestDao getInstance() {
		if(instance == null) {
			instance = new FriendRequestDao();
		}
		return instance;
	}

	public FriendRequest create(FriendRequest friendRequest) throws SQLException {
		String insertFriendRequest =
			"INSERT INTO FriendRequest(UserRequestor,UserRequestee) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFriendRequest);
			insertStmt.setString(1, friendRequest.getUserRequestor().getUserName());
			insertStmt.setString(2, friendRequest.getUserRequestee().getUserName());
			insertStmt.executeUpdate();

			return friendRequest;
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
	
	public FriendRequest updateAccepted(FriendRequest friendRequest, boolean newAccepted) throws SQLException {
		String updateFriendRequest = "UPDATE FriendRequest SET Accepted_huh=? WHERE FriendRequestId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateFriendRequest);
			updateStmt.setBoolean(1, newAccepted);
			updateStmt.setInt(2, friendRequest.getFriendRequestId());
			updateStmt.executeUpdate();

			// Update the blogComment param before returning to the caller.
			friendRequest.setAccepted_huh(newAccepted);
			return friendRequest;
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

	public FriendRequest delete(FriendRequest friendRequest) throws SQLException {
		String deleteFriendRequest = "DELETE FROM FriendRequest WHERE UserName=?,UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFriendRequest);
			deleteStmt.setString(1, friendRequest.getUserRequestor().getUserName());
			deleteStmt.setString(2, friendRequest.getUserRequestee().getUserName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
