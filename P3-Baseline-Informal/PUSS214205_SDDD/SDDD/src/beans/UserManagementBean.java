package beans;

import java.io.Serializable;

import java.util.HashMap;

import java.util.Map;


/*This is the class is the bean for the web page "usermanagement.jsp".*/
public class UserManagementBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Map<String, String> userList = new HashMap<String, String>();
    /*
      Fetches a list of all users in the system.
      @return List usersList.
     */
    public Map<String, String> getUserList() {
        return userList;
    }

    /*
      Adds a user to the list of all users. Requires "Project leader" role.
      @param request, response
     */
    public void populateBean(Map<String, String> map){
    	userList.putAll(map); // get the string that the user entered in the form  	
    }
}