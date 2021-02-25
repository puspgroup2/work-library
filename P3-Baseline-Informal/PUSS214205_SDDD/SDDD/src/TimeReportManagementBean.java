import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*This class is the bean for the web page "signreport.jsp".*/
public class TimeReportManagementBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private List<Integer> timeReportList = new ArrayList<Integer>();
    private String userName;
    
    /*
      Sets list of time reports and signs(1)/unsigns(0) them. Requires "Project leader" role.
      @param request, response
     */
    public void populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer Input = request.getIntHeader("sign/unsign"); // get the check box results that the user entered in the form
    	timeReportList.add(Input);
    }

    /*
      Fetches a list of time reports. Requires "Project leader" role.
      @return List timeReportList
     */
    public List<Integer> getTimeReportList() {
        return timeReportList;
    }
    
    /*
      Fetches the user name of the project member. Requires "Project leader" role.
      @return userName
     */
    public String getUserName() {
    	return userName;
    }
    
    /*
      Sets the user name of the project member. Requires "Project leader" role.
      @return userName
     */
    public void setUserName(String userName) {
    	this.userName = userName;
    }

}