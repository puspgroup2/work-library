package servlets;
import java.io.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import beans.UserBean;
import database.DataBase;
import handlers.MailHandler;
import handlers.PasswordHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogIn
 * 
 * A log-in page. 
 * 
 * The first thing that happens is that the user is logged out if he/she is logged in. 
 * Then the user is asked for name and password. 
 * If the user is logged in he/she is directed to the functionality page. 
 * 
 * @author Martin Host
 * @version 1.0
 * 
 */
@WebServlet("/LogIn")

public class LogIn extends ServletBase 
{
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Implementation of all input to the servlet. All post-messages are forwarded to this method. 
	 * 
	 * First logout the user, then check if he/she has provided a username and a password. 
	 * If he/she has, it is checked with the database and if it matches then the session state is 
	 * changed to login, the username that is saved in the session is updated, and the user is 
	 * relocated to the functionality page. 
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();

		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username == null || password == null) {
			response.sendError(LOGIN_FALSE);
			session.setAttribute("errorMessage", LOGIN_FALSE);
		} else {
			UserBean ub = new UserBean();
			ub.populateBean(username, password);
			if (db.checkLogin(ub)) {
				ub.setRole(db.getRole(username));
				session.setAttribute("username", ub.getUserName());
				session.setAttribute("role", ub.getRole());
				session.setAttribute("state", LOGIN_TRUE);
				response.sendRedirect("index.jsp");
			} else {
				//failed login
				session.setAttribute("state", LOGIN_FALSE);
				session.setAttribute("errorMessage", LOGIN_FALSE);
				response.sendRedirect("login.jsp");
			}
		}
	}


	/**
	 * All requests are forwarded to the doGet method. 
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		System.out.println("test");
		String userName = request.getParameter("username");
		String mail = db.getEmail(userName);

		if(mail != null) {
			MailHandler mh = new MailHandler();
			String pw = PasswordHandler.generatePassword();
			mh.sendPasswordMail(mail, "placeholder", pw);

			String hashedPw = PasswordHandler.hashPassword(pw, PasswordHandler.generateSalt());
			
			if (db.changePassword(userName, hashedPw)) {
				//TODO Handle potential error
			}
		} else {
			response.sendRedirect("login.jsp");
		}
	}
}
