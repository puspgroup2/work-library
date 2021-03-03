package beans;

import java.io.Serializable;

import java.util.HashMap;

import java.util.Map;


/*This is the class is the bean for the web page "usermanagement.jsp".*/
public class UserManagementBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Map<String, String> userList = new HashMap<String, String>();
    /*
      Fetches a map of all users in the system.
      @return Map usersList.
     */
    public Map<String, String> getUserList() {
        return userList;
    }

    /*
      Adds a user to the Map of all users. Requires "Project leader" role.
      @param map
     */
    public void populateBean(Map<String, String> map){
    	userList.putAll(map); // get the string that the user entered in the form  	
    }
}