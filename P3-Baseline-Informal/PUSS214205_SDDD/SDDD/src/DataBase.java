import java.sql.DriverManager;

public class DataBase {
	private DriverManager connection;
	
	public DataBase() {
		
	}
	
	public boolean connect() {
		return false;
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
	
	// Methods every user has access to
}
