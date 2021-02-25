package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class InvoiceServlet
 * 
 * creates a list of unpaid ticket sent to users interface through AJAX
 * @author Vitiv
 */
public class InvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int SEAT_IN_ROW = 12;
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String sessionToken =  request.getParameter("session_token");
		HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String youHaveChosenNoTickets =  rb.getString("youHaveChosenNoTickets");
		String movieTitle = rb.getString("movieTitle");
		String datetime = rb.getString("datetime");
		String duration = rb.getString("duration");
		String mins = rb.getString("mins");
		String row = rb.getString("row");
		String seat = rb.getString("seat");
		String priceTr = rb.getString("price");



		TicketDAO tDao = new TicketDAO();
		List<Ticket> bookedUnpaidList = tDao.getBookedUnpaidTickets(sessionToken);

		for(Ticket t : bookedUnpaidList) {
			out.print("<div> "
					+ "  	<div style=\" border: 1px solid grey;  border-radius: 5px;\"> "
					+ "  		<div>"+movieTitle+": <b>"+t.getMovieTitle()+"</b> |  "+priceTr+": <b>"+t.getPrice()+"</b> $$</div>"
					+ "  		<div> "+datetime+" <b>"+String.format("%te.%1$tm.%1$tY (%1$TH:%1$TM)", t.getMovieSessionTime())+"</b> | "+duration+": <b>"+t.getMovieDuration()+"</b> "+mins+"</div>"
					+ "  		<div> "+row+": <b>"+(((t.getSeat()-((t.getSeat()-1) % SEAT_IN_ROW))/SEAT_IN_ROW)+1)+"</b> | "+seat+": <b><c:out value=\"${ticket.seat}\"/></b></div>"
					+ "  		<div style=\"text-align: right; padding:10px;\"><p><button onclick=\"removeTicket("+t.getTicketId()+")\" class=\"btn btn-danger btn-sm\" >x</button></p></div>"
					+ "  	</div>"
					+ "</div>");
		}
		
		if(bookedUnpaidList.size()<1) {
			out.print(youHaveChosenNoTickets);
		}
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
