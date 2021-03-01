

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
		
		//When the site is loaded
		TimeReportManagementBean trmb = new TimeReportManagementBean();
		
		ArrayList<Integer> allTimeReportIDs;
		ArrayList<Integer> signedReportIDs = (ArrayList<Integer>) db.getUnsignedTimeReportIDs();
		ArrayList<Integer> unsignedReportIDs = (ArrayList<Integer>) db.getSignedTimeReportIDs();
		signedReportIDs.addAll(unsignedReportIDs); //Add the ID:s of signed and usigned time reports
		allTimeReportIDs = signedReportIDs; //Rename variable
		
		HashMap<Integer, Boolean> allTimeReports = new HashMap<>();
		
		for(Integer s : allTimeReportIDs) { 
			boolean signed;
			if (db.getSignatureFromTimeReport(s) == null) {
				signed = false;
			} else {
				signed = true;
			}
			
			allTimeReports.put(s, signed);//Fills the HashMap so it contains all time reports and signed/unsigned
		}
		trmb.populateBean(allTimeReports);
		
		
		session.setAttribute("TimeReportManagementBean", trmb);
		
		//When someone presses the "Submit"-button
		String userName = (String)session.getAttribute("username");
		TimeReportManagementBean trmb1 = new TimeReportManagementBean();
		trmb1 = (TimeReportManagementBean) request.getAttribute("TimeReportManagementBean");
		
		for(Map.Entry<Integer, Boolean> entry : trmb1.getTimeReportList().entrySet()){
			db.setSigned(entry.getValue(), userName,  entry.getKey());
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
