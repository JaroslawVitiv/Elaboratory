package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditMovieServlet
 */
public class EditMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMovieServlet() {
        super();
    }
    
    
    protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 Movie movie = null;
    	 MovieSessionDAO msDao = null;
    	 MovieDAO mDao;
    	 List<MovieSession> msdList;
    	 String timeStamp;
    	 
			
    	 response.setContentType("text/html");
    	 
    	 PrintWriter out = response.getWriter();
    	 StringBuilder sessionList = new StringBuilder(); 
    	 HttpSession session = request.getSession();
    	 boolean admin = (boolean) session.getAttribute("admin");
 		
 		if(!admin) {
 			response.sendRedirect("/cinema");
 		}
         
         int movieNumber = Integer.parseInt(request.getParameter("m"));
     
         
        
        
 
			try {
				mDao = new MovieDAO();
				movie = mDao.getMovie(movieNumber);
				out.print(movieNumber);
				
				if(movie!=null) {
					
					msDao = new MovieSessionDAO();
					msdList = msDao.getAllMovieSessions(movie);
				
					

					
					for(MovieSession ms : msdList) {
						sessionList.append("<div> Date: ");
						sessionList.append(String.format("%te %1$tB, %1$tY (%1$TH:%1$TM)", ms.getDateTime()));
						sessionList.append("</div>");
						sessionList.append("<div> Price: ");
						sessionList.append(ms.getPrice());
						sessionList.append(" uah");
						sessionList.append("<form method=\"post\" action=\"removesession\">"
								+ "<input type=\"hidden\" value=\""+ms.getSessionId()+"\" name=\"sessionId\"  />"
								+ "<input type=\"hidden\" value=\""+ms.getMovieId()+"\" name=\"movieId\"  />"
								+ "<input value=\"Remove\" type=\"submit\" />"
								+ "</form>");

						sessionList.append("<hr/>");
						sessionList.append("</div>");
					}
					
					request.setAttribute("listOfAllSessions", sessionList.toString());
					
					request.setAttribute("movie_id", movieNumber);
					request.setAttribute("poster", movie.getPoster());
					request.setAttribute("title", movie.getTitle());
					request.setAttribute("descriptionEn",movie.getDescriptionEn());
					request.setAttribute("descriptionUa",movie.getDescriptionUa());
					request.setAttribute("genre" ,movie.getGenre());
					request.setAttribute("duration", movie.getDuration());
					request.setAttribute("price", (movie.getPrice()/100));
					
				
					request.getRequestDispatcher("editmovie.jsp").forward(request,response);
				} else {
					out.print("Error: Movie is not found");
				}
				
			
			} catch (SQLException ex) {
				ex.printStackTrace();
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
