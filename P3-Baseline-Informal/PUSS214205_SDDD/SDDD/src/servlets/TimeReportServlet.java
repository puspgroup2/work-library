package servlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
	 * Handles GET request and serves summaryreport.jsp, 
	 * which displays a summary of all Time reports for the user.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<TimeReportBean> timeReports = getTimeReportList(session);
		List<TimeReportBean> signedReports = getSignedReports();
		
		session.setAttribute("signedReports", signedReports);		// Only signed
		session.setAttribute("timeReports", timeReports);		// All time reports
		response.sendRedirect("summaryreport.jsp");
	}
	
	/**
	 * This POST handles the following actions:
	 * - Submit new Time report
	 * - Edit a Time report
	 * - View a summary of a Time report
	 * - Submit edit changes to a Time report
	 * - Create a new Time report
	 * - View a summary of all Time reports
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// These parameters are set or NOT set depending on what button send the post request.
		String submitNewReport = request.getParameter("submitNew");  // Submit button in newreport.jsp
		String editSelectedBtn = request.getParameter("editBtn");    // Edit selected button in summaryreport.jsp
		String viewBtn = request.getParameter("viewBtn");		     // "View selected" button in summaryreport.jsp
		String submitEdit = request.getParameter("submitEdit");      // Submit button in updatereport.jsp
		String createNewNav = request.getParameter("new");		 	 // Navigation to newreport.jsp
		String viewSummary = request.getParameter("summary");		 // Navigation to summaryreport.jsp
		
		// Only one of the parameters can be NOT null per POST request.
		if (viewSummary != null) {
			response.sendRedirect("summaryreport.jsp");
		} 
		else if (createNewNav != null) {
			response.sendRedirect("newreport.jsp");
		} 
		else if (editSelectedBtn != null) {
			session.setAttribute("editable", true);
			sendViewTimeReportRedirect(request, response, session);
		} 
		else if (viewBtn != null) {
			session.setAttribute("editable", false);
			sendViewTimeReportRedirect(request, response, session);
		} 
		else if (submitNewReport != null) {
			TimeReportBean bean = new TimeReportBean();
			bean.populateBean(request, response);
			int reportID = db.newTimeReport(
						(String) session.getAttribute("username"), 
						Integer.parseInt(request.getParameter("week")));
			updateReport(request, response, session, db, reportID);
			doGet(request,response);
		} 
		else if (submitEdit != null) {
			TimeReportBean bean = new TimeReportBean();
			bean.populateBean(request, response);
			int reportID = Integer.parseInt(request.getParameter("reportID"));
			updateReport(request, response, session, db, reportID);
			doGet(request,response);
		}

	}
	
	/** Helper method to get all unsigned report .*/
	private List<TimeReportBean> getTimeReportList(HttpSession session) {
		List<Integer> reportIdList = db.getTimeReportIDs((String) session.getAttribute("username"));
		List<TimeReportBean> timeReports = new ArrayList<TimeReportBean>();
		
		for(Integer id : reportIdList) {
			TimeReportBean bean = new TimeReportBean();
			bean.setReportID(id);
			bean.setSigned(db.getSignatureFromTimeReport(id));
			bean.setTotalTime(db.getTotalMinutesFromTimeReport(id));
			bean.setWeek(db.getWeekFromTimeReport(id));
			timeReports.add(bean);			
		}
		
		// Rather cryptic way to sort but works fine!.
		timeReports.sort(Comparator.comparing(b -> b.getWeek(), 
						 Comparator.nullsFirst(Comparator.naturalOrder())));
		return timeReports;
	}
	
	/** Helper method to get all signed report .*/
	private List<TimeReportBean> getSignedReports() {
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
		// Rather cryptic way to sort but works fine!.
		signedReports.sort(Comparator.comparing(b -> b.getWeek(), Comparator.nullsFirst(Comparator.naturalOrder())));
		return signedReports;
	}

	/** Translates the Map from the Frontend, to the map that the database
	 *  expects. This is required since Frontend and DB uses different key names.
	 * 	The field names of the Frontend can be found in TimeReportBean.java and the db in Database.java
	 */
	private Map<String, Integer> translateFrontendToDb(Map<String, Integer> map ) {
		Map<String, Integer> translated = new HashMap<>();
		for (Map.Entry<String, Integer> entry: map.entrySet()) {
			String document = entry.getKey().split("_")[0];
			if (document.equals("total")) {
				document = "totalMinutes";
			} else if (document.equals("final")) {
				document = "finalReport";
			} else {
				document = document.toUpperCase();
			}
			translated.put(document, entry.getValue());
		}
		return translated;
	}

	/** Translates the Map from the Database, to the map that the Frontend
	 *  expects. This is required since Frontend and DB uses different key names.
	 *  The field names of the Frontend can be found in TimeReportBean.java and the db in Database.java
	 */
	private Map<String, Integer> translateDbToFrontend(Map<String, Integer> map, char character ) {
		Map<String, Integer> translated = new HashMap<>();
		for (Map.Entry<String, Integer> entry: map.entrySet()) {
			String document = entry.getKey().split("_")[0];

			if (document.equals("totalMinutes")) {
				document = String.format("total_%s", character);
			} else if (document.equals("finalReport")) {
				document = String.format("final_%s", character);
			} else {
				document = String.format("%s_%s", document.toLowerCase(), character);
			}

			translated.put(document, entry.getValue());
		}
		return translated;
	}

	/** Helper method that redirects the client to view a time report. */
	private void sendViewTimeReportRedirect(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		TimeReportBean bean = getTimeReportBean(request, session);
		session.setAttribute("timereport", bean);
		sendJsResponse(response);
	}

	/** Helper method to send response to frontend JS.
	 *  Not that this is needed instead of a redirect because the
	 *  frontend handles the page redirect.
	 */
	private void sendJsResponse(HttpServletResponse response) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		out.print("ok");
		out.flush();
	}

	/** Helper method to get and fill a time report beam. */
	private TimeReportBean getTimeReportBean(HttpServletRequest request, HttpSession session) {
		int reportID = Integer.parseInt(request.getParameter("reportID"));
		String username = (String) session.getAttribute("username");

		TimeReportBean bean = new TimeReportBean();
		bean.populateBean(
				translateDbToFrontend(db.getDocumentTimeD(reportID), 'd'),
				translateDbToFrontend(db.getDocumentTimeI(reportID), 'i'),
				translateDbToFrontend(db.getDocumentTimeF(reportID), 'f'),
				translateDbToFrontend(db.getDocumentTimeR(reportID), 'r'),
				db.getActivityReport(reportID),
				db.getTotalMinutesFromTimeReport(reportID)
				);
		bean.setReportID(reportID);
		bean.setWeek(db.getWeekFromTimeReport(reportID));
		bean.setUsername(username);

		return bean;
	}
	
	/** Helper method to update a time report. */
	private void updateReport(HttpServletRequest request, HttpServletResponse response, 
						      HttpSession session, DataBase db, int reportID) throws ServletException, IOException {
		TimeReportBean bean = new TimeReportBean();
		bean.populateBean(request, response);
		
		db.updateDocumentTimeD(reportID, translateFrontendToDb(bean.getReportValuesD()));
		db.updateDocumentTimeI(reportID, translateFrontendToDb(bean.getReportValuesI()));
		db.updateDocumentTimeF(reportID, translateFrontendToDb(bean.getReportValuesF()));
		db.updateDocumentTimeR(reportID, translateFrontendToDb(bean.getReportValuesR()));
		db.updateActivityReport(reportID, bean.getReportValuesActivity());
		db.updateTotalMinutes(reportID, bean.getTotalTime());
	}

}
