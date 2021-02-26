

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
		
		switch (request.getParameter("action")) {
		case "edit":
			request.getParameter("reportID");
			populateBean(db.getTimeReport("reportID"));
			TimeReportBean tb1 = new TimeReportBean();
			tb1.populateBean(request, response);
			db.updateTimeReport(tb1.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "add":
			TimeReportBean tb2 = new TimeReportBean();
			tb2.populateBean(request, response);
			db.newTimeReport(tb2.getReportValues());
			response.sendRedirect("summaryreport.jsp");
			break;
		case "remove":
			db.deleteTimeReport("someID");
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
