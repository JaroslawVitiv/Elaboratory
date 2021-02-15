
package com.busvancar.cinema;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	response.setContentType("text/html");
    	
    	//invalidate the session if exists
    	HttpSession session = request.getSession(false);
    	if(session != null){
    		session.invalidate();
    	}
    	
    //	ServletContext sc = this.getServletContext();
	//	synchronized(this) {
	//		ArrayList<User> users = (ArrayList<User>) sc.getAttribute("users");
	//		users.remove(0);
	//		sc.setAttribute("users", users);
	//	}
    	response.sendRedirect(request.getContextPath());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  processData(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	  processData(request, response);
  }
}
