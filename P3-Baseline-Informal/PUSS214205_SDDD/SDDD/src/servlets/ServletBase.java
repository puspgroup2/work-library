package servlets;

import javax.servlet.http.HttpServlet;

import database.DataBase;

/**
 * This class is the superclass for all servlets in the application. It includes
 * basic functionality required by many servlets, like for example the connection to the database.
 */
public class ServletBase extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Define states
	public static final int LOGIN_FALSE = 0;
	public static final int LOGIN_TRUE = 1;
	protected static DataBase db = null;

	/**
	 * Default constructor.
	 */
	public ServletBase() {
		db = new DataBase();
		db.connect();
	}

}
