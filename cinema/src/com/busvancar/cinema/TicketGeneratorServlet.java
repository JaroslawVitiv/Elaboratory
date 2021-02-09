package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TicketGeneratorServlet
 */
@WebServlet("/TicketGeneratorServlet")
public class TicketGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int ROWS = 12;
	private final int SEATS = 96;
	private String sessionToken;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketGeneratorServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TicketDAO tDao = new TicketDAO();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		Ticket ticket = new Ticket();
		int movieSession = Integer.parseInt(request.getParameter("movie_session"));
		int seat  = Integer.parseInt(request.getParameter("seat"));
		MovieSessionDAO msDao = new MovieSessionDAO();

		double price =  tDao.getPrice(msDao.getMovieSessionBasePrice(movieSession), seat);
		
		
		HttpSession session = request.getSession();
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
		
		
		Ticket[] tickets = tDao.geAllTickets(movieSession);
		
		out.print(getSeats(tickets, tDao.getPrice(msDao.getMovieSessionBasePrice(movieSession), 1)));
		
	}
	
	private String getSeats( Ticket[] seats, double basePrice) {
		StringBuilder seatsLine = new StringBuilder();
		double priceIncrementRate = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		
		
		seatsLine.append("<div id=\"cinemaHall\" class=\"grid-container\">");
		
		for(int num = 0; num < seats.length; num++) {
			
				
			if(seats[num]!=null) {
				if(seats[num].getPurchaserId() > 0) {
					seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "danger", "disabled"));
				} else {
					if(seats[num].getSessionToken().equals(sessionToken)) {
						seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "warning", ""));
					} else {
						seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "warning", "disabled"));
					}
				}
			} else {
					seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "success", ""));
			}
			
			if(num % ROWS  == 0) {
				priceIncrementRate += 0.049;
			}
		}
		seatsLine.append("</div>");
		return seatsLine.toString();
	}
	
	private String getSeat(int seatNumber, double price,  String color, String disabled) {
		return " <div><span id=\"seat"+(seatNumber+1)+"\" ><button onclick=\"add2cart("+(seatNumber+1)+", "+price+")\" class=\"btn btn-sm btn-"+color+"\"  "+disabled+">"+(seatNumber+1)+" <hr/> Price:<br/>"+price+"</button></span></div> ";
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
