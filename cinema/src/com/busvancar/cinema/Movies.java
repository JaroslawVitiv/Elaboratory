package com.busvancar.cinema;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletContext;
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
    		int page = 1;
    		
    			
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
    		 
    		 LocalDate chosendate = LocalDate.now();
    		 if(request.getParameter("date") != null) {
    			 chosendate = LocalDate.parse(request.getParameter("date"));
    		}
    		 request.setAttribute("chosendate", chosendate);
    	    
    		 
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
     			session.setAttribute("ascDesc", 1);
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
    			
				schedule = movieSessDao.getSchedule(sortBy, ascDesc, genreCategoryIndex, chosendate);
			
				if(request.getParameter("page") != null && Integer.parseInt(request.getParameter("page")) > 0) {
	    			page = Integer.parseInt(request.getParameter("page"));
	    			schedule =  movieSessDao.getSchedule(page);
	    		}
				request.setAttribute("pagination", getPagination(page));
				
				
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

	


	private String getPagination(int page) {
		int p;
		if(page<2) {
			p=1;
		} else if(page>9) {
			p=10;
		} else {
			p=page;
		}
		
		
		StringBuilder res = new StringBuilder("	<nav aria-label=\"Page navigation example\" id=\"pagination\"> "
				+ "  <ul class=\"pagination\">");
		
		if(p>0) {	
			res.append("<li class=\"page-item\"><a class=\"page-link\" href=\"/cinema/?page="+(p-1)+"\">Previous</a></li>");
		} else {
			res.append("<li class=\"page-item\"><a class=\"page-link\" disabled> Previous </a></li>");
		}
		
		for(int week = 1; week < 11; week++) {
			if(p!=week) {
				res.append("<li class=\"page-item\"><a class=\"page-link\" href=\"/cinema/?page="+week+"\">"+week+"</a></li>");
			} else {
				res.append("<li class=\"page-item\"><a class=\"page-link\" disabled>"+week+"</a></li>");
			}
		}
		
		if(p<10) {
			res.append(" <li class=\"page-item\"><a class=\"page-link\" href=\"/cinema/?page="+(p+1)+"\" >Next</a></li>");
		} else {
			res.append(" <li class=\"page-item\"><a class=\"page-link\" disabled >Next</a></li>");
		}
		res.append(" </ul></nav>");
	
		return res.toString();
	}




	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			processData(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
}
