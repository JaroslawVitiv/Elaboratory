package com.busvancar.cinema;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.util.Arrays;


/**
 * Servlet implementation class Movies
 */
public class Movies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//getServletContext().getRealPath("")+ File.separator + UPLOAD_DIRECTORY + File.separator + fileName
    private void processData(HttpServletRequest request, HttpServletResponse response) {
			User user = null;
			List<String> genres = new ArrayList<String>();
			response.setContentType("text/html");  
    		MovieSessionDAO movieSessDao = new MovieSessionDAO();
    		List<MovieSession> schedule;
    		HttpSession session = request.getSession();
    		if(session.getAttribute("session_token") == null) {
    			session.setAttribute("session_token", UUID.randomUUID().toString());
    		}
    		
    		if(session.getAttribute("l10n") == null || "uk_UA".equals(request.getParameter("language"))) {
    			session.setAttribute("l10n",  "uk_UA");
    			genres = Genre.getGenreList(Genre.genres_uk_UA);
    		}
    		 
    		 if("en_GB".equals(request.getParameter("language"))) {
    			 session.setAttribute("l10n", "en_GB"); 
    			 genres = Genre.getGenreList(Genre.genres_en_GB);
    		 }
    		 
    		 LocalDate currentdate = LocalDate.now();
    		 request.setAttribute("currentdate", currentdate);
    	    
    		 
    		 if(session.getAttribute("sortBy") == null || "0".equals(request.getParameter("sortBy"))) {
     			session.setAttribute("sortBy",  0);
     		 } else if("1".equals(request.getParameter("sortBy"))){
     			session.setAttribute("sortBy", 1);
     		 } else if("2".equals(request.getParameter("sortBy"))){
     			session.setAttribute("sortBy",  2); 
     		 }
    		 
    		 
    		 if(session.getAttribute("genre") == null) {
      			session.setAttribute("genre",  0);
      		 }
    		
    		 if(session.getAttribute("ascDesc") == null || "0".equals(request.getParameter("ascDesc"))) {
     			session.setAttribute("ascDesc", 0);
     		 }
     		 
     		 if("1".equals(request.getParameter("ascDesc"))) {
     			 session.setAttribute("ascDesc", 1); 
     		 }
     		 
     		 
    		 
    		 
    		try {
    			
    			int sortBy = (int) session.getAttribute("sortBy");
    			int ascDesc =  (int) session.getAttribute("ascDesc");
    			if(request.getParameter("genre") != null) {
    				session.setAttribute("genre",  Integer.parseInt(request.getParameter("genre")));
    			}
    			int genreCategoryIndex = 0;
    			if(session.getAttribute("genre")!=null) {
    				genreCategoryIndex = (int) session.getAttribute("genre");
    			}
    			
				schedule = movieSessDao.getSchedule(sortBy, ascDesc, genreCategoryIndex);
				
				
				if( genreCategoryIndex > -1 && genreCategoryIndex < Genre.genres_en_GB.length) {
					if("en_GB".equals(session.getAttribute("l10n"))) {
						session.setAttribute("genreCategory", Genre.genres_en_GB[genreCategoryIndex]);
					} else {
						session.setAttribute("genreCategory", Genre.genres_uk_UA[genreCategoryIndex]);
					}
				} else {
					if("en_GB".equals(session.getAttribute("l10n"))) {
						session.setAttribute("genreCategory", Genre.genres_en_GB[0]);
					} else {
						session.setAttribute("genreCategory", Genre.genres_uk_UA[0]);
					}
				}
				
				if(schedule.size()>0) {
					request.setAttribute("schedule", schedule);
				} else {
					request.setAttribute("message", "Sorry, but no results found. Try another option...");
				}
				request.setAttribute("genres", genres);
				
				if(session.getAttribute("user")!=null) {
					user = (User) session.getAttribute("user");
				}
				request.setAttribute("user", user);
				
				if(user!=null &&  user.getAdmin()>0) {
					response.sendRedirect(request.getContextPath() + "/administration");
				} else {	
					request.getRequestDispatcher("index.jsp").forward(request,response);
				}
				
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
