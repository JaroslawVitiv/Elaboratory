package com.busvancar.cinema;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class UsersOnlineListener
 *
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

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    //public void contextInitialized(ServletContextEvent event)  { 
    	//ServletContext sc = event.getServletContext();
    	//ArrayList<User> users = (ArrayList<User>) sc.getAttribute("users");
    	//if(users==null) {
    		//System.out.print("No users yet");
    		//users = new ArrayList<User>();
    		//sc.setAttribute("users", users);
    //	}
    //}
	
}
