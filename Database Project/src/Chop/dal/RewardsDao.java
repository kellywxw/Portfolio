package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RewardsDao {
	protected ConnectionManager connectionManager;

	private static RewardsDao instance = null;
	protected RewardsDao() {
		connectionManager = new ConnectionManager();
	}
	public static RewardsDao getInstance() {
		if(instance == null) {
			instance = new RewardsDao();
		}
		return instance;
	}
		
	public Rewards create(Rewards rewards) throws SQLException {
		String insertRewards =
			"INSERT INTO Rewards (AllowRewards,ShareRewards,Points,UserName) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRewards);
			insertStmt.setBoolean(1, rewards.isAllowRewards());
			insertStmt.setBoolean(2, rewards.isShareRewards());
			insertStmt.setInt(3, rewards.getPoints());
			insertStmt.setString(4, rewards.getUser().getUserName());
			insertStmt.executeUpdate();
			return rewards;
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

	
	public Rewards getRewardsByUserName(String userName) throws SQLException {
		String selectRewards =
			"SELECT AllowRewards,ShareRewards,Points,UserName " +
			"FROM Rewards " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRewards);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				
				boolean allowRewards = results.getBoolean("AllowRewards");
				boolean shareRewards = results.getBoolean("ShareRewards");
				int points = results.getInt("Points");
				String resultUserName = results.getString("UserName");
				Users user = usersDao.getUserFromUserName(resultUserName);
				Rewards rewards = new Rewards(allowRewards, shareRewards, points, user);
				return rewards;
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
	
	public Rewards updateRewards(Rewards rewards, boolean newAllowRewards, boolean newShareRewards, int newPoints) throws SQLException {
		String updateRewards  = "UPDATE Rewards SET AllowRewards=?, ShareRewards=?, Points=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateRewards);
			updateStmt.setBoolean(1, newAllowRewards);
			updateStmt.setBoolean(2, newShareRewards);
			updateStmt.setInt(3, newPoints);
			updateStmt.setString(4,rewards.getUser().getUserName());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			rewards.setAllowRewards(newAllowRewards);
			rewards.setShareRewards(newShareRewards);
			rewards.setPoints(newPoints);
			return rewards;
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

	public Rewards delete(Rewards rewards) throws SQLException {
		String deleteRewards = "DELETE FROM Rewards WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRewards);
			deleteStmt.setString(1, rewards.getUser().getUserName());
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
