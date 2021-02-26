import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	/**
	 * Returns a list containing a user's Time Report ID's.
	 * @param username
	 * @return list of Time Report IDs.
	 */
	public List<String> getTimeReportIDs(String username) {
		String getIDs = "SELECT reportID FROM TimeReports WHERE userName = ?";
		ArrayList<String> timeReportIDs = new ArrayList<String>();
		try(PreparedStatement ps = connection.prepareStatement(getIDs)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				timeReportIDs.add(String.valueOf(rs.getInt(1)));
			}
			return timeReportIDs;
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return timeReportIDs;
		}
	}
	
	/**
	 * Returns a list containing ID's of all unsigned Time Reports.
	 * @return list of Time Report IDs.
	 */
	public List<String> getUnsignedTimeReportIDs() {
		String getIDs = "SELECT reportID FROM TimeReports WHERE signature IS NULL";
		ArrayList<String> timeReportIDs = new ArrayList<String>();
		try(PreparedStatement ps = connection.prepareStatement(getIDs)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				timeReportIDs.add(String.valueOf(rs.getInt(1)));
			}
			return timeReportIDs;
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return timeReportIDs;
		}
	}
	/**
	 * Returns a list containing ID's of all signed Time Reports.
	 * @return list of Time Report IDs.
	 */
	public List<String> getSignedTimeReportIDs() {
		String getIDs = "SELECT reportID FROM TimeReports WHERE signature IS NOT NULL";
		ArrayList<String> timeReportIDs = new ArrayList<String>();
		try(PreparedStatement ps = connection.prepareStatement(getIDs)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				timeReportIDs.add(String.valueOf(rs.getInt(1)));
			}
			return timeReportIDs;
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return timeReportIDs;
		}
	}

	/**
	 * Retrieves a user's role with the help of their userName.
	 * @param userName The userName of the user.
	 * @return the role of the user, null will otherwise be returned.
	 */
	public String getRole(String userName) {
		String role = null;
		String sql = "SELECT role from Users where userName = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				role = rs.getString("role");
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return role;
	}

	/**
	 * Updates the user's role.
	 * @param userName The userName of the user.
	 * @param role The role of the user.
	 * @return true and updates the user's role, returns false if it wasn't possible.
	 */
	public boolean updateRole(String userName, String role) {
		String sql = "UPDATE Users SET role = ? + WHERE userName = ?";
		
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, role);
			ps.setString(2, userName);
			
			ps.executeUpdate();
			return true;
			
		}
        catch (SQLException e) {
    	System.out.println(e);
        e.printStackTrace();
        return false;
    }
		
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
	public boolean deleteTimeReport(int reportID) {
		String sql = "DELETE FROM TimeReports WHERE reportID = ?";
		
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reportID);
			
			ps.executeUpdate();
			return true;
			
		}
        catch (SQLException e) {
    	System.out.println(e);
        e.printStackTrace();
        return false;
    }
	}
	
	/**
	 * Retrieves the list of all the members.
	 * @return a list of all the members.
	 */
	public List<String> getMembers() {
		String sql = "SELECT * FROM Users";
		List<String> members = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				members.add(rs.getString("userName"));
				
			}
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		 return members;
	}
	
	
	
	// admin??
	
	/**
	 * Returns a map containing all time values in the DocumentTimeD table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeD(String reportID) {
		return getDocumentTime(reportID, 'D');
	}
	
	
	/**
	 * Returns a map containing all time values in the DocumentTimeI table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeI(String reportID) {
		return getDocumentTime(reportID, 'I');
	}
	
	
	/**
	 * Returns a map containing all time values in the DocumentTimeR table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeR(String reportID) {
		return getDocumentTime(reportID, 'R');
	}
	
	
	/**
	 * Returns a map containing all time values in the DocumentTimeF table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeF(String reportID) {
		return getDocumentTime(reportID, 'F');
	}
	
	/**
	 * Returns a map containing all time values in the ActivityReport table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	private Map<String, Integer> getDocumentTime(String reportID, char doctype) {
		String getDocumentTime = "SELECT * FROM DocumentTime" + doctype + " WHERE reportID = ?";
		try(PreparedStatement ps = connection.prepareStatement(getDocumentTime)) {
			ps.setInt(1, Integer.valueOf(reportID));
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				//report does not exist does not exist
				return null;
			} else {
				HashMap<String, Integer> documentTime = new HashMap<String, Integer>();
				documentTime.put("totalMinutes", rs.getInt("totalMinutes"));
				documentTime.put("SDP", rs.getInt("SDP"));
				documentTime.put("SRS", rs.getInt("SRS"));
				documentTime.put("SVVS", rs.getInt("SVVS"));
				documentTime.put("STLDD", rs.getInt("STLDD"));
				documentTime.put("SVVI", rs.getInt("SVVI"));
				documentTime.put("SDDD", rs.getInt("SDDD"));
				documentTime.put("SVVR", rs.getInt("SVVR"));
				documentTime.put("SSD", rs.getInt("SSD"));
				documentTime.put("finalReport", rs.getInt("finalReport"));	
				return documentTime;
			}
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return null;
		}
	}
	
	
	/**
	 * Returns a map containing all time values in the ActivityReport table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getActivityReport(String reportID) {
		String getActivityReport = "SELECT * FROM DocumentTime WHERE reportID = ?";
		try(PreparedStatement ps = connection.prepareStatement(getActivityReport)) {
			ps.setInt(1, Integer.valueOf(reportID));
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				//report does not exist does not exist
				return null;
			} else {
				HashMap<String, Integer> activityReport = new HashMap<String, Integer>();
				activityReport.put("totalMinutes", rs.getInt("totalMinutes"));
				activityReport.put("functionalTest", rs.getInt("functionalTest"));
				activityReport.put("systemTest", rs.getInt("systemTest"));
				activityReport.put("regressionTest", rs.getInt("regressionTest"));
				activityReport.put("meeting", rs.getInt("meeting"));
				activityReport.put("lecture", rs.getInt("lecture"));
				activityReport.put("exercise", rs.getInt("exercise"));
				activityReport.put("computerExercise", rs.getInt("computerExercise"));
				activityReport.put("homeReading", rs.getInt("homeReading"));
				activityReport.put("other", rs.getInt("other"));	
				return activityReport;
			}
		} catch(SQLException e) {
			System.err.println(e);
            e.printStackTrace();
            return null;
		}
	}
	/**
	 * Retrieves a user's password with the help of their userName.
	 * @param userName The userName of the user.
	 * @return password of the user, null will be returned if it's wrong.
	 */
	public String getPassword(String userName) {
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
	/**
	 * Changes the user's password.
	 * @param userName The user's userName.
	 * @param password The user's password.
	 * @return true if the change was successful, otherwise false.
	 */
	public boolean changePassword(String userName, String password) {
		String sql = "UPDATE Users SET password = ? + WHERE userName = ?";
		
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, password);
			ps.setString(2, userName);
			
			ps.executeUpdate();
			return true;
			
		}
        catch (SQLException e) {
    	System.out.println(e);
        e.printStackTrace();
        return false;
    }
		
	}
	
	/**
	 * Retrieves a user's e-mail with the help of their userName.
	 * @param userName The userName of the user.
	 * @return e-mail if it exists, otherwise null will be returned.
	 */
	public String getEmail(String userName) {
		String email = null;
		String sql = "SELECT email from Users where userName = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				email = rs.getString("email");
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return email;
	}
	/**
	 * Checks if the password for a specific user is correct.
	 * @param userName The userName of the user.
	 * @param password The password of the user.
	 * @return true if they were correct, otherwise false will be returned.
	 */
	// anv�nd username "Ulla" and pw "ulla123!"
	public boolean checkLogin(UserBean ub) {
		String userName = ub.getUserName();
		String password = ub.getPassword();
        String sql = "SELECT * FROM Users where userName = ? AND password = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
            	String name = rs.getString("userName");
            	String pw = rs.getString("password");
            	return name.equals(userName) && pw.equals(password);
            }
        } catch (SQLException e) {
        	System.out.println(e);
            e.printStackTrace();

        }

        return false;
    }
	
	
}
