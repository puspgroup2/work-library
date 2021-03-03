package servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBean;
import database.DataBase;
import handlers.PasswordHandler;

/**
 * Servlet implementation class PasswordChangerServlet
 */
@WebServlet("/PasswordChangerServlet")
public class PasswordChangerServlet extends ServletBase 
{
	private static final long serialVersionUID = 1L;
	private final int PW_CHANGE_SUCCESSFUL_ = 2;
	private final int PW_CHANGE_FAILED_NETWORK_ERROR_ = 1;
	private final int PW_CHANGE_FAILED_IDENTICAL_PASSWORDS_ = 0;
	private final int PW_CHANGE_FAILED_FALSE_CURRENT_PASSWORD_ = 3;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DataBase db = new DataBase();
		db.connect();
		String username = (String) session.getAttribute("username");
		String newPw = (String) request.getParameter("password");
		String oldPw = (String) request.getParameter("oldPassword");
		String salt = db.getSalt(username);
		UserBean ub = new UserBean();
		ub.populateBean(username, PasswordHandler.hashPassword(oldPw, salt));
		System.out.println(PasswordHandler.hashPassword(oldPw, salt));
		if(db.checkLogin(ub)) {
			if (!newPw.equals(oldPw)) {
				if (db.changePassword(username, PasswordHandler.hashPassword(newPw, salt))) {//sets the password.
					//change successful
					session.setAttribute("passwordMessage", PW_CHANGE_SUCCESSFUL_);
				} else {
					//change not successful
					session.setAttribute("passwordMessage", PW_CHANGE_FAILED_NETWORK_ERROR_);
				}
			} else {
				session.setAttribute("passwordMessage", PW_CHANGE_FAILED_IDENTICAL_PASSWORDS_);
			}
		} else {
			session.setAttribute("passwordMessage", PW_CHANGE_FAILED_FALSE_CURRENT_PASSWORD_);
		}
		response.sendRedirect("changepassword.jsp");
		doGet(request, response);
	}
}