package servlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TimeReportBean;
import beans.TimeReportManagementBean;
import database.DataBase;

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
		db.connect();
		HttpSession session = request.getSession();

		System.out.println("Get");
		
		List<TimeReportBean> unsignedReports = new ArrayList<TimeReportBean>();
		for(Integer id: db.getUnsignedTimeReportIDs()) {
			TimeReportBean bean = new TimeReportBean();
			bean.setReportID(id);
			bean.setTotalTime(db.getTotalMinutesFromTimeReport(id));
			bean.setWeek(db.getWeekFromTimeReport(id));
			bean.setSigned(db.getSignatureFromTimeReport(id));
			bean.setUsername(db.getUserNameFromTimeReport(id));
			unsignedReports.add(bean);			
		}
		
		
		
		session.setAttribute("unsignedReports", unsignedReports);
		response.sendRedirect("signreport.jsp");
	}

	/**
	 * Parses raw string data to a list of integers.
	 * @param rawData raw string
	 * @return List of integers
	 */
	private List<Integer> unStringifiy(String rawData) {
		List<Integer> ids = new ArrayList<Integer>();
		if (!rawData.equals("[]")) {
			for (String rawId: rawData.split(",")) {
				String id = rawId.split("\"")[1];
				ids.add(Integer.parseInt(id));
			}			
		}
		return ids;
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("username");
		
		switch (request.getParameter("input")) {
		case "sign":
			// The timereports come as a string in the format of: ["x1", "x2"]
			// We need to parse this into a Java list.
			String unsignedReports = request.getParameter("timeReports");
			List<Integer> ids = unStringifiy(unsignedReports);
			
			for (Integer id: ids) {
				db.setSigned(true, userName, id);	
			}
			break;
		}
		
		doGet(request, response);
	}
}
