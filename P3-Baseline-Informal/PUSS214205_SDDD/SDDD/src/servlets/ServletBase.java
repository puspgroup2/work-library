package servlets;

import javax.servlet.http.HttpServlet;

import database.DataBase;


/**
 *  This class is the superclass for all servlets in the application. 
 *  It includes basic functionality required by many servlets, like for example a page head 
 *  written by all servlets, and the connection to the database. 
 *  
 *  This application requires a database.
 *  For username and password, see the constructor in this class.
 *  
 *  <p>The database can be created with the following SQL command: 
 *  mysql> create database base;
 *  <p>The required table can be created with created with:
 *  mysql> create table users(name varchar(10), password varchar(10), primary key (name));
 *  <p>The administrator can be added with:
 *  mysql> insert into users (name, password) values('admin', 'adminp'); 
 *  
 *  @author Martin Host
 *  @version 1.0
 *  
 */
public class ServletBase extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// Define states
	public static final int LOGIN_FALSE = 0;
	public static final int LOGIN_TRUE = 1;	
	protected DataBase db = null;
	
	
	/**
	 * Default constructor. 
	 */
    public ServletBase() {
    	db = new DataBase();
    	db.connect();
    }


}
