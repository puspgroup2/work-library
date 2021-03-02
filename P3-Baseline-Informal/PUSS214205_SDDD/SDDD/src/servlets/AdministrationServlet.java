package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserManagementBean;
import database.DataBase;
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
		umb = new UserManagementBean();
		memberMap = new HashMap<>();
		ArrayList<String> memberNames = (ArrayList<String>) db.getMembers();
		for (String s : memberNames) {
			if(!s.equals("admin")) {
				memberMap.put(s, db.getEmail(s));
			}
		}
		umb.populateBean(memberMap);
		session.setAttribute("UserManagementBean", umb);
		response.sendRedirect("administration.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataBase db = new DataBase();
		db.connect();
		
		switch(request.getParameter("action")) {
			case "Add": db.addUser(request.getParameter("name"), PasswordHandler.generatePassword(), 
						request.getParameter("Email"), PasswordHandler.generateSalt());
						break;
			
			case "Remove": db.removeUser(request.getParameter("name"));
						   break;
			default: break;
		}
		doGet(request, response);
		
	}
}


