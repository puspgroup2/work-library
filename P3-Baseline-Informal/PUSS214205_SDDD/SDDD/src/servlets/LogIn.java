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

public class LogIn extends ServletBase {
	private static final long serialVersionUID = 1L;
	private final int USER_LOGIN_FAILED_ = 0;
	private final int PW_CHANGE_FAILED_ = 1;
	private final int PW_CHANGE_SUCCESS_ = 2;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogIn() {
		super();
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
	
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username == null || password == null) {
			session.setAttribute("message", USER_LOGIN_FAILED_);
		} else {
			UserBean ub = new UserBean();
			String salt = db.getSalt(username);
			if (salt != null) {
				String hashPassword = PasswordHandler.hashPassword(password, salt);
				ub.populateBean(username, hashPassword);
				if (db.checkLogin(ub)) {
					ub.setRole(db.getRole(username));
					session.setAttribute("username", ub.getUserName());
					session.setAttribute("role", ub.getRole());
					session.setAttribute("state", LOGIN_TRUE);
					response.sendRedirect("index.jsp");
				} else {
					//---------Failed Login--------------
					session.setAttribute("message", USER_LOGIN_FAILED_);
					response.sendRedirect("login.jsp");
				}
			} else {
				//------------Failed Login----------------
				session.setAttribute("message", USER_LOGIN_FAILED_);
				response.sendRedirect("login.jsp");
			}
		}
	}

	/**
	 * All requests are forwarded to the doGet method. 
	 * ,
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		String userName = request.getParameter("username");
		String mail = db.getEmail(userName);

		if(mail != null) {
			MailHandler mh = new MailHandler();
			String pw = PasswordHandler.generatePassword();
			mh.sendPasswordMail(mail, userName, pw);
			
			if (db.changePassword(userName, PasswordHandler.hashPassword(pw, db.getSalt(userName)))) {
				//TODO Handle potential error
			}
			session.setAttribute("message", PW_CHANGE_SUCCESS_);
			response.sendRedirect("login.jsp");
		} else {
			session.setAttribute("message", PW_CHANGE_FAILED_);
			response.sendRedirect("login.jsp");
		}
	}
}
