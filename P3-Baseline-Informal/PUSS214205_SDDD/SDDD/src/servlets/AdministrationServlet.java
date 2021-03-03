package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserManagementBean;
import database.DataBase;
import handlers.MailHandler;
import handlers.PasswordHandler;

@WebServlet("/AdministrationServlet")
public class AdministrationServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	HashMap<String, String> memberMap;
	UserManagementBean umb;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		UserManagementBean umb = new UserManagementBean();
		Map<String, String> memberMap = new HashMap<String, String>();
		ArrayList<String> memberNames = (ArrayList<String>) db.getUsers();
		for (String s : memberNames) {
			if(!s.equals("admin")) {
				memberMap.put(s, db.getEmail(s));
			}
		}
		umb.populateBean(memberMap);
		session.setAttribute("AdministrationBean", umb);
		response.sendRedirect("administration.jsp");
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String removeBtn = request.getParameter("Remove");
		String addBtn = request.getParameter("Add");
		String username = request.getParameter("username");
		String mail = request.getParameter("mail");

		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		
		HashMap<String, String> mapM = new HashMap<String, String>();
		ArrayList<String> memberNames = (ArrayList<String>) db.getUsers();
		
		for (String s : memberNames) {
			if(!s.equals("admin")) {
				mapM.put(s, db.getEmail(s));
			}
		}
		
		boolean emptyString = false; 

		if(removeBtn != null) { 
			for(Map.Entry<String, String> member : mapM.entrySet()) {
				if(member.getKey().equals(request.getParameter(member.getKey())) && !member.getKey().equals(request.getParameter("admin"))) {
					db.removeUser(member.getKey());
				}
			}
		}else if(addBtn != null) {

			if(username.equals("") || mail.equals("")) {
				session.setAttribute("AdminMessage", 0);
				emptyString = true;
			}

			if(!emptyString) {
				String pw = PasswordHandler.generatePassword();
				String salt = PasswordHandler.generateSalt();
				String hashedPw = PasswordHandler.hashPassword(pw, salt);
				if(!db.addUser(username, hashedPw, mail, salt)) {
					session.setAttribute("AdminMessage", 0);
				} else {
					session.setAttribute("AdminMessage", 1);
					
					if(request.getParameter("mail") != null) {
						MailHandler mh = new MailHandler();
						
						mh.sendPasswordMail(mail, username, pw);
						
						if (db.changePassword(username, hashedPw)) {
							//TODO Handle potential error
						}
					}
				}
			}
		}
	doGet(request, response);
	}
}


