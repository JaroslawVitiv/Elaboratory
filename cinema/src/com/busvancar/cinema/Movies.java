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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


/**
 * Servlet implementation class Movies
 * is the main handler linked to welcoming page
 * @author Vitiv
 *  
 */
public class Movies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}
	
	private void processData(HttpServletRequest request, HttpServletResponse response) {
    		int page = 1;
    		HttpSession session = request.getSession();
    		if(session.getAttribute("l10n")==null) {
    			session.setAttribute("l10n", "uk_UA");
    		}
    		Locale locale = new Locale((String) session.getAttribute("l10n"));
   		    ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
   		    String sorryNoResultsFoundTryAnotherOption = rb.getString("sorryNoResultsFoundTryAnotherOption");
   		    
			User user = null;
			List<String> genres = new ArrayList<String>();
			response.setContentType("text/html");  
    		MovieSessionDAO movieSessDao = new MovieSessionDAO();
    		List<MovieSession> schedule;
    		
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
    		
    		 if(session.getAttribute("ascDesc") == null || "1".equals(request.getParameter("ascDesc"))) {
     			session.setAttribute("ascDesc", 1);
     		 }
     		 
     		 if("0".equals(request.getParameter("ascDesc"))) {
     			 session.setAttribute("ascDesc", 0); 
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
				
				request.setAttribute("pagination", getPagination(page, request));
				
				
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
					request.setAttribute("message", sorryNoResultsFoundTryAnotherOption+"...");
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
				logger.warn(e);
		    }
			
    }


	private String getPagination(int page, HttpServletRequest request) {
		int p;
		if(page<2) {
			p=1;
		} else if(page>9) {
			p=10;
		} else {
			p=page;
		}
		
		
		if(p>1) {	
		    request.setAttribute("pWeek", " href=\"/cinema/?page="+(p-1)+"\" ");
		} else {
			request.setAttribute("pWeek", " disabled ");
		}
		
		
		for(int week = 1; week < 11; week++) {
			if(p!=week) {
				request.setAttribute("page"+week, "page-link");
			} else {
				request.setAttribute("page"+week, "btn btn-danger");
			}
		}
		
		if(p<10) {
		    request.setAttribute("nWeek", " href=\"/cinema/?page="+(p+1)+"\" ");
		} else {
			request.setAttribute("nWeek", " disabled ");
		}
		return "";
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			processData(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
}
