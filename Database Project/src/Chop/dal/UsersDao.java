package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * Data access object (DAO) class to interact with the underlying BlogUsers table in your
 * MySQL instance. This is used to store {@link BlogUsers} into your MySQL instance and 
 * retrieve {@link BlogUsers} from MySQL instance.
 */
public class UsersDao extends PersonsDao {
	// Single pattern: instantiation is limited to one object.
	private static UsersDao instance = null;
	protected UsersDao() {
		super();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}

	public Users create(Users users) throws SQLException {
		// Insert into the superclass table first.
		create(new Persons(users.getUserName(), users.getPassword(),users.getFirstName(),
				users.getLastName(), users.getEmail()));

		String insertUser = "INSERT INTO Users(UserName,ProfilePicture,About,Age,LastLogin,Gender) VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, users.getUserName());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setNull(2, Types.BLOB);
			//insertStmt.setBlob(2, users.getProfilePicture());
			insertStmt.setString(3, users.getAbout());
			insertStmt.setInt(4, users.getAge());
			insertStmt.setTimestamp(5, new Timestamp(users.getLastLogin().getTime()));
			insertStmt.setString(6, users.getGender());
			insertStmt.executeUpdate();
			return users;
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


	public Users updateUsers(Users user, String newProfilePicture, String newAbout, int newAge, String newGender) throws SQLException {
		String updateUsers = "UPDATE Users SET ProfilePicture=?, About=?, Age=?, Gender=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUsers);
			updateStmt.setString(1, newProfilePicture);
			updateStmt.setString(2, newAbout);
			updateStmt.setInt(3, newAge);
			updateStmt.setString(4, newGender);
			updateStmt.setString(5, user.getUserName());
			updateStmt.executeUpdate();

			user.setProfilePicture(newProfilePicture);
			user.setAbout(newAbout);
			user.setAge(newAge);
			user.setGender(newGender);
			
			return user;
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
	
	public Users updateAboutUser(Users user, String newAbout) throws SQLException {
		String updateAboutUser = "UPDATE Users SET About=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAboutUser);
			updateStmt.setString(1, newAbout);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();

			user.setAbout(newAbout);
			return user;
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
	
	

	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
			deleteStmt.executeUpdate();

			// Then also delete from the superclass.
			// Notes:
			// 1. Due to the fk constraint (ON DELETE CASCADE), we could simply call
			//    super.delete() without even needing to delete from Administrators first.
			// 2. BlogPosts has a fk constraint on BlogUsers with the reference option
			//    ON DELETE SET NULL. If the BlogPosts fk reference option was instead
			//    ON DELETE RESTRICT, then the caller would need to delete the referencing
			//    BlogPosts before this BlogUser can be deleted.
			//    Example to delete the referencing BlogPosts:
			//    List<BlogPosts> posts = BlogPostsDao.getBlogPostsForUser(blogUser.getUserName());
			//    for(BlogPosts p : posts) BlogPostsDao.delete(p);
			super.delete(user);

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

	public Users getUserFromUserName(String userName) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectUser =
			"SELECT Users.UserName AS UserName,Password,FirstName,LastName,Email,ProfilePicture,About,Age,LastLogin,Gender " +
			"FROM Users INNER JOIN Persons " +
			"  ON Users.UserName = Persons.UserName " +
			"WHERE Users.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				String profilePicture = results.getString("ProfilePicture");
				String about = results.getString("About");
				int age = results.getInt("Age");
				Timestamp lastLogin = results.getTimestamp("LastLogin");
				String gender = results.getString("Gender");
				Users user = new Users(resultUserName, password, firstName, lastName, email, profilePicture,about,age,lastLogin,gender);
				return user;
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
