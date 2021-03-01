

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TimeReportManagementServlet
 */
@WebServlet("/TimeReportManagementServlet")
public class TimeReportManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		HttpSession session = request.getSession();
		TimeReportManagementBean trmb = new TimeReportManagementBean();
		
		ArrayList<String> signedReportIDs = (ArrayList<String>) db.getUnsignedTimeReportIDs();
		ArrayList<String> unsignedReportIDs = (ArrayList<String>) db.getSignedTimeReportIDs();
		signedReportIDs.addAll(unsignedReportIDs); // All timereport ID:s
		HashMap<String, Integer> allTimeReports = new HashMap<>();
		
		for(String s : signedReportIDs) {
			int week = db.getWeekFromTimeReport(s);
			String name = db.getUserNameFromTimeReport(s);
			
			allTimeReports.put(name, week);
		}
		trmb.populateBean(allTimeReports);
		
		
		session.setAttribute("TimeReportManagementBean", trmb);
		
		//När någon klickar submit
		TimeReportManagementBean trmb1 = new TimeReportManagementBean();
		trmb1 = (TimeReportManagementBean) request.getAttribute("TimeReportManagementBean");
		
		for(Map.Entry<String, Boolean> entry : trmb1.getTimeReportList().entrySet()){
			db.signUnsignTimeReport(entry.getKey(), entry.getValue());
		}
		
		response.sendRedirect("signreport.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
