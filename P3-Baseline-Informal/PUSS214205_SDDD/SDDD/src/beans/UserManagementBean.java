package beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*This is the class is the bean for the web page "usermanagement.jsp".*/
public class UserManagementBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, String> userMap = new HashMap<String, String>(); // Map containing the elements <Username, Role>

	/*
	 * Fetches a map of all users in the system.
	 * 
	 * @return Map usersList.
	 */
	public Map<String, String> getUserMap() {
		return userMap;
	}

	/*
	 * Adds users to the Map of all users. Requires "Project leader" role.
	 * 
	 * @param map
	 */
	public void populateBean(Map<String, String> map) {
		userMap.putAll(map); // Fills the map with user input.
	}
}