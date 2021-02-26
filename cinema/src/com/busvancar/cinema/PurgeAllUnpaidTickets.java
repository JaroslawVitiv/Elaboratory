package com.busvancar.cinema;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PurgeAllUnpaidTickets
 * @author Vitiv
 * 
 * distroys all marked but unpaid tickets upon 15 minutes timeout
 */
public class PurgeAllUnpaidTickets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PurgeAllUnpaidTickets() {
        super();  
    }

	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionToken =  request.getParameter("session_token");
		TicketDAO tDao = new TicketDAO();
		tDao.purgeAllUnpaidTickets(sessionToken);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
