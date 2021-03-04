package servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserManagementBean;
import database.DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Servlet implementation class UserMangementServlet
 */
@WebServlet("/UserManagementServlet")
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * This method is called when the webpage using this class is loaded.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();
		UserManagementBean umb = new UserManagementBean();
		HashMap<String, String> memberMap = new HashMap<>();
		ArrayList<String> memberNames = (ArrayList<String>) db.getUsers();
		for (String s : memberNames) {
			if(!s.equals("admin")) {
				memberMap.put(s, db.getRole(s));
			}
		}
		umb.populateBean(memberMap);
		session.setAttribute("UserManagementBean", umb);
		response.sendRedirect("usermanagement.jsp");
	}

	/**
	 * This method is called when submitting the changes made to users.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		db.connect();
		
		HashMap<String, String> memberMap = new HashMap<>();
		ArrayList<String> memberNames = (ArrayList<String>) db.getUsers();
		for (String name : memberNames) {
			if(!name.equals("admin")) {
				db.updateRole(name, request.getParameter(name+"role"));
			}
		}
		doGet(request, response);
	}

}
