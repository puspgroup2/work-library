package servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TimeReportBean;
import database.DataBase;

/**
 * Servlet implementation class TimeReportServlet
 */
@WebServlet("/TimeReportServlet")
public class TimeReportServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		session.setAttribute("timeReports", db.getTimeReportIDs((String) session.getAttribute("username")));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		
		TimeReportBean tb1 = new TimeReportBean();
		tb1.populateBean(db.getActivityReport((Integer) session.getAttribute("reportID")));
		session.setAttribute("timeReport", tb1);
		
		switch (request.getParameter("action")) {
		case "edit":
			TimeReportBean tb2 = new TimeReportBean();
			tb2.populateBean(request, response);			
			db.updateTimeReport((int) session.getAttribute("reportID"), tb2.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "add":
			TimeReportBean tb3 = new TimeReportBean();
			tb3.populateBean(request, response);
			db.newTimeReport((String) session.getAttribute("username"), (Integer) session.getAttribute("week"));
			db.updateTimeReport((int) session.getAttribute("reportID"), tb3.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "remove":
			if(session.getAttribute("role") == "PG") {
			db.deleteTimeReport((int) session.getAttribute("reportID"));
			response.sendRedirect("summaryreport.jsp");
			}
			break;
		}
		doGet(request, response);
	}

}
