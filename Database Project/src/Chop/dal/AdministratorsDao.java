package Chop.dal;
import Chop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;


public class AdministratorsDao extends PersonsDao {
	// Single pattern: instantiation is limited to one object.
	private static AdministratorsDao instance = null;
	protected AdministratorsDao() {
		super();
	}
	public static AdministratorsDao getInstance() {
		if(instance == null) {
			instance = new AdministratorsDao();
		}
		return instance;
	}
	
	
	public Administrators create(Administrators administrator) throws SQLException {
		// Insert into the superclass table first.
		create(new Persons(administrator.getUserName(),administrator.getPassword(),administrator.getFirstName(),
			administrator.getLastName(),administrator.getEmail()));

		String insertAdministrator = "INSERT INTO Admins(UserName,LastLogin) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAdministrator);
			insertStmt.setString(1, administrator.getUserName());
			insertStmt.setTimestamp(2, new Timestamp(administrator.getLastLogin().getTime()));
			insertStmt.executeUpdate();
			return administrator;
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

	
	public Administrators updatePerson(Administrators administrator, String newPassword, String newFirstName, String newLastName, String newEmail) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updatePerson(administrator, newPassword, newFirstName, newLastName, newEmail);
		return administrator;
	}
	
	public Administrators getAdminByUserName(String userName) throws SQLException {
		String selectAdmin = 
				"SELECT Admins.UserName AS UserName,Password,FirstName,LastName,Email,LastLogin " +
				"FROM Admins INNER JOIN Persons " +
				"  ON Admins.UserName = Persons.UserName " +
				"WHERE Admins.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAdmin);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				Date lastLogin = new Date(results.getTimestamp("LastLogin").getTime());
				Administrators administrator = new Administrators(resultUserName, password, firstName, lastName, email, lastLogin);
				return administrator;
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
	

	public Administrators delete(Administrators administrator) throws SQLException {
		String deleteAdministrator = "DELETE FROM Admins WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAdministrator);
			deleteStmt.setString(1, administrator.getUserName());
			deleteStmt.executeUpdate();

			// Then also delete from the superclass.
			// Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from Administrators first.
			super.delete(administrator);

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
