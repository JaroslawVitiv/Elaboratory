package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemovesessionServlet
 */
public class RemovesessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemovesessionServlet() {
        super();
    }

    private void processData(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String wasSuccessfullyRemoved =  rb.getString("wasSuccessfullyRemoved");
		String stillExistsNotProperlyRemoved =  rb.getString("stillExistsNotProperlyRemoved");
		String continue2admin =  rb.getString("continue2admin");
		String movieSessionRemoval = rb.getString("movieSessionRemoval");
		String sessionTr = rb.getString("session");
		String fromMovie = rb.getString("fromMovie");

		
		
		PrintWriter out = response.getWriter();
		
		MovieSession mSession = new MovieSession();
		MovieSessionDAO msDao = new MovieSessionDAO();
		mSession.setSessionId(Integer.parseInt(request.getParameter("sessionId")));
		mSession.setMovieId(Integer.parseInt(request.getParameter("movieId")));

	
		out.print("<div>"+movieSessionRemoval+"</div><hr/>");
		try {
			out.print("<div>"+sessionTr+" N:"+mSession.getSessionId()+"</div>");
			out.print("<div>"+fromMovie+" N: "+mSession.getMovieId()+"</div>");
			
			if(msDao.removeMovieSession(mSession)) {
				out.print("<div style=\"color:green\">..."+wasSuccessfullyRemoved+"</div>");
			} else {
				out.print("<div style=\"color:red\">..."+stillExistsNotProperlyRemoved+"!</div>");
			}
			out.print("<div><a href=\"edit?m="+mSession.getMovieId()+"\">"+continue2admin+"...</div>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
