package com.busvancar.cinema;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * @author User
 *helps to count number of users visiting application online
 *  through HttpSessions  
 */

@WebListener
public class UsersOnlineListener implements HttpSessionListener{

    /**
     * Default constructor. 
     */
    public UsersOnlineListener() {
        // TODO Auto-generated constructor stub
    }

    ServletContext ctx=null;  
    static int current=0;  
      
    public void sessionCreated(HttpSessionEvent e) {  
	    current++;  
	      
	    ctx=e.getSession().getServletContext();  
	    ctx.setAttribute("currentUsers", current);  
    }  
  
    public void sessionDestroyed(HttpSessionEvent e) {  
        current--;  
        ctx.setAttribute("currentUsers", current);  
    }  
}
