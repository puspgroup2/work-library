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
		
		List<TimeReportBean> signedReports = new ArrayList<TimeReportBean>();
		
		for (Integer x: db.getSignedTimeReportIDs()) {
			TimeReportBean signedBean = new TimeReportBean();
			signedBean.setReportID(x);
			signedBean.setTotalTime(db.getTotalMinutesFromTimeReport(x));
			signedBean.setWeek(db.getWeekFromTimeReport(x));
			signedBean.setSigned(db.getSignatureFromTimeReport(x));
			signedBean.setUsername(db.getUserNameFromTimeReport(x));
			signedReports.add(signedBean);
		}
		
		session.setAttribute("signedReports", signedReports);
		
		
		
		
		
		
		session.setAttribute("TimeReportBeanCan", TimeReportBeanCan);
		response.sendRedirect("summaryreport.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		
		TimeReportBean trb1 = new TimeReportBean();
		//trb1.populateBean(db.getActivityReport((Integer) session.getAttribute("reportID")));
		session.setAttribute("timeReport", trb1);
		
		String submitNewReport = request.getParameter("submitNew"); //Submit button in newreport.jsp
		String editSelectedBtn = request.getParameter("editBtn");  //edit selected button in summaryreport.jsp
		String viewBtn = request.getParameter("viewBtn");			//"view selected" button in summaryreport.jsp
		String submitEdit = request.getParameter("submitEdit");		//submit button in updatereport.jsp
		String createNewNav = request.getParameter("new");			//navigation to newreport.jsp
		
		if (createNewNav != null) {
			response.sendRedirect("newreport.jsp");
		}
		
		if (editSelectedBtn != null) {
			List<Integer> signedReports = db.getSignedTimeReportIDs(); 
			if(!signedReports.contains(session.getAttribute("reportID"))) {
				session.setAttribute("editable", true);
				response.sendRedirect("updatereport.jsp");
			}else {
				session.setAttribute("editable", false);
				response.sendRedirect("updatereport.jsp");
			}
		}
		
		if (submitNewReport != null) {
			TimeReportBean tb3 = new TimeReportBean();
			tb3.populateBean(request, response);
			int id=db.newTimeReport((String) session.getAttribute("username"), Integer.parseInt(request.getParameter("week")));
			
			db.updateActivityReport(id, tb3.getReportValues());
			doGet(request,response);
		}
		
		if (viewBtn != null) {
			session.setAttribute("editable", false);
			response.sendRedirect("updatereport.jsp");
			
		}
		
		if (submitEdit != null) {
			TimeReportBean trb2 = new TimeReportBean();
			trb2.populateBean(request, response);			
			db.updateActivityReport((int) session.getAttribute("reportID"), trb2.getReportValues());
			response.sendRedirect("summaryreport.jsp");
		}
		
		
		/*
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
			db.updateActivityReport((int) session.getAttribute("reportID"), trb2.getReportValues());
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
			db.updateActivityReport((int) session.getAttribute("reportID"), tb3.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "remove":
			if(session.getAttribute("role") == "PG") {
			db.deleteTimeReport((int) session.getAttribute("reportID"));
			response.sendRedirect("summaryreport.jsp");
			}
			break;
		}
		*/
		//doGet(request, response);
	}

}
