package servlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		List<Integer> reportIdList = db.getTimeReportIDs((String) session.getAttribute("username"));
		session.setAttribute("timeReports", reportIdList);
		List<TimeReportBean> TimeReportBeanCan = new ArrayList<TimeReportBean>();
		for(Integer i : reportIdList) {
			TimeReportBean trb = new TimeReportBean();
			trb.setSigned(db.getSignatureFromTimeReport(i));
			trb.setTotalTime(db.getTotalMinutesFromTimeReport(i));
			trb.setWeek(db.getWeekFromTimeReport(i));
			TimeReportBeanCan.add(trb);			
		}
		session.setAttribute("TimeReportBeanCan", TimeReportBeanCan);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		
		TimeReportBean trb1 = new TimeReportBean();
		trb1.populateBean(db.getActivityReport((Integer) session.getAttribute("reportID")));
		session.setAttribute("timeReport", trb1);
		
		switch (request.getParameter("action")) {
		case "edit":
			List<Integer> signedReports = db.getSignedTimeReportIDs(); 
			if(!signedReports.contains(session.getAttribute("reportID"))) {
				session.setAttribute("editable", true);
				response.sendRedirect("updatereport.jsp");
			}else {
				session.setAttribute("editable", false);
				response.sendRedirect("updatereport.jsp");
			}
			break;
		case "submitEdit":
			TimeReportBean trb2 = new TimeReportBean();
			trb2.populateBean(request, response);			
			db.updateTimeReport((int) session.getAttribute("reportID"), trb2.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "view":
			session.setAttribute("editable", false);
			response.sendRedirect("updatereport.jsp");
			break;
		case "new":
			response.sendRedirect("newreport.jsp");
		case "submitNew":
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
