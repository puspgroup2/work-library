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
		String getIDs = "SELECT reportID FROM TimeReports WHERE signature = NULL";
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
	
	public Map<String, Integer> getDocumentTimeD(String reportID) {
		return getDocumentTime(reportID, 'D');
	}
	
	public Map<String, Integer> getDocumentTimeI(String reportID) {
		return getDocumentTime(reportID, 'I');
	}
	
	public Map<String, Integer> getDocumentTimeR(String reportID) {
		return getDocumentTime(reportID, 'R');
	}
	
	public Map<String, Integer> getDocumentTimeF(String reportID) {
		return getDocumentTime(reportID, 'F');
	}
	
	private Map<String, Integer> getDocumentTime(String reportID, char timedoc) {
		String getActivityReport = "SELECT * FROM DocumentTime" + timedoc + " WHERE reportID = ?";
		try(PreparedStatement ps = connection.prepareStatement(getActivityReport)) {
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
	
	public String getEmail(String userName) throws SQLException {
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
	
	// anv�nd username "Ulla" and pw "ulla123!"
	public boolean checkLogin(String userName, String password) throws SQLException {
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
