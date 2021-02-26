package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class TicketGeneratorServlet
 * @author Vitiv
 * generates cinema hall model with the numbered seats
 * colored according to different status:
 * red - for booked and paid
 * yellow - for marked but unpaid and pending 15 minutes before dropping if not paid
 * green - for available seats 
 */
public class TicketGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int SEATS_IN_ROW = 12;
	private final int SEATS = 96;
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
    public TicketGeneratorServlet() {
        super();
    }

	/**
	 * @throws SQLException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		rb.getString("price");
		
		TicketDAO tDao = new TicketDAO();
		int availableSeats = 0;
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		Ticket ticket = new Ticket();
		int movieSession = Integer.parseInt(request.getParameter("movie_session"));
		int seat  = Integer.parseInt(request.getParameter("seat"));
		
		if(msDao==null) {
			msDao = new MovieSessionDAO();
		}
		MovieSession ms;
		
		double price = 0;
		int coins = 0;
		synchronized(this){
			try {
				ms = msDao.getMovieSession(movieSession);
				coins = (int)(ms.getPrice()*100);
				price =  msDao.getPrice(coins, seat);
			} catch (SQLException e) {
				logger.warn(e);
			}
		}
		
		if(session.getAttribute("session_token") == null) {
			session.setAttribute("session_token", UUID.randomUUID().toString());
		}
		sessionToken = (String) session.getAttribute("session_token");
		
		ticket.setSeat(seat);
		ticket.setSessionId(movieSession);
		ticket.setPrice(price);
		ticket.setSessionToken(sessionToken);
		ticket.setTime(time);
		ticket.setPurchaserId(0);
		
		PrintWriter out = response.getWriter();
				
		if(tDao.isCreated(ticket)) {
			tDao.removeTicket(ticket);
		} else {
			tDao.createTicket(ticket);	
		}
		
		availableSeats = SEATS - tDao.getBookedSeats(movieSession);
		tDao.updateMovieSessionAvailableSeats(movieSession, availableSeats);
		Ticket[] tickets = tDao.getAllTickets(movieSession);
		out.print(getSeats(tickets, msDao.getPrice(coins, 1)));
		
		
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
		return " <div>"
				+ "<span id=\"seat"+(seatNumber+1)+"\" >"
						+ "<button onclick=\"add2cart("+(seatNumber+1)+");\" class=\"btn btn-sm btn-"+color+"\"  "+disabled+" />"+(seatNumber+1)+" <hr/> "+price+" $$</button></span></div> ";
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
}
