import java.sql.Connection;
import java.sql.DriverManager;
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
	
	public boolean disconnect() {
		return false;
	}
	
	// Methods only the admin has access to.
	
	/**
	 * Only the admin can perform this action.
	 * @return true if the user was successfully added to the database.
	 */
	public boolean addUser() {
		return false;
	}
	
	/**
	 * Only the admin can perform this action.
	 * @return true if the user was successfully removed from the database.
	 */
	public boolean removeUser() {
		return false;
	}
	
	/**
	 * Associates the role of "project leader" to one user. Only the admin has
	 * authority to perform this action.
	 * @return true if a project leader was successfully assigned.
	 */
	public boolean setProjectLeader() {
		return false;
	}
	
	// Methods only the project leader has access to
	
	public String getRole() {
		return " ";
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
}
