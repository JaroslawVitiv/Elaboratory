package com.busvancar.cinema;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
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
		User user = null;
		MovieSessionDAO msDao = new MovieSessionDAO();
		TicketDAO tDao = new TicketDAO();
		MovieSession ms = null;
		tDao.clearAllUnpaid();
		
		HttpSession session = request.getSession();
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
				path = "images" + File.separator +ms.getMoviePoster();
				
				cinemaHall.append("<div>");
				cinemaHall.append("<img src=\"");
				cinemaHall.append(path);
				cinemaHall.append("\" width=\"30%\" />");
				
				cinemaHall.append("<h1><u><b>"+ms.getMovieTitle()+"</b></u></h1>");
				cinemaHall.append("<h2>"+ms.getMovieDescriptionEn()+"</h2>");
				cinemaHall.append("<h2>"+ms.getMovieDescriptionUa()+"</h2>");
				
				cinemaHall.append("<h3>Duration: <b>"+ms.getMovieDuration()+"</b> minutes</h3>");
				cinemaHall.append("<h4>Genre: <b>"+ms.getMovieGenre()+"</b> </h4>");
				cinemaHall.append("<h3>Date&Time: <b>"+String.format("%te.%1$tm.%1$tY (%1$TH:%1$TM)", ms.getDateTime())+"</b> </h4>");
				cinemaHall.append("</div>");
				cinemaHall.append("<hr/>");
				
				
				Ticket[] tickets = tDao.geAllTickets(movieSession);
				cinemaHall.append(getSeats(tickets, msDao.getPrice(msDao.getMovieSessionBasePrice(movieSession), 1)));
			
				ms.getPrice();
				request.setAttribute("cinemaHall", cinemaHall.toString());
				
				int ticketsBookedUnpaid = tDao.getTicketCount(sessionToken);
				
				request.setAttribute("bookedUnpaid", ticketsBookedUnpaid);
				
				List<Ticket> bookedUnpaidList = tDao.getBookedUnpaidTickets(sessionToken);
				
				request.setAttribute("bookedUnpaidList", bookedUnpaidList);
				
			StringBuilder logingBoard = new StringBuilder();
			if(user==null) {
				logingBoard.append(" <a class=\"btn btn-lg btn-outline-info\" href=\"signin.jsp\">Sign in</a> "
										+ " <a class=\"btn btn-lg btn-outline-info\" href=\"login.jsp\">Log in</a> ");
			}else {
				logingBoard.append("Hi, ");
				logingBoard.append(user.getFirstName());
				logingBoard.append("! <a href=\"logout\">Log out</a>");
				logingBoard.append(" | <a href=\"addmovie.jsp\">Add a new Movie</a>");
			}
			
			request.setAttribute("logingBoard", logingBoard.toString());
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
					seatsLine.append(getSeat(num, Double.parseDouble(df.format(basePrice * priceIncrementRate)), "danger ", " disabled ", userId ));
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

	private String getSeat(int seatNumber, double price,  String color, String disabled, int userId) {
		String alert = " bootbox.alert(\'<h2>In order to book the tickets, you need to LOGIN or SIGNUP!</h2>\'); ";
		if(userId>0) {
			alert = " add2cart("+(seatNumber+1)+"); ";
		}
		return " <div><span id=\"seat"+(seatNumber+1)+"\" ><button  onclick=\""+alert+"\" class=\"btn btn-sm btn-"+color+"\"  "+disabled+">"+(seatNumber+1)+" <hr/> Price:<br/>"+price+"</button></span></div> ";
	}
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
