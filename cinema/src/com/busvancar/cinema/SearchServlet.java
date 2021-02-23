package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class SearchServlet
 * @author Vitiv
 * helps to find the movie and its sessions for the next few days
 * by the entering of movie title
 * 
 * Searching is performed by mysql LIKE command
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy (HH:mm)");  
    	
    	
    	Locale locale = new Locale((String) session.getAttribute("l10n"));
    	ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
    	String title = rb.getString("title");
    	String description = rb.getString("description");
    	String duration =  rb.getString("duration");
    	String genre =  rb.getString("genre");
    	String datetime  = rb.getString("datetime");
    	String price =  rb.getString("price");
    	String checkavailability = rb.getString("checkavailability");
    	String mins = rb.getString("mins");
    	String available = rb.getString("available");
    	String availableSeats = rb.getString("availableSeats");
    	String sorryNoResultsFoundTryAnotherOption = rb.getString("sorryNoResultsFoundTryAnotherOption");
    	
    	PrintWriter out = response.getWriter();
    	List<MovieSession> schedule = new ArrayList<>();
    	MovieSessionDAO movieSessDao = new MovieSessionDAO();
    	
    	int sortBy = 0; 
    	if(session.getAttribute("sortBy")!=null) {
    		sortBy = (int) session.getAttribute("sortBy");
    	}
    	
		int ascDesc = 0; 
		if(session.getAttribute("ascDesc") != null) {
			ascDesc = (int) session.getAttribute("ascDesc");
		}
    	
    	
    	if(request.getParameter("request")!=null) {
			try {
				schedule = movieSessDao.getSchedule(sortBy, ascDesc, request.getParameter("request"));
			} catch (SQLException e) {
				logger.warn(e);
			}
		}else {
			try {
				schedule = movieSessDao.getSchedule(sortBy, ascDesc, "");
			} catch (SQLException e) {
				logger.warn(e);
			}
		}
    
    	
    	if(schedule.size()>0) {
    		out.print("<div class=\"grid\">");
	    	for(MovieSession ms : schedule) {
	    		
	    		out.print("   <div class=\"movie\">");
			    out.print("     <div>  <img src=\"images/"+ ms.getMoviePoster() +"\"  width=\"250px\" /> 	</div>");
			    out.print("     <div class=\"description\">");
				out.print("		<h1> "+title+": "+ms.getMovieTitle()+"</h1>");
			    out.print("<div>"+description+": ");
				if("uk_UA".equals(session.getAttribute("l10n"))) {
					out.print(ms.getMovieDescriptionUa());
				} else {
					out.print(ms.getMovieDescriptionEn()); 
				}
			    out.print("</div>");
				out.print("		      <div>"+duration+": <b> "+ms.getMovieDuration()+"</b> "+mins+"</div>");
				 out.print("<div>"+genre+": ");
					if("uk_UA".equals(session.getAttribute("l10n"))) {
						out.print(ms.getMovieGenreUa());
					} else {
						out.print(ms.getMovieGenre());
					}
			    out.print("</div>");
			    out.print("		      <div>"+datetime+": <b>"+formatter.format(ms.getDateTime())+"</b></div>");
			    out.print("		      <div>"+price+": "+ms.getPrice()+" $$ </div>");
			    out.print("		      <div>"+available+": "+ms.getAvailableSeats()+" "+availableSeats+" </div>");
			    out.print("           <hr/>");
			    out.print(" 	      <div> <a class=\"btn btn-outline-danger\" href=\"availability?movie_session="+ ms.getSessionId()+"\"/> "+checkavailability+" </a></div>");
			    out.print("        </div>");  
			    out.print("	    <hr/>");
			    out.print("  </div>");  
	    	}
	       out.print("</div>");  

    	} else {
    		out.print("<div style=\"font-size:50px; font-family: cursive; text-align:center; color:maroon;\">"+sorryNoResultsFoundTryAnotherOption+"...");
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
