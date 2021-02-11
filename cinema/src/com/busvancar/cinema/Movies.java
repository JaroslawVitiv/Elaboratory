package com.busvancar.cinema;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
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

    ///getServletContext().getRealPath("")+ File.separator + UPLOAD_DIRECTORY + File.separator + fileName
    private void processData(HttpServletRequest request, HttpServletResponse response) {
			
    		response.setContentType("text/html");  
    		MovieSessionDAO movieSessDao = new MovieSessionDAO();
    		List<MovieSession> schedule;
    		StringBuilder moviesSessionHtml = new StringBuilder();
    		HttpSession session = request.getSession();
    		if(session.getAttribute("session_token") == null) {
    			session.setAttribute("session_token", UUID.randomUUID().toString());
    		}

    		String path;
    		
				try {
					schedule = movieSessDao.getSchedule();
				    
					for(MovieSession ms : schedule) {
						
						path = "images" + File.separator +ms.getMoviePoster();
						moviesSessionHtml.append("<div>");
						moviesSessionHtml.append("<img src=\"");
						moviesSessionHtml.append(path);
						moviesSessionHtml.append("\" width=\"120px\" />");
						moviesSessionHtml.append("</div>");
						moviesSessionHtml.append("<div> Title: ");
						moviesSessionHtml.append(ms.getMovieTitle());
						moviesSessionHtml.append("</div>");
						moviesSessionHtml.append("<div> Description in English: ");
						moviesSessionHtml.append(ms.getMovieDescriptionEn());
						moviesSessionHtml.append("</div>");
						moviesSessionHtml.append("<div> Опис українською: ");
						moviesSessionHtml.append(ms.getMovieDescriptionUa());
						moviesSessionHtml.append("</div>");
						moviesSessionHtml.append("<div> Duration: ");
						moviesSessionHtml.append(ms.getMovieDuration());
						moviesSessionHtml.append(" minutes</div>");
						moviesSessionHtml.append("<div> Genre: ");
						moviesSessionHtml.append(ms.getMovieGenre());
						moviesSessionHtml.append("</div>");
						moviesSessionHtml.append("<div> Date & Time: ");
						moviesSessionHtml.append(String.format("%te %1$tB, %1$tY (%1$TH:%1$TM)", ms.getDateTime().toLocalDateTime()));
						moviesSessionHtml.append("</div>");
						moviesSessionHtml.append("<div> Price: ");
						moviesSessionHtml.append(ms.getPrice());
						moviesSessionHtml.append("<div> <a href=\"availability?movie_session="+ms.getSessionId()+"\">Check availability</a></div> ");
						
						moviesSessionHtml.append("<hr/></div>");
					}
				
					moviesSessionHtml.append("</div>");
				request.setAttribute("schedule", moviesSessionHtml.toString());
				
				StringBuilder logingBoard = new StringBuilder();
				if(session.getAttribute("firstName")==null) {
					logingBoard.append("<a href=\"signin.jsp\">Sign in</a> | <a href=\"login.jsp\">Log in</a>");
				}else {
					logingBoard.append("Hi, ");
					logingBoard.append(session.getAttribute("firstName"));
					logingBoard.append("! <a href=\"logout\">Log out</a>");
					logingBoard.append(" | <a href=\"changePassword.jsp\">Change password</a>");

					boolean adminStatus = (boolean) session.getAttribute("admin");
					if(adminStatus) {
						logingBoard.append(" | <a href=\"administration\">Cinema Manager Board</a>");
					}
				}
				
				request.setAttribute("logingBoard", logingBoard.toString());
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
