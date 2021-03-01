

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TimeReportServlet
 */
@WebServlet("/TimeReportServlet")
public class TimeReportServlet extends servletBase {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		HttpSession session = request.getSession();
		session.setAttribute("timeReports", db.getTimeReportIDs((String) session.getAttribute("userName")));
		
		TimeReportBean tb1 = new TimeReportBean();
		tb1.populateBean(db.getActivityReport((String) session.getAttribute("reportID")));
		session.setAttribute("timeReport", tb1.getReportValues());
			
		switch (request.getParameter("action")) {
		case "edit":
			TimeReportBean tb2 = new TimeReportBean();
			tb1.populateBean(db.getActivityReport((String) session.getAttribute("reportID")));
			session.setAttribute("timeReport", tb1.getReportValues());

			tb2.populateBean(request, response);			
			db.updateTimeReport((int) session.getAttribute("reportID"), tb2.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "add":
			TimeReportBean tb3 = new TimeReportBean();
			tb3.populateBean(request, response);
			db.newTimeReport((String) session.getAttribute("userName"), 0, "", (Integer) session.getAttribute("week"));
			db.updateTimeReport((int) session.getAttribute("reportID"), tb3.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "remove":
			db.deleteTimeReport((int) session.getAttribute("someID"));
			response.sendRedirect("summaryreport.jsp");
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
