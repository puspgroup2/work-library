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
    private String signed;
    private int totalTime;
    private int reportID = -1;
    private Map<String, Integer> reportValues = new HashMap<String, Integer>();
    private String[] fieldNames = {"sdp_d", "sdp_i", "sdp_f", "sdp_r", "sdp_total",
    							   "srs_d", "srs_i", "srs_f", "srs_r", "srs_total",
    							   "svvs_d", "svvs_i", "svvs_f", "svvs_r", "svvs_total",
    							   "stldd_d", "stldd_i", "stldd_f", "stldd_r", "stldd_total",
    							   "svvi_d", "svvi_i", "svvi_f", "svvi_r", "svvi_total",
    							   "sddd_d", "sddd_i", "sddd_f", "sddd_r", "sddd_total",
    							   "svvr_d", "svvr_i", "svvr_f", "svvr_r", "svvr_total",
    							   "ssd_d", "ssd_i", "ssd_f", "ssd_r", "ssd_total",
    							   "final_d", "final_i", "final_f", "final_r", "final_total",
    							   "total_d", "total_i", "total_f", "total_r", "total_total",
    							   "functional_test", "system_test", "regression_test",
    							   "meeting", "lecture", "exercise", "computer_exercise", "home_reading", "other"};

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
    
    public int getReportID() {
    	return this.reportID;
    }
    
    public void setReportID(int reportID) {
    	this.reportID=reportID;
    }

    /*
      Fetches a list of the reported values, the values are in minutes.
      @return map reportValues
     */
    public Map<String, Integer> getReportValues() {
        return reportValues;
    }
    
    /*
    Sets the reported values into databaseData map .
    @param databaseData
   */
    public void populateBean(Map<String, Integer> databaseData) {
    	this.reportValues = databaseData;
    }

    /*
      Puts into the reported values map the user values and the fieldNames
      @param request, response
     */
    public void populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	for(int i = 0; i < fieldNames.length; i++) {
    	String Input = request.getParameter(fieldNames[i]); 
    	reportValues.put(fieldNames[i], Integer.parseInt(Input));
    	}
    }

}
