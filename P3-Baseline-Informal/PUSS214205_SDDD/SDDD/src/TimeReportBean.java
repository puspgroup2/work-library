import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*This class is the bean for the web pages "newReport.jsp, editReport.jsp and summaryReport.jsp".*/
public class TimeReportBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private String userName;
    private int week = 0;
    private List<Integer> reportValues = new ArrayList<Integer>();

    /*
      Fetches the userName that the time report belong to.
      @return UserName
     */
    public String getUserName() {
        return userName;
    }

    /*
      Sets the user name that the time report belong to.
      @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*
      Fetches the week that the report was created for.
      @return week
     */
    public int getWeek() {
        return week;
    }

    /*
      Sets the week that the time report was created for.
      @param week
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /*
      Fetches a list of the reported values, the values are in minutes.
      @return list reportValues
     */
    public List<Integer> getReportValues() {
        return reportValues;
    }

    /*
      Sets the reported values into a list, the values are in minutes.
      @param request, response
     */
    public void populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int Input = request.getIntHeader("reportValues"); // get the values that the user entered in the form
    	reportValues.add(Input);
    }

}
