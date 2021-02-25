

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PasswordChangerServlet
 */
@WebServlet("/PasswordChangerServlet")
public class PasswordChangerServlet extends servletBase 
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DataBase db = new DataBase();
		
		PasswordHandler ph = new PasswordHandler();
		if (db.setPassword(ph.hashPassword(session.getAttribute("password")))) {//Gets the password attribute, hashes the password, sets the password.
			//change successful
			request.setAttribute("errorMessage", true);
		} else {
			//change not successful
			request.setAttribute("errorMessage", false);
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
