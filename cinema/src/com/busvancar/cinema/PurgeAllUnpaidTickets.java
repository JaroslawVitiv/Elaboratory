package com.busvancar.cinema;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PurgeAllUnpaidTickets
 */
public class PurgeAllUnpaidTickets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurgeAllUnpaidTickets() {
        super();  
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionToken =  request.getParameter("session_token");
		
		TicketDAO tDao = new TicketDAO();
		tDao.purgeAllUnpaidTickets(sessionToken);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
