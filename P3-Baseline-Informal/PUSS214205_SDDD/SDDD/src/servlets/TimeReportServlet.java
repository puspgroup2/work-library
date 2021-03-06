package servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TimeReportBean;

/**
 * This servlet handles most managements for time report handling-
 */
@WebServlet("/TimeReportServlet")
public class TimeReportServlet extends ServletBase {

	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET request and serves summaryreport.jsp, which displays a summary of
	 * all Time reports for the user.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<TimeReportBean> timeReports = getTimeReportList(session);
		List<TimeReportBean> signedReports = getSignedReports();

		session.setAttribute("signedReports", signedReports); // Only signed
		session.setAttribute("timeReports", timeReports); // All time reports
		response.sendRedirect("summaryreport.jsp");
	}

	/**
	 * This POST handles the following actions: - Submit new Time report - Edit a
	 * Time report - View a summary of a Time report - Submit edit changes to a Time
	 * report - Create a new Time report - View a summary of all Time reports
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		// These parameters are set or NOT set depending on what button send the post
		// request.
		String submitNewReport = request.getParameter("submitNew"); // Submit button in newreport.jsp
		String editSelectedBtn = request.getParameter("editBtn"); // Edit selected button in summaryreport.jsp
		String viewBtn = request.getParameter("viewBtn"); // "View selected" button in summaryreport.jsp
		String submitEdit = request.getParameter("submitEdit"); // Submit button in updatereport.jsp
		String createNewNav = request.getParameter("new"); // Navigation to newreport.jsp
		String viewSummary = request.getParameter("summary"); // Navigation to summaryreport.jsp
		String getUsersReport = request.getParameter("getUsersReport");

		// Only one of the parameters can be NOT null per POST request.
		if (viewSummary != null) {
			response.sendRedirect("summaryreport.jsp");
		} else if (createNewNav != null) {
			response.sendRedirect("newreport.jsp");
		} else if (editSelectedBtn != null) {
			session.setAttribute("editable", true);
			sendViewTimeReportRedirect(request, response, session);
		} else if (viewBtn != null) {
			session.setAttribute("editable", false);
			sendViewTimeReportRedirect(request, response, session);
		} else if (submitNewReport != null) {
			TimeReportBean bean = new TimeReportBean();
			bean.populateBean(request, response);
			try {
				int reportID = db.newTimeReport((String) session.getAttribute("username"),
						Integer.parseInt(request.getParameter("week")));
				if (reportID != 0) {
					updateReport(request, response, session, db, reportID);
					doGet(request, response);
					session.setAttribute("reportError", 0);
				} else {
					session.setAttribute("reportError", 1);
					response.sendRedirect("newreport.jsp");
				}
			} catch (NumberFormatException e) {
				session.setAttribute("reportError", 2);
				response.sendRedirect("newreport.jsp");
			}
		} else if (submitEdit != null) {
			TimeReportBean bean = new TimeReportBean();
			bean.populateBean(request, response);
			int reportID = Integer.parseInt(request.getParameter("reportID"));
			updateReport(request, response, session, db, reportID);
			doGet(request, response);
		} else if (getUsersReport != null) {
			List<TimeReportBean> usersTimeReports = getTimeReportList(getUsersReport);

			session.setAttribute("timeReports", usersTimeReports);
			response.sendRedirect("summaryreport.jsp");
		}
	}
}
