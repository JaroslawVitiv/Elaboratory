package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class CinemaHallServlet
 * shows a model of cinema hall for a particular movie of particular movie session
 * for better representation of free, booked and pre-paid seats.
 * 
 * The servlet gets updated information from CheckAvailabilityServlet through AJAX
 * @author Vitiv
 */
public class CinemaHallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int SEATS_IN_ROW = 12;
	private String sessionToken;
	private MovieSessionDAO msDao;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CinemaHallServlet() {
        super();
    }

	/**
	 * @throws SQLException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TicketDAO tDao = new TicketDAO();
		
		int movieSession = Integer.parseInt(request.getParameter("movie_session"));
		if(msDao==null) {
		   msDao = new MovieSessionDAO();
		}
	
		int coins = 0;
		try {
			 MovieSession ms = msDao.getMovieSession(movieSession);
			coins = (int)(ms.getPrice()*100);
		} catch (SQLException e) {
			logger.warn(e);
			
		}
		
		HttpSession session = request.getSession();
		if(session.getAttribute("session_token") == null) {
			session.setAttribute("session_token", UUID.randomUUID().toString());
		}
		sessionToken = (String) session.getAttribute("session_token");
		
		PrintWriter out = response.getWriter();
				
		Ticket[] tickets = tDao.getAllTickets(movieSession);
		
		double basicPrice = msDao.getPrice(coins, 1);
		
		out.print(getSeats(tickets, basicPrice));
		
		
	}
	
	private String getSeats(Ticket[] seats, double basePrice) {
		StringBuilder seatsLine = new StringBuilder();
		double priceIncrementRate = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		
		
		seatsLine.append("<div id=\"cinemaHall\" class=\"grid-container\">");
		
		for(int num = 0; num < seats.length; num++) {
			
				
			if(seats[num]!=null) {
				if(seats[num].getPurchaserId() > 0) {
					seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "danger ", " disabled "));
				} else {
					if(seats[num].getSessionToken().equals(sessionToken)) {
						seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "warning ", " "));
					} else {
						seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "warning ", " disabled "));
					}
				}
			} else {
					seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "success", ""));
			}
			
			if((num+1) % SEATS_IN_ROW  == 0) {
				priceIncrementRate += 0.049;
			}
		}
		seatsLine.append("</div>");
		return seatsLine.toString();
	}
	
	private String getSeat(int seatNumber, double price,  String color, String disabled) {
		return " <div><span id=\"seat"+(seatNumber+1)+"\" ><button onclick=\"  add2cart("+(seatNumber+1)+"); \" class=\"btn btn-sm btn-"+color+"\"  "+disabled+" />"+(seatNumber+1)+" <hr/> "+price+" $$</button></span></div> ";
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			processData(request, response);
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			processData(request, response);
	
	}
}
