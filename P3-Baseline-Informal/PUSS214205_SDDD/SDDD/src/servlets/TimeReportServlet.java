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
			trb.setReportID(i);
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
	
	
	
	private Map<String, Integer> translateCrypticMap(Map<String, Integer> map ) {
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
	
	private Map<String, Integer> detranslateCrypticMap(Map<String, Integer> map, char character ) {
		Map<String, Integer> translated = new HashMap<>();
		for (Map.Entry<String, Integer> entry: map.entrySet()) {
			String document = entry.getKey().split("_")[0];
			
			if (document.equals("totalMinutes")) {
				document = "total";
			} else if (document.equals("finalReport")) {
				document = "final";
			} else {
				document = String.format("%s_%s", document.toLowerCase(), character);
			}

			translated.put(document, entry.getValue());
		}
		return translated;
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
			int reportID = Integer.parseInt(request.getParameter("reportID"));
			
			tb.populateBean(
					detranslateCrypticMap(db.getDocumentTimeD(reportID), 'd'),
					detranslateCrypticMap(db.getDocumentTimeI(reportID), 'i'),
					detranslateCrypticMap(db.getDocumentTimeF(reportID), 'f'),
					detranslateCrypticMap(db.getDocumentTimeR(reportID), 'r'),
					db.getActivityReport(reportID),
					db.getTotalMinutesFromTimeReport(reportID)
					);
			tb.setWeek(db.getWeekFromTimeReport(reportID));
			tb.setUsername((String)session.getAttribute("username"));
			session.setAttribute("TimeReportBean", tb);
			
			ServletOutputStream out = response.getOutputStream();
			out.print("ok");
			out.flush();		
		}
		
		if (viewBtn != null) {
			session.setAttribute("editable", false);
			TimeReportBean bean = new TimeReportBean();
			int reportID = Integer.parseInt(request.getParameter("reportID"));
			
			bean.populateBean(
					detranslateCrypticMap(db.getDocumentTimeD(reportID), 'd'),
					detranslateCrypticMap(db.getDocumentTimeI(reportID), 'i'),
					detranslateCrypticMap(db.getDocumentTimeF(reportID), 'f'),
					detranslateCrypticMap(db.getDocumentTimeR(reportID), 'r'),
					db.getActivityReport(reportID),
					db.getTotalMinutesFromTimeReport(reportID)
					);
			
			bean.setWeek(db.getWeekFromTimeReport(reportID));
			bean.setUsername(db.getUserNameFromTimeReport(reportID));
			session.setAttribute("timereport", bean);
			ServletOutputStream out = response.getOutputStream();
			out.print("ok");
			out.flush();
		}
		
		if (submitNewReport != null) {
			TimeReportBean bean = new TimeReportBean();
			bean.populateBean(request, response);
			int reportID = db.newTimeReport(
						(String) session.getAttribute("username"), 
						Integer.parseInt(request.getParameter("week")));
			
			updateReport(request, response, session, db, reportID);
			doGet(request,response);
		}
		
		
		if (submitEdit != null) {
			TimeReportBean bean = new TimeReportBean();
			bean.populateBean(request, response);
			int reportID = (Integer) session.getAttribute("reportID");
			
			updateReport(request, response, session, db, reportID);
			doGet(request,response);
		}
		
	}
	
	/** Helper method to update a time report. 
	 * @throws IOException 
	 * @throws ServletException 
	 * */
	private void updateReport(HttpServletRequest request, HttpServletResponse response, 
						      HttpSession session, DataBase db, int reportID) throws ServletException, IOException {
		TimeReportBean bean = new TimeReportBean();
		bean.populateBean(request, response);
		
		db.updateDocumentTimeD(reportID, translateCrypticMap(bean.getReportValuesD()));
		db.updateDocumentTimeI(reportID, translateCrypticMap(bean.getReportValuesI()));
		db.updateDocumentTimeF(reportID, translateCrypticMap(bean.getReportValuesF()));
		db.updateDocumentTimeR(reportID, translateCrypticMap(bean.getReportValuesR()));
		db.updateActivityReport(reportID, bean.getReportValuesActivity());
		db.updateTotalMinutes(reportID, bean.getTotalTime());
	}

}
