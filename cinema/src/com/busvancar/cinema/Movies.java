package com.busvancar.cinema;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Movies
 */
public class Movies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//getServletContext().getRealPath("")+ File.separator + UPLOAD_DIRECTORY + File.separator + fileName
    private void processData(HttpServletRequest request, HttpServletResponse response) {
			User user = null;
    		response.setContentType("text/html");  
    		MovieSessionDAO movieSessDao = new MovieSessionDAO();
    		List<MovieSession> schedule;
    		HttpSession session = request.getSession();
    		if(session.getAttribute("session_token") == null) {
    			session.setAttribute("session_token", UUID.randomUUID().toString());
    		}
    		
    		 if("uk_UA".equals(request.getParameter("language"))) {
    			 session.setAttribute("l10n", "uk_UA");
    		 } else {
    			 session.setAttribute("l10n", "en_GB"); 
    		 }
    		 
    	
    		
    		try {
				schedule = movieSessDao.getSchedule();
				request.setAttribute("schedule", schedule);
				
				if(session.getAttribute("user")!=null) {
					user = (User) session.getAttribute("user");
				}
				
				request.setAttribute("user", user);
				
				request.getRequestDispatcher("index.jsp").forward(request,response);
				
			} catch (SQLException | ServletException | IOException e) {
					e.printStackTrace();
		    }
			
    }

	


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			processData(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
}
