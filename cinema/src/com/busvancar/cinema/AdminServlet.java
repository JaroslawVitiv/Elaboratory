package com.busvancar.cinema;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

    protected void processData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
    	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		User user  =  (User) session.getAttribute("user");
		
		
		if(user.getAdmin()<1) {
			response.sendRedirect("/cinema");
		}
		
		response.setContentType("text/html");  
		MovieDAO movieDao = new MovieDAO();
		List<Movie> movies;
		StringBuilder moviesHtml = new StringBuilder();
		String path;
		try {
			movies = movieDao.listAllMovies();
		

			
			for(Movie m : movies) {	
				path = "images" + File.separator +m.getPoster();
				moviesHtml.append("<div>");
				moviesHtml.append("<img src=\"");
				moviesHtml.append(path);
				moviesHtml.append("\" width=\"120px\" />");
				moviesHtml.append("</div>");

				moviesHtml.append("<div>");
				moviesHtml.append(m.getTitle());
				moviesHtml.append(m.getDescriptionEn());
				moviesHtml.append(m.getDescriptionUa());
				moviesHtml.append("</div>");

				moviesHtml.append("<div>");
				moviesHtml.append(m.getDuration());
				moviesHtml.append(m.getGenre());
				moviesHtml.append(m.getPrice()/100);
				moviesHtml.append("</div>");

				moviesHtml.append("<div>");
				moviesHtml.append("<a href=\"edit?m=");
				moviesHtml.append(m.getId());
				moviesHtml.append("\">Edit</a> | ");
				moviesHtml.append("<a href=\"remove?m=");
				moviesHtml.append(m.getId());
				moviesHtml.append("\">Remove</a>");
				moviesHtml.append("</div>");

				moviesHtml.append("<hr/></div>");

			}
			
			moviesHtml.append("</div>");
			request.setAttribute("schedule", moviesHtml.toString());
			
			StringBuilder logingBoard = new StringBuilder();
			if(session.getAttribute("user")==null) {
				logingBoard.append("<a href=\"signin.jsp\">Sign in</a> | <a href=\"login.jsp\">Log in</a>");
			}else {
				user = (User) session.getAttribute("user");
				logingBoard.append("Hi, ");
				logingBoard.append(user.getFirstName());
				logingBoard.append("! <a href=\"logout\">Log out</a>");
				logingBoard.append(" | <a href=\"addmovie.jsp\">Add a new Movie</a>");
			}
			
			request.setAttribute("logingBoard", logingBoard.toString());
			request.getRequestDispatcher("administration.jsp").forward(request,response);
			
			
		} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
	    }
		
		
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/administration.jsp");
		rd.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
