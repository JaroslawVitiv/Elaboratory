package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class EditMovieServlet
 * is the main handling part of admin boad for editing movies
 * @author Vitiv
 */
public class EditMovieServlet extends HttpServlet implements CinemaHall {
	private static final long serialVersionUID = 1L;
	
     private Logger logger = null;
     	
     public void init() {
     	logger = Logger.getRootLogger();
     	BasicConfigurator.configure();
     }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMovieServlet() {
        super();
    }
    
    
    protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 Movie movie = new Movie();
         MovieDAO mDao = new MovieDAO();
    	 List<MovieSession> msdList;
    	 MovieSessionDAO msDao = new MovieSessionDAO();
         DecimalFormat df = new DecimalFormat("###.##");
       
    	 response.setContentType("text/html");
    	 
    	 PrintWriter out = response.getWriter();
    	 HttpSession session = request.getSession();
    	 User user = (User) session.getAttribute("user");
 		int admin = (int)user.getAdmin();
 		if(admin  < 1) {
 			response.sendRedirect("/cinema");
 		}
 		
 		int movieId = Integer.parseInt(request.getParameter("m"));
 		
 		movie.setId(movieId);
 		int views = msDao.getViews(movie);
 		double income = msDao.getIncome(movie);
 		double cinemaHallOccupationRate = 0;
		try {
			cinemaHallOccupationRate = ((double) views / (msDao.getAllMovieSessions(movie).size() * SEATS)) * 100;
		} catch (SQLException e) {
			logger.warn(e);
		} 
		
 		request.setAttribute("views", views);
 		request.setAttribute("income", income);
 		request.setAttribute("cinemaHallOccupationRate", df.format(cinemaHallOccupationRate));
 		
 		try {
			mDao = new MovieDAO();
			movie = mDao.getMovie(movie.getId());
				
			if(movie!=null) {
					
				msDao = new MovieSessionDAO();
				msdList = msDao.getAllMovieSessions(movie);
				
				request.setAttribute("listOfAllSessions", msdList);
				request.setAttribute("movie", movie);
					
				request.getRequestDispatcher("editmovie.jsp").forward(request,response);
			} else {
				out.print("Error: Movie is not found");
			}
				
			
		} catch (SQLException ex) {
			logger.warn(ex);
		}
			
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
