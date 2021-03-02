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
		session.setAttribute("AdministrationBean", umb);
		response.sendRedirect("administration.jsp");
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String removeBtn = request.getParameter("Remove");
		String addBtn = request.getParameter("Add");


		DataBase db = new DataBase();
		db.connect();
		HttpSession session = request.getSession();

		boolean emptyString = false; 

		if(removeBtn != null) { //
			for(Map.Entry<String, String> member : memberMap.entrySet()) {
				if(member.getKey().equals(request.getParameter(member.getKey()))) {
					db.removeUser(member.getKey());
				}
			}
		} else if(addBtn != null) {

			if(request.getParameter("username").equals("") || request.getParameter("mail").equals("")) {
				session.setAttribute("AdminMessage", 0);
				emptyString = true;
			}

			if(!emptyString) {
				if(!db.addUser(request.getParameter("username"), "password", request.getParameter("mail"), "hej")) {
					session.setAttribute("AdminMessage", 0);
				} else {
					session.setAttribute("AdminMessage", 1);
				}
			}
		}

		/*switch(request.getParameter("action")) {
			case "Add": db.addUser(request.getParameter("name"), PasswordHandler.generatePassword(), 
						request.getParameter("Email"), PasswordHandler.generateSalt());
						break;

			case "Remove": db.removeUser(request.getParameter("name"));
						   break;
			default: break; 
		}*/
		doGet(request, response);

	}
}


