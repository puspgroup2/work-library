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
		UserManagementBean umb = new UserManagementBean();
		Map<String, String> memberMap = new HashMap<String, String>();
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
		
		HashMap<String, String> mapM = new HashMap<String, String>();
		ArrayList<String> memberNames = (ArrayList<String>) db.getMembers();
		PasswordHandler pw = new PasswordHandler();
		
		for (String s : memberNames) {
			if(!s.equals("admin")) {
				mapM.put(s, db.getEmail(s));
			}
		}
		boolean emptyString = false; 

		if(removeBtn != null) { //
			for(Map.Entry<String, String> member : mapM.entrySet()) {
				if(member.getKey().equals(request.getParameter(member.getKey())) && !member.getKey().equals(request.getParameter("admin"))) {
					db.removeUser(member.getKey());
				}
			}
		} else if(addBtn != null) {

			if(request.getParameter("username").equals("") || request.getParameter("mail").equals("")) {
				session.setAttribute("AdminMessage", 0);
				emptyString = true;
			}

			if(!emptyString) {
				if(!db.addUser(request.getParameter("username"), pw.generatePassword(), request.getParameter("mail"), pw.generateSalt())) {
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


