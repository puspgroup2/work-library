import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DataBase {
	private Connection connection;
	private static String databaseServerAddress = "vm23.cs.lth.se";
	private static String database = "pusp2102hbg";
	private static String databaseUser = "pusp2102hbg";
	private static String databasePassword = "s9hg34sf";
	
	public DataBase(){
		connection = null;
	}
	
	public boolean connect() throws SQLException {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + databaseServerAddress + 
					"/" + database, databaseUser, databasePassword);
		} catch (SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return true is the connection was successfully closed.
	 * @throws SQLException if the connection could not be closed.
	 */
	public boolean disconnect() throws SQLException {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// Methods only the admin has access to.
	
	/**
	 * Only the admin can perform this action.
	 * @return true if the user was successfully added to the database.
	 */
	public boolean addUser(String username, String password, String email) {
		// checks if user exists
		String getUser = "SELECT * FROM Users WHERE userName = ?";
		try(PreparedStatement ps = connection.prepareStatement(getUser)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return false;
		}
		
		// Adds user
		String addUser = "INSERT INTO Users(userName, password, email) VALUES (?, ?, ?)";
		try(PreparedStatement ps = connection.prepareStatement(addUser)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.executeUpdate();
			return true;
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return false;
		}
	}
	
	/**
	 * Only the admin can perform this action.
	 * @return true if the user was successfully removed from the database.
	 */
	public boolean removeUser(String username) {
		// checks if user exists
		String getUser = "SELECT * FROM Users WHERE userName = ?";
		try(PreparedStatement ps = connection.prepareStatement(getUser)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return false;
			}
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return false;
		}
		
		// Removes user
		String removeUser = "DELETE FROM Users WHERE userName = ?";
		try(PreparedStatement ps = connection.prepareStatement(removeUser)) {
			ps.setString(1, username);
			ps.executeUpdate();
			return true;
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return false;
		}
		
	}
	
	// Methods only the project leader has access to
	
	public String getRole() {
		return "Jag heter Anna";
	}
	
	public boolean updateRole() {
		return false;
	}
	
	public boolean signTimeReport() {
		return false;
	}
	
	// Methods every user has access to
	
	/**
	 * Creates a new Time Report.
	 * @return the Time Report ID.
	 */
	public String newTimeReport() {
		String reportID = null;
		return reportID;
	}
	
	/**
	 * Updates a Time Report according to the values contained in the map.
	 * @param timeReport A map with key-value pairs consisting of the tuple's 
	 * columns and the values associated with these.
	 * @return true if the Time Report was successfully updated.
	 */
	public boolean updateTimeReport(Map<String, String> timeReport) {
		return false;
	}
	
	/**
	 * Deletes the specified Time Report.
	 * @param reportID the Time Report to be deleted.
	 * @return true if deletion was successful.
	 */
	public boolean deleteTimeReport(String reportID) {
		return false;
	}
	
	
	// admin??
	
	public String getPassword(String userName) throws SQLException {
		String pw = null;
		String sql = "select password from Users where userName = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pw = rs.getString("password");
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return pw;
	}
	
	public String getEmail(String userID) {
		String email = null;
		return email;
	}
	
	public boolean checkLogin(String userName, String password) {
		
	}
	
	
}
