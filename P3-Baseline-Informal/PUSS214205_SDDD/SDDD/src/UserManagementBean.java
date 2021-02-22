import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*This is the class is the bean for the web page "usermanagement.jsp".*/
public class UserManagementBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private String userName;
    private String role;
    private List<String> userList = new ArrayList<String>();

    /*
      Fetches the user name of the user that is to be created. Requires "Admin" role.
      @return userName.
     */
    public String getUsername(){
        return userName;
    }

    /*
      Sets the user name of the user that is to be created. Requires "Admin" role.
      @param userName
     */
    public void setUsername(String userName){
        this.userName = userName;
    }

    /*
      Fetches a list of all users in the system.
      @return List usersList.
     */
    public List<String> getUserList() {
        return userList;
    }

    /*
      Sets a list of all users in the system.
      @param List userList
     */
    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    /*
      Fetches the role of the user that is to be created. Requires "Project leader" role.
      @return role
     */
    public String getRole() {
        return role;
    }

    /*
      Sets the role of the user that is to be created. Requires "Project leader" role.
      @return role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /*
      Adds a user to the list of all users. Requires "Project leader" role.
      @param request, response
     */
    public void populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String Input = request.getHeader("userName"); // get the string that the user entered in the form
    	userList.add(Input);
    }

}