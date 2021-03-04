package servlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
		List<Integer> signedTimeReportIDs = db.getSignedTimeReportIDs();
		
		for (Integer x: signedTimeReportIDs) {
			TimeReportBean signedBean = new TimeReportBean();
			signedBean.setReportID(x);
			signedBean.setTotalTime(db.getTotalMinutesFromTimeReport(x));
			signedBean.setWeek(db.getWeekFromTimeReport(x));
			signedBean.setSigned(db.getSignatureFromTimeReport(x));
			signedBean.setUsername(db.getUserNameFromTimeReport(x));
			signedReports.add(signedBean);
		}
		
		TimeReportBeanCan.sort(Comparator.comparing(b -> b.getWeek(), Comparator.nullsFirst(Comparator.naturalOrder())));
		signedReports.sort(Comparator.comparing(b -> b.getWeek(), Comparator.nullsFirst(Comparator.naturalOrder())));
		
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
		
		
		
		String submitNewReport = request.getParameter("submitNew"); //Submit button in newreport.jsp
		String editSelectedBtn = request.getParameter("editBtn");  //edit selected button in summaryreport.jsp
		String viewBtn = request.getParameter("viewBtn");			//"view selected" button in summaryreport.jsp
		String submitEdit = request.getParameter("submitEdit");		//submit button in updatereport.jsp
		String createNewNav = request.getParameter("new");			//navigation to newreport.jsp
		String viewSummary = request.getParameter("summary");
		
		
		if (viewSummary != null) {
			response.sendRedirect("summaryreport.jsp");
		}
		
		
		if (createNewNav != null) {
			response.sendRedirect("newreport.jsp");
		}
		
		if (editSelectedBtn != null) {
			List<Integer> signedReports = db.getSignedTimeReportIDs(); 
			session.setAttribute("editable", true);
			TimeReportBean tb = new TimeReportBean();
			int reportID = (Integer)session.getAttribute("reportID");
			tb.populateBean(db.getDocumentTimeD(reportID), 
					db.getDocumentTimeF(reportID), 
					db.getDocumentTimeI(reportID), 
					db.getDocumentTimeR(reportID), 
					db.getActivityReport(reportID));
			
			tb.setWeek(db.getWeekFromTimeReport(reportID));
			tb.setUsername((String)session.getAttribute("username"));
			session.setAttribute("TimeReportBean", tb);
		}
		
		if (submitNewReport != null) {
			TimeReportBean trb3 = new TimeReportBean();
			trb3.populateBean(request, response);
			int id=db.newTimeReport((String) session.getAttribute("username"), Integer.parseInt(request.getParameter("week")));
			
			db.updateDocumentTimeD(id, trb3.getReportValuesD());
			db.updateDocumentTimeI(id, trb3.getReportValuesI());
			db.updateDocumentTimeF(id, trb3.getReportValuesF());
			db.updateDocumentTimeR(id, trb3.getReportValuesR());
			db.updateActivityReport(id, trb3.getReportValuesActivity());
			db.updateTotalMinutes(id, trb3.getTotalTime());
			doGet(request,response);
		}
		
		if (viewBtn != null) {
			session.setAttribute("editable", false);
			TimeReportBean trb1 = new TimeReportBean();
			int id = (Integer) session.getAttribute("reportID");
			trb1.populateBean(db.getDocumentTimeD(id), 
					db.getDocumentTimeI(id), 
					db.getDocumentTimeF(id), 
					db.getDocumentTimeR(id), 
					db.getActivityReport(id));
			
			trb1.setWeek(db.getWeekFromTimeReport(id));
			trb1.setUsername(db.getUserNameFromTimeReport(id));
			session.setAttribute("timereport", trb1);
			response.sendRedirect("updatereport.jsp");
			
		}
		
		if (submitEdit != null) {
			TimeReportBean trb2 = new TimeReportBean();
			trb2.populateBean(request, response);
			int id = (Integer) session.getAttribute("reportID");
			
			db.updateDocumentTimeD(id, trb2.getReportValuesD());
			db.updateDocumentTimeI(id, trb2.getReportValuesI());
			db.updateDocumentTimeF(id, trb2.getReportValuesF());
			db.updateDocumentTimeR(id, trb2.getReportValuesR());
			db.updateActivityReport(id, trb2.getReportValuesActivity());
			db.updateTotalMinutes(id, trb2.getTotalTime());
			
			doGet(request, response);
		}

		
	}

}
