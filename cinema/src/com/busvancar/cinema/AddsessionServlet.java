package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class AddsessionServlet
 */
public class AddsessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddsessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	private void processData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String movieSessionSuccessfullyInserted =  rb.getString("movieSessionSuccessfullyInserted");
		String movieSessionNotInserted = rb.getString("movieSessionNotInserted");
		String priceTr = rb.getString("price");
		String datetime = rb.getString("datetime");
		String continue2admin = rb.getString("continue2admin");

		MovieSession mSession = new MovieSession();
		MovieSessionDAO msDao = new MovieSessionDAO();
		mSession.setMovieId(Integer.parseInt(request.getParameter("movieId")));
		mSession.setPrice(Double.parseDouble(replaceCommas(request.getParameter("sessionPrice"))));
		
		String dateTime = request.getParameter("sessionDate")+" "+request.getParameter("sessionTime");
		StringBuilder message = new StringBuilder();
		
		Timestamp timestamp = null;

		try{
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    Date parsedDate = sdf.parse(dateTime);
		    timestamp = new Timestamp(parsedDate.getTime());
		    mSession.setDateTime(timestamp);
		} catch (ParseException e) {
			logger.warn(e);
		} 
		try {
			if(msDao.insertMovieSession2db(mSession)) {
				message.append("<div style=\"color:green\">"+movieSessionSuccessfullyInserted+"</div>");
				message.append("<div>"+datetime+": "+String.format("%te.%1$tm.%1$tY (%1$TH:%1$TM)", mSession.getDateTime().toLocalDateTime())+"</div>");
				message.append("<div>"+priceTr+": "+mSession.getPrice()+" ($$)</div>");
				
			} else {
				message.append("<div style=\"color:red\">"+movieSessionNotInserted+"</div>");
			}
			message.append("<div><a href=\"edit?m="+mSession.getMovieId()+"\">"+continue2admin+"</div>");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String replaceCommas(String number) {
		return number.replace(',','.');
	}

}
