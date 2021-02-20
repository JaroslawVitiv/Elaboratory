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

/**
 * Servlet implementation class InvoiceServlet
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
		
		TicketDAO tDao = new TicketDAO();
		List<Ticket> bookedUnpaidList = tDao.getBookedUnpaidTickets(sessionToken);

		for(Ticket t:bookedUnpaidList) {
			out.print("<div>"
					+ "  	<div style=\" border: 1px solid grey;  border-radius: 5px;\"> "
					+ "  		<div>Movie: <b>"+t.getMovieTitle()+"</b> |  Price: <b>"+t.getPrice()+"</b> $$</div>"
					+ "  		<div> Date&Times <b>"+String.format("%te.%1$tm.%1$tY (%1$TH:%1$TM)", t.getMovieSessionTime())+"</b> | Duration: <b>"+t.getMovieDuration()+"</b> min.</div>"
					+ "  		<div> Row: <b>"+(((t.getSeat()-((t.getSeat()-1) % SEAT_IN_ROW))/SEAT_IN_ROW)+1)+"</b> | Seat: <b><c:out value=\"${ticket.seat}\"/></b></div>\r\n"
					+ "  		\r\n"
					+ "  		<div style=\"text-align: right; padding:10px;\"><p><button onclick=\"removeTicket("+t.getTicketId()+")\" class=\"btn btn-danger btn-sm\" >x</button></p></div> \r\n"
					+ "  	</div>\r\n"
					+ "	\r\n"
					+ "  </div>");
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
