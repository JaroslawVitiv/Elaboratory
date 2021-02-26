package com.busvancar.cinema;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class RemovesessionServlet
 * @author Vitiv
 * removies movie sessions from a movie by Admin
 */
public class RemovesessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemovesessionServlet() {
        super();
    }

    private void processData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String wasSuccessfullyRemoved =  rb.getString("wasSuccessfullyRemoved");
		String stillExistsNotProperlyRemoved =  rb.getString("stillExistsNotProperlyRemoved");
		String continue2admin =  rb.getString("continue2admin");
		String movieSessionRemoval = rb.getString("movieSessionRemoval");
		String sessionTr = rb.getString("session");
		String fromMovie = rb.getString("fromMovie");

		MovieSession mSession = new MovieSession();
		MovieSessionDAO msDao = new MovieSessionDAO();
		mSession.setSessionId(Integer.parseInt(request.getParameter("sessionId")));
		mSession.setMovieId(Integer.parseInt(request.getParameter("movieId")));

		StringBuilder message = new StringBuilder();
		message.append("<div>"+movieSessionRemoval+"</div><hr/>");
		try {
			message.append("<div>"+sessionTr+" N:"+mSession.getSessionId()+"</div>");
			message.append("<div>"+fromMovie+" N: "+mSession.getMovieId()+"</div>");
			
			if(msDao.removeMovieSession(mSession)) {
				message.append("<div style=\"color:green\">..."+wasSuccessfullyRemoved+"</div>");
			} else {
				message.append("<div style=\"color:red\">..."+stillExistsNotProperlyRemoved+"!</div>");
			}
			message.append("<div><a href=\"edit?m="+mSession.getMovieId()+"\">"+continue2admin+"...</div>");
		} catch (SQLException e) {
			logger.warn(e);
		}
		request.setAttribute("message", message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/messenger.jsp");
		rd.include(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
	
}
