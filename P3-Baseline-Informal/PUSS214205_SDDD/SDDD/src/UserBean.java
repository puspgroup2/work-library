
import java.io.Serializable;


 /*This class is the bean for the web page "login.jsp".*/
 
public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private String userName;
    private String email;
    private String role;
    
    public void populateBean(String userName, String email, String role) {
    	this.userName = userName;
    	this.email = email;
    	this.role = role;
    }

    /*
      Fetches the user name of the user that is logged in.
      @return userName.
     */
    public String getUserName() {
        return userName;
    }

    /*
      Sets the user name of the user that is logged in.
      @param userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*
      Fetches the email of the user that is logged in.
      @return email.
     */
    public String getEmail() {
        return email;
    }

    /*
      Sets the email of the user that is logged in.
      @param email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
      Fetches the role of the user that is logged in.
      @return role.
     */
    public String getRole() {
        return role;
    }

    /*
      Sets the role of the user that is logged in.
      @param role.
     */
    public void setRole(String role) {
        this.role = role;
    }
}
