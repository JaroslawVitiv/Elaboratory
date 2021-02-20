package com.busvancar.cinema;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckAvailabilityServlet
 */
public class CheckAvailabilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int ROWS = 12;
	private String sessionToken;
	private int userId = 0;
	public String message = "";
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckAvailabilityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String duration =  rb.getString("duration");
		String genre =  rb.getString("genre");
		String datetime  = rb.getString("datetime");
		String mins = rb.getString("mins");
		message = rb.getString("inOrder2bookTicketsLOGINorSIGNUP");
		User user = null;
		MovieSessionDAO msDao = new MovieSessionDAO();
		TicketDAO tDao = new TicketDAO();
		MovieSession ms = null;
		tDao.clearAllUnpaid();
		
		if(session.getAttribute("session_token") == null) {
			session.setAttribute("session_token", UUID.randomUUID().toString());
		}
		sessionToken = (String) session.getAttribute("session_token");
		
		int movieSession = Integer.parseInt(request.getParameter("movie_session"));
		response.setContentType("text/html");  
		
		if(session.getAttribute("user")!=null) {
			user = (User) session.getAttribute("user");
			userId = user.getId();
		}
		
		
		
		StringBuilder cinemaHall = new StringBuilder();
		String path;
		try {
				ms = msDao.getMovieSession(movieSession);
				if(ms.getMovieTitle()==null)  {
					response.sendRedirect("/cinema");
				}
				
				path = "images" + File.separator +ms.getMoviePoster();
				
				cinemaHall.append("<div>");
				cinemaHall.append("<img src=\"");
				cinemaHall.append(path);
				cinemaHall.append("\" width=\"30%\" />");
				
				cinemaHall.append("<h1><u><b>"+ms.getMovieTitle()+"</b></u></h1>");
				cinemaHall.append("<h2>");
				if(session.getAttribute("l10n") == "uk_UA") {
					cinemaHall.append(ms.getMovieDescriptionUa());
				} else {
					cinemaHall.append(ms.getMovieDescriptionEn());
				}
				cinemaHall.append("</h2>");
				cinemaHall.append("<h3>"+duration+": <b>"+ms.getMovieDuration()+"</b> "+mins+"</h3>");
				cinemaHall.append("<h4>"+genre+": <b>");
				if(session.getAttribute("l10n") == "uk_UA") {
					cinemaHall.append(ms.getMovieGenreUa());
				} else {
					cinemaHall.append(ms.getMovieGenre());
				}
				cinemaHall.append("</b> </h4>");
				cinemaHall.append("<h3>"+datetime+": <b>"+String.format("%te.%1$tm.%1$tY (%1$TH:%1$TM)", ms.getDateTime())+"</b> </h4>");
				cinemaHall.append("</div>");
				cinemaHall.append("<hr/>");
				
				Ticket[] tickets = tDao.getAllTickets(movieSession);
				cinemaHall.append(getSeats(tickets, msDao.getPrice(msDao.getMovieSessionBasePrice(movieSession), 1)));
			
				ms.getPrice();
				request.setAttribute("cinemaHall", cinemaHall.toString());
				
				int ticketsBookedUnpaid = tDao.getTicketCount(sessionToken);
				
				request.setAttribute("bookedUnpaid", ticketsBookedUnpaid);
				
				List<Ticket> bookedUnpaidList = tDao.getBookedUnpaidTickets(sessionToken);
				
				request.setAttribute("bookedUnpaidList", bookedUnpaidList);
		
			request.setAttribute("autos", getAutoGrid(ROWS));
			request.getRequestDispatcher("availability.jsp").forward(request,response);
			
		} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
	    }
		
		
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/availability.jsp");
		rd.include(request, response);
	}
	

	private String getAutoGrid(int rows) {
		StringBuilder autoGrid = new  StringBuilder();
		int row=0;
		while(rows>row++) {
			autoGrid.append(" auto ");
		}
		return autoGrid.toString();
	}

	

	private String getSeats( Ticket[] seats, double basePrice) {
		StringBuilder seatsLine = new StringBuilder();
		double priceIncrementRate = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		
		
		seatsLine.append("<div  id=\"cinemaHall\"> <div  class=\"grid-container\">  ");
		
		for(int num = 0; num < seats.length; num++) {
			
				
			if(seats[num]!=null) {
				if(seats[num].getPurchaserId() > 0) {
					seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "danger ", " disabled ", userId));
				} else {
					if(seats[num].getSessionToken().equals(sessionToken)) {
						seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "warning ", " ", userId));
					} else {
						seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "warning ", " disabled ", userId));
					}
				}
			} else {
					seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "success ", " ", userId));
			}
			
			if((num+1) % ROWS  == 0) {
				priceIncrementRate += 0.049;
			}
		}
	
		seatsLine.append("</div></div>");
		return seatsLine.toString();
	}
	
	private String getSeat(int seatNumber, double floatPrice,  String color, String disabled, int userId) {
		String alert = " bootbox.alert(\'<h2>"+message+"</h2>\'); ";
		if(userId>0) {
			alert = " add2cart("+(seatNumber+1)+"); ";
		}
		return " <div><span id=\"seat"+(seatNumber+1)+"\" ><button  onclick=\""+alert+"\" class=\"btn btn-sm btn-"+color+"\"  "+disabled+">"+(seatNumber+1)+" <hr/>"+floatPrice+" $$</button></span></div> ";
	}
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
