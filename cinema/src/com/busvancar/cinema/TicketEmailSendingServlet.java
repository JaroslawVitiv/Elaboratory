package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TicketEmailSendingServlet
 */
public class TicketEmailSendingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketEmailSendingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String isSuccessfullySentCheckYourInboxInludingSPAMbox =  rb.getString("isSuccessfullySentCheckYourInboxInludingSPAMbox");
		String ticketTr =  rb.getString("ticketTr");

		PrintWriter out = response.getWriter();
		Ticket ticket = new Ticket();
		ticket.setTicketId(Integer.parseInt(request.getParameter("ticketId")));
		out.print(ticketTr+" "+request.getParameter("ticketId")+" "+isSuccessfullySentCheckYourInboxInludingSPAMbox);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
