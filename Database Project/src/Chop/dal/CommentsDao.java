package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

public class CommentsDao {
	protected ConnectionManager connectionManager;

	private static CommentsDao instance = null;
	protected CommentsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CommentsDao getInstance() {
		if(instance == null) {
			instance = new CommentsDao();
		}
		return instance;
	}	
	
	
	public Comments create(Comments comment) throws SQLException {
		String insertComment =
			"INSERT INTO Comments(Content,Created,UserName,ItemId) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertComment,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, comment.getContent());
			insertStmt.setTimestamp(2, new Timestamp(comment.getCreated().getTime()));
			insertStmt.setString(3, comment.getUser().getUserName());
			insertStmt.setInt(4, comment.getItem().getItemId());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int commentId = -1;
			if(resultKey.next()) {
				commentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			comment.setCommentId(commentId);
			return comment;
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
	 * Update the content of the Comments instance.
	 * This runs a UPDATE statement.
	 */
	public Comments updateContent(Comments comment, String newContent) throws SQLException {
		String updateComment = "UPDATE Comments SET Content=?,Created=? WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateComment);
			updateStmt.setString(1, newContent);
			Date newCreatedTimestamp = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setInt(3, comment.getCommentId());
			updateStmt.executeUpdate();

			// Update the blogComment param before returning to the caller.
			comment.setContent(newContent);
			comment.setCreated(newCreatedTimestamp);
			return comment;
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
	 * Delete the Comments instance.
	 * This runs a DELETE statement.
	 */
	public Comments delete(Comments comment) throws SQLException {
		String deleteComment = "DELETE FROM Comments WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteComment);
			deleteStmt.setInt(1, comment.getCommentId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
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
	 * Get the Comment by CommentID.
	 */
	public Comments getCommentByCommentId(int commentId) throws SQLException {
		String selectBlogComment =
			"SELECT CommentId,Content,Created,UserName,ItemId " +
			"FROM Comments " +
			"WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBlogComment);
			selectStmt.setInt(1, commentId);
			results = selectStmt.executeQuery();
			ItemsDao itemsDao = ItemsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultCommentId = results.getInt("CommentId");
				String content = results.getString("Content");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				int itemId = results.getInt("ItemId");
				String userName = results.getString("UserName");
				
				Items item = itemsDao.getItemByItemId(itemId);
				Users user = usersDao.getUserFromUserName(userName);
				Comments comment = new Comments(resultCommentId, content,
					created, user, item);
				return comment;
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
	 * Get all the Comments for a user.
	 */
	public List<Comments> getCommentsForUser(String userName) throws SQLException {
		List<Comments> comments = new ArrayList<Comments>();
		String selectBlogComments =
			"SELECT CommentId,Content,Created,UserName,ItemId " +
			"FROM Comments " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBlogComments);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			ItemsDao itemsDao = ItemsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int commentId = results.getInt("CommentId");
				String content = results.getString("Content");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				int itemId = results.getInt("ItemId");

				Items item = itemsDao.getItemByItemId(itemId);
				Users user = usersDao.getUserFromUserName(userName);
				Comments comment = new Comments(commentId, content, created,
						user, item);
				comments.add(comment);
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
		return comments;
	}
	

	/**
	 * Get all the Comments for an item.
	 */
	public List<Comments> getCommentsForItem(int itemId) throws SQLException {
		List<Comments> comments = new ArrayList<Comments>();
		String selectBlogComments =
			"SELECT CommentId,Content,Created,UserName,ItemId " +
			"FROM Comments " +
			"WHERE ItemId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBlogComments);
			selectStmt.setInt(1, itemId);
			results = selectStmt.executeQuery();
			ItemsDao itemsDao = ItemsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int commentId = results.getInt("CommentId");
				String content = results.getString("Content");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String userName = results.getString("UserName");

				Items item = itemsDao.getItemByItemId(itemId);
				Users user = usersDao.getUserFromUserName(userName);
				Comments comment = new Comments(commentId, content, created,
						user, item);
				comments.add(comment);
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
		return comments;
	}
}
