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
    	HttpSession session = request.getSession();
		
		User user  =  (User) session.getAttribute("user");
		
		response.setContentType("text/html");  
		MovieDAO movieDao = new MovieDAO();
		List<Movie> movies;
		try {
			movies = movieDao.listAllMovies();
			request.setAttribute("schedule", movies);
			
			if(session.getAttribute("user")!=null) {
				if(user.getAdmin()<1) {
					response.sendRedirect("/cinema");
				}
				
			}
			
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
