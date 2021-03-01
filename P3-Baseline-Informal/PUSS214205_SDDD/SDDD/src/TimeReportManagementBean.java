
import java.io.Serializable;

import java.util.HashMap;

import java.util.Map;


/*This class is the bean for the web page "signreport.jsp".*/
public class TimeReportManagementBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Map<String, Integer> timeReportList = new HashMap<>();
    
    /*
      Sets list of time reports and signs(1)/unsigns(0) them. Requires "Project leader" role.
      @param request, response
     */
    public void populateBean(Map<String, Integer> map){ // get the check box results that the user entered in the form
    	timeReportList.putAll(map);
    }

    /*
      Fetches a list of time reports. Requires "Project leader" role.
      @return List timeReportList
     */
    public Map<String, Integer> getTimeReportList() {
        return timeReportList;
    }
}