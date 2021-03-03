package beans;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*This class is the bean for the web pages "newReport.jsp, editReport.jsp and summaryReport.jsp".*/
public class TimeReportBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private int week = 0;
    private String username;
    private String signed;
    private int totalTime;
    private int reportID = -1;
    private Map<String, Integer> reportValuesD = new HashMap<String, Integer>();
    private Map<String, Integer> reportValuesI = new HashMap<String, Integer>();
    private Map<String, Integer> reportValuesF = new HashMap<String, Integer>();
    private Map<String, Integer> reportValuesR = new HashMap<String, Integer>();
    private Map<String, Integer> reportValuesActivity = new HashMap<String, Integer>();
    private String[] fieldNamesD = {"sdp_d", "srs_d", "svvs_d", "stldd_d", "svvi_d", "sddd_d", "svvr_d", "ssd_d", "fianl_d", "total_d"}; 
    private String[] fieldNamesI = {"sdp_i", "srs_i", "svvs_i", "stldd_i", "svvi_i", "sddd_i", "svvr_i", "ssd_i", "final_i", "total_i"}; 
    private String[] fieldNamesF = {"sdp_f", "srs_f", "svvs_f", "stldd_f", "svvi_f", "sddd_f", "svvr_f", "ssd_f", "final_f", "total_f"};
    private String[] fieldNamesR = {"sdp_r", "srs_r", "svvs_r", "stldd_r", "svvi_r", "sddd_r", "svvr_r", "ssd_r", "final_r", "total_r"};
    private String[] fieldNamesActivity = {	"functional_test", "system_test", "regression_test", "meeting", "lecture", "exercise", "computer_exercise", "home_reading", "other"};

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
    Fetches the signed time report.
    @return signed
   */
    public String getSigned() {
    	return signed;
    }
    /*
    Sets the time report as signed.
    @param signed
   */
    public void setSigned(String signed) {
    	this.signed = signed;
    }
    /*
    
    Fetches the total time that reported.
    @return totalTime
   */
    public int getTotalTime() {
    	return totalTime;
    }
    /*
    Sets the total time for a specified week .
    @param totalTime
   */
    public void setTotalTime(int totalTime) {
    	this.totalTime = totalTime;
    }
    
    public void setTotalTime(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String Input = request.getParameter("totaltime");
    	totalTime = Integer.parseInt(Input);
    }
    
    /*
     Fetches the ID of the report.
     @return reportID
     */
    public int getReportID() {
    	return this.reportID;
    }
    /*
     Sets ID to the report.
     @param reportID
     */
    public void setReportID(int reportID) {
    	this.reportID=reportID;
    }
    /*
     Sets the username for the current user.
     @param username
     */
    public void setUsername(String username) {
    	this.username = username;
    }
    /*
     Fetches the username.
     @return username
     */
    public String getUsername() {
    	return this.username ;
    }
    
    /*
      Fetches a list of the reported values, the values are in minutes.
      @return map reportValues
     */
    public Map<String, Integer> getReportValuesD() {
        return reportValuesD;
    }

    public Map<String, Integer> getReportValuesI() {
        return reportValuesI;
    }
    
    public Map<String, Integer> getReportValuesF() {
        return reportValuesF;
    }
    
    public Map<String, Integer> getReportValuesR() {
        return reportValuesR;
    }
    
    public Map<String, Integer> getReportValuesActivity() {
        return reportValuesActivity;
    }
    
    /*
    Sets the reported values into databaseData map .
    @param databaseData
   */
    public void populateBean(Map<String, Integer> databaseDataD, Map<String, Integer> databaseDataI, Map<String, Integer> databaseDataF, Map<String, Integer> databaseDataR, Map<String, Integer> databaseDataActivity) {
    	reportValuesD = databaseDataD;
    	reportValuesI = databaseDataI;
    	reportValuesF = databaseDataF;
    	reportValuesR = databaseDataR;
    	reportValuesActivity = databaseDataActivity;
    }

    /*
      Puts into the reported values map the user values and the fieldNames
      @param request, response
     */
    public void populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    for(int i = 0; i < fieldNamesD.length; i++) {
	    	String Input = request.getParameter(fieldNamesD[i]); 
	    	if (request.getParameter(Input) == null) {
	    		reportValuesD.put(fieldNamesD[i], 0);
	    	} else {
	    		reportValuesD.put(fieldNamesD[i], Integer.parseInt(Input));
	    	}
	    	}
	    	
    	for(int i = 0; i < fieldNamesI.length; i++) {
        	String Input = request.getParameter(fieldNamesI[i]); 
        	if (request.getParameter(Input) == null) {
        		reportValuesI.put(fieldNamesI[i], 0);
        	} else {
        		reportValuesI.put(fieldNamesI[i], Integer.parseInt(Input));
        	}
        	}
    	
    	for(int i = 0; i < fieldNamesF.length; i++) {
        	String Input = request.getParameter(fieldNamesF[i]); 
        	if (request.getParameter(Input) == null) {
        		reportValuesF.put(fieldNamesF[i], 0);
        	} else {
        		reportValuesF.put(fieldNamesF[i], Integer.parseInt(Input));
        	}
        	}
    	
    	for(int i = 0; i < fieldNamesR.length; i++) {
        	String Input = request.getParameter(fieldNamesR[i]); 
        	if (request.getParameter(Input) == null) {
        		reportValuesR.put(fieldNamesR[i], 0);
        	} else {
        		reportValuesR.put(fieldNamesR[i], Integer.parseInt(Input));
        	}
        	}
    	
    	for(int i = 0; i < fieldNamesActivity.length; i++) {
        	String Input = request.getParameter(fieldNamesActivity[i]); 
        	if (request.getParameter(Input) == null) {
        		reportValuesActivity.put(fieldNamesActivity[i], 0);
        	} else {
        		reportValuesActivity.put(fieldNamesActivity[i], Integer.parseInt(Input));
        	}
        	}
    }

}
