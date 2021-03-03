package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.UserBean;

/**
 * A database used by the time reporting system TimeMate.
 * @author Anna, Alexandra, Annelie
 *
 */
public class DataBase {
	private Connection connection;
	private static String databaseServerAddress = "vm23.cs.lth.se";
	private static String database = "pusp2102hbg";
	private static String databaseUser = "pusp2102hbg";
	private static String databasePassword = "s9hg34sf";
	
	public DataBase(){
		connection = null;
	}
	
	/**
	 * Establishes a connection to the MySQL database.
	 * @return true if the connection was established
	 */
	public boolean connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + databaseServerAddress + 
					"/" + database, databaseUser, databasePassword);
		} catch (SQLException e) {
			handleSQLException(e);
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
			handleSQLException(e);
			return false;
		}
		return true;
	}
	
	// Methods only the admin has access to.
	
	/**
	 * Only the admin can perform this action.
	 * @param username The userName of the user.
	 * @param password The password for the login.
	 * @param email The email of the user.
	 * @param salt The salt to be added.
	 * @return true if the user was successfully added to the database.
	 */
	public boolean addUser(String username, String password, String email, String salt) {
		// checks if user exists
		String getUser = "SELECT * FROM Users WHERE userName = ?";
		try(PreparedStatement ps = connection.prepareStatement(getUser)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch(SQLException e) {
			handleSQLException(e);
            return false;
		}
		
		// Adds user
		String addUser = "INSERT INTO Users(userName, password, email, salt) VALUES (?, ?, ?, ?)";
		try(PreparedStatement ps = connection.prepareStatement(addUser)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, salt);
			ps.executeUpdate();
			return true;
		} catch(SQLException e) {
			handleSQLException(e);
            return false;
		}
	}
	
	/**
	 * Only the admin can perform this action.
	 * @param username The userName of the user.
	 * @return true if the user was successfully removed from the database.
	 */
	public boolean removeUser(String username) {
		String removeUser = "DELETE FROM Users WHERE userName = ?";
		try(PreparedStatement ps = connection.prepareStatement(removeUser)) {
			ps.setString(1, username);
			return ps.executeUpdate() > 0;
		} catch(SQLException e) {
			handleSQLException(e);
            return false;
		}
	}
	
	// Methods only the project leader has access to
	
	/**
	 * Returns a list containing a user's Time Report ID's.
	 * @param username The userName of the user.
	 * @return list of Time Report IDs.
	 */
	public List<Integer> getTimeReportIDs(String username) {
		String getIDs = "SELECT reportID FROM TimeReports WHERE userName = ?";
		ArrayList<Integer> timeReportIDs = new ArrayList<Integer>();
		try(PreparedStatement ps = connection.prepareStatement(getIDs)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				timeReportIDs.add(rs.getInt(1));
			}
			return timeReportIDs;
		} catch(SQLException e) {
			handleSQLException(e);
            return timeReportIDs;
		}
	}
	
	/**
	 * Returns a list containing ID's of all unsigned Time Reports.
	 * @return list of Time Report IDs.
	 */
	public List<Integer> getUnsignedTimeReportIDs() {
		String getIDs = "SELECT reportID FROM TimeReports WHERE signature IS NULL";
		ArrayList<Integer> timeReportIDs = new ArrayList<Integer>();
		try(PreparedStatement ps = connection.prepareStatement(getIDs)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				timeReportIDs.add(rs.getInt(1));
			}
			return timeReportIDs;
		} catch(SQLException e) {
			handleSQLException(e);
            return timeReportIDs;
		}
	}
	/**
	 * Returns a list containing ID's of all signed Time Reports.
	 * @return list of Time Report IDs.
	 */
	public List<Integer> getSignedTimeReportIDs() {
		String getIDs = "SELECT reportID FROM TimeReports WHERE signature IS NOT NULL";
		ArrayList<Integer> timeReportIDs = new ArrayList<Integer>();
		try(PreparedStatement ps = connection.prepareStatement(getIDs)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				timeReportIDs.add(rs.getInt(1));
			}
			return timeReportIDs;
		} catch(SQLException e) {
			handleSQLException(e);
            return timeReportIDs;
		}
	}
	
	/**
	 * Sets the time report as signed by the project leader
	 * @param yes if the Time Report be signed
	 * @param userName the name of the project leader
	 * @param reportID the number of the Time Report in question.
	 */
	public void setSigned(boolean yes, String userName, int reportID) {
		String sql = "UPDATE TimeReports "
				+ "set signature = ? "
				+ "where reportID = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			String result = yes ? userName : null;
			ps.setString(1, result);
			ps.setInt(2, reportID);	
			ps.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
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
		  handleSQLException(e);
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
		String sql = "UPDATE Users SET role = ? WHERE userName = ?";
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, role);
			ps.setString(2, userName);
			return ps.executeUpdate() > 0;
		}
        catch (SQLException e) {
        handleSQLException(e);
        return false;
        }
	}

	
	// Methods every user has access to
	/**
	 * Creates a new Time Report.
	 * @return the Time Report ID.
	 */
	public int newTimeReport(String userName, int week) {
        String sql = "INSERT into TimeReports(userName, week) values(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setInt(2, week);
            ps.executeUpdate();
        } catch (SQLException e) {
        	handleSQLException(e);
        }
        return getReportID(userName, week);
    }
	
	/**
	 * Updates a Time Report according to the values contained in the map.
	 * @param timeReport A map with key-value pairs consisting of the tuple's 
	 * columns and the values associated with these.
	 * @return true if the Time Report was successfully updated.
	 */
	public boolean updateTimeReport(int reportID, Map<String, Integer> timeReport) {
		String sql = "UPDATE TimeReports"
				+ " SET ? = ?"
				+ " WHERE reportID = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			for(Map.Entry<String, Integer> entry : timeReport.entrySet()) {
				ps.setString(1, entry.getKey());
				ps.setInt(2, entry.getValue());
				ps.setInt(3, reportID);
				ps.executeUpdate(); // h�r f�r jag fel hela tiden!!
			}
		} catch (SQLException e) {
			handleSQLException(e);
			return false;
		}
		return true;
	}
	
	
	public boolean updateActivityReport(int reportID, Map<String, Integer> activityReport) {
		if (this.select(reportID, "*", "TimeReports") == null) return false;
		
		String sql;
		if(this.select(reportID, "*", "ActivityReports") == null) {
			String addActivityReport = "INSERT INTO ActivityReports(reportID) VALUES (?)";
			try(PreparedStatement ps = connection.prepareStatement(addActivityReport)) {
				ps.setInt(1, reportID);
				ps.executeUpdate();
			} catch(SQLException e) {
				handleSQLException(e);
	            return false;
			}
		}
		
		for (String s : activityReport.keySet()) {
			sql = "UPDATE ActivityReports SET " + s + " = ? WHERE reportID = ?";
			try(PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setInt(1, activityReport.get(s));
				ps.setInt(2, reportID);
				ps.execute();
			} catch (SQLException e) {
				handleSQLException(e);
			}
		}
		return true;
	}
	
	
	
	public boolean updateDocumentTimeD(int reportID, Map<String, Integer> documentTimeD) {
		return updateDocumentTime(reportID, documentTimeD, 'D');
	}
	
	
	public boolean updateDocumentTimeI(int reportID, Map<String, Integer> documentTimeI) {
		return updateDocumentTime(reportID, documentTimeI, 'I');
	}
	
	
	public boolean updateDocumentTimeR(int reportID, Map<String, Integer> documentTimeR) {
		return updateDocumentTime(reportID, documentTimeR, 'R');
	}
	
	
	public boolean updateDocumentTimeF(int reportID, Map<String, Integer> documentTimeF) {
		return updateDocumentTime(reportID, documentTimeF, 'F');
	}
	
	
	private boolean updateDocumentTime(int reportID, Map<String, Integer> documentTime, char type) {
		if (this.select(reportID, "*", "TimeReports") == null) return false;
		
		String sql;
		if(this.select(reportID, "*", "DocumentTime" + type) == null) {
			String addDocumentTime = "INSERT INTO DocumentTime" + type + "(reportID) VALUES (?)";
			try(PreparedStatement ps = connection.prepareStatement(addDocumentTime)) {
				ps.setInt(1, reportID);
				ps.executeUpdate();
			} catch(SQLException e) {
				handleSQLException(e);
	            return false;
			}
		}
		
		for (String s : documentTime.keySet()) {
			sql = "UPDATE DocumentTime" + type + " SET " + s + " = ? WHERE reportID = ?";
			try(PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setInt(1, documentTime.get(s));
				ps.setInt(2, reportID);
				ps.execute();
			} catch (SQLException e) {
				handleSQLException(e);
			}
		}
		return true;
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
			return ps.executeUpdate() > 0;
		}
        catch (SQLException e) {
        handleSQLException(e);
        return false;
        }
	}
	
	/**
	 * Retrieves the list of all the users.
	 * @return a list of all the users.
	 */
	public List<String> getUsers() {
		String sql = "SELECT * FROM Users";
		List<String> users = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				users.add(rs.getString("userName"));
				
			}
			
		} catch (SQLException e) {
			handleSQLException(e);
		}
		 return users;
	}
	
	
	
	// admin??
	
	/**
	 * Returns the username connected to a Time Report.
	 * @param reportID the reportID of a specific Time Report.
	 * @return the username connected to the reportID.
	 */
	public String getUserNameFromTimeReport(int reportID) {
		String username = null;
		String sql = "SELECT userName from TimeReports where reportID = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reportID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				username = rs.getString("userName");
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return username;
	}
	
	
	/**
	 * Returns the totalMinutes stored in a Time Report.
	 * @param reportID the reportID of a specific Time Report.
	 * @return the totalMinutes connected to the reportID.
	 */
	public int getTotalMinutesFromTimeReport(int reportID) {
		int totalminutes = 0;
		String sql = "SELECT totalMinutes from TimeReports where reportID = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reportID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				totalminutes = rs.getInt("totalMinutes");
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return totalminutes;
	}
	
	
	/**
	 * Returns the username of a  Time Report.
	 * @param reportID the reportID of a specific Time Report.
	 * @return the totalMinutes connected to the reportID.
	 */
	public String getSignatureFromTimeReport(int reportID) {
		String signature = null;
		String sql = "SELECT signature from TimeReports where reportID = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reportID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				signature = rs.getString("signature");
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return signature;
	}
	
	
	/**
	 * Returns the week of a  Time Report.
	 * @param reportID the reportID of a specific Time Report.
	 * @return the week connected to the reportID.
	 */
	public int getWeekFromTimeReport(int reportID) {
		int week = -1;
		String sql = "SELECT week from TimeReports where reportID = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reportID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				week = rs.getInt("week");
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return week;
	}
	
	
	/**
	 * Returns a map containing all time values in the DocumentTimeD table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeD(int reportID) {
		return getDocumentTime(reportID, 'D');
	}
	
	
	/**
	 * Returns a map containing all time values in the DocumentTimeI table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeI(int reportID) {
		return getDocumentTime(reportID, 'I');
	}
	
	
	/**
	 * Returns a map containing all time values in the DocumentTimeR table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeR(int reportID) {
		return getDocumentTime(reportID, 'R');
	}
	
	
	/**
	 * Returns a map containing all time values in the DocumentTimeF table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getDocumentTimeF(int reportID) {
		return getDocumentTime(reportID, 'F');
	}
	
	/**
	 * Returns a map containing all time values in the ActivityReport table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	private Map<String, Integer> getDocumentTime(int reportID, char doctype) {
		String getDocumentTime = "SELECT * FROM DocumentTime" + doctype + " WHERE reportID = ?";
		try(PreparedStatement ps = connection.prepareStatement(getDocumentTime)) {
			ps.setInt(1, reportID);
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
			handleSQLException(e);
            return null;
		}
	}
	
	
	/**
	 * 
	 * @param userName The userName associated with the user.
	 * @param week The week that the time report is referring to.
	 * @return the report id.
	 */
	public int getReportID(String userName, int week) {
		int reportID = 0;
		String sql = "SELECT reportID from TimeReports"
				+ " where userName = ?"
				+ " and week = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, userName);
			ps.setInt(2, week);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reportID = rs.getInt("reportID");
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return reportID;
	}
	
	// helper method
	private int getWeek(int reportID) {
		int week = 0;
		String sql = "SELECT week "
				+ "from TimeReports "
				+ "where reportID = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reportID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				week = rs.getInt("week");
				System.out.println(week);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return week;
	}
	
	
	/**
	 * Returns a map containing all time values in the ActivityReport table.
	 * @param reportID the Time Report to be returned.
	 * @return a map of time values if Time Report exists, else null.
	 */
	public Map<String, Integer> getActivityReport(int reportID) {
		String getActivityReport = "SELECT * FROM ActivityReports WHERE reportID = ?";
		try(PreparedStatement ps = connection.prepareStatement(getActivityReport)) {
			ps.setInt(1, reportID);
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
			handleSQLException(e);
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
			handleSQLException(e);
		}
		return pw;
	}
	
	/**
	 * @param userID The user whose salt are requested. 
	 * @return the salt as a String
	 */
	public String getSalt(String userID) {
		String sql = "SELECT salt "
				+ "from Users "
				+ "where userName = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString("salt");
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return null;
	}
	/**
	 * Changes the user's password.
	 * @param userName The user's userName.
	 * @param password The user's password.
	 * @return true if the change was successful, otherwise false.
	 */
	public boolean changePassword(String userName, String password) {
		String sql = "UPDATE Users SET password = ? WHERE userName = ?";
		
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, password);
			ps.setString(2, userName);
			return ps.executeUpdate() > 0;
		}
        catch (SQLException e) {
        handleSQLException(e);
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
			handleSQLException(e);
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
	public boolean checkLogin(UserBean bajsbean) {
        String sql = "SELECT * FROM Users where userName = ? AND password = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
        	String userName = bajsbean.getUserName();
        	String password = bajsbean.getPassword();
        	
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
            	String name = rs.getString("userName");
            	String pw = rs.getString("password");
            	return name.equals(userName) && pw.equals(password);
            }
        } catch (SQLException e) {
        	handleSQLException(e);
        }
        return false;
    }
	
	private void handleSQLException(SQLException e) {
		System.err.println(e);
		e.printStackTrace();
	}
	
	/*Helper method */
	public ResultSet select(int reportID, String attribute, String relation) {
		String sql = "SELECT " + attribute + " from " + relation + " WHERE reportID = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reportID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return rs;
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return null;
	}
	
	public boolean valueExists(int reportID, String target, ResultSet rs) throws SQLException {
		if (rs == null) return false;
		while (rs.next()) {
			if (rs.getInt("reportID") == reportID) {
				return rs.getInt(target) != 0;
			}
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		DataBase db = new DataBase();
		db.connect();


		System.out.println(db.updateDocumentTimeD(1, db.getDocumentTimeD(5)));
		
		/**
		db.newTimeReport("Assar", 9);
		HashMap<String, Integer> map = new HashMap<>();
		map.put("totalMinutes", 30);
		db.updateTimeReport(9, map);
		*/
	}
	
}
