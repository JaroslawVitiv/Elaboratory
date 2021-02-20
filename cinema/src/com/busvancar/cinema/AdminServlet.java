package com.busvancar.cinema;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int SEATS = 96;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

    protected void processData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
    	HttpSession session = request.getSession();
		TicketDAO tDao = new TicketDAO();
		User user  =  (User) session.getAttribute("user");
		
		response.setContentType("text/html");  
		MovieDAO movieDao = new MovieDAO();
		MovieSessionDAO msDao = new MovieSessionDAO();
		UserDAO userDao = new UserDAO();
		List<Movie> movies;
		try {
			movies = movieDao.listAllMovies();
			request.setAttribute("schedule", movies);
			
			if(session.getAttribute("user")!=null) {
				if(user.getAdmin()<1) {
					response.sendRedirect("/cinema");
				}
				
			}
			
			LocalDate start = LocalDate.now();
			LocalDate end = LocalDate.now();

			if(request.getParameter("start")!=null) {
				start = LocalDate.parse(request.getParameter("start"));
			}
			if(request.getParameter("end")!=null) {
				end = LocalDate.parse(request.getParameter("end"));
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			if(start.isAfter(end)){
				LocalDate temp = start;
				start = end;
				end = temp;
			} 
			
			request.setAttribute("start", start);
			request.setAttribute("end", end);
			String period = start.format(formatter)+" - "+end.format(formatter);
			
			double incomes  = tDao.getIncomes(start, end);
			int ticketsBought = tDao.getTicketsBought(start, end);
			int movieSessions = msDao.getMovieSessions(start, end);
			double visitingRate = 0;
			double aveTicketPrice = tDao.getAveTicketPrice(start, end);
			List<User> top5keyCustomers  = userDao.getTopKeyCustomers(5, start, end);

			if(movieSessions > 0) {
				visitingRate = (ticketsBought*100) / (movieSessions * SEATS);
			}
			request.setAttribute("period", period);
			request.setAttribute("incomes", incomes);
			request.setAttribute("ticketsBought", ticketsBought);
			request.setAttribute("visitingRate", visitingRate);
			request.setAttribute("aveTicketPrice", aveTicketPrice);
			request.setAttribute("top5keyCustomers", top5keyCustomers);

			
			request.getRequestDispatcher("administration.jsp").forward(request,response);
			
			
		} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
	    }
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/administration.jsp");
		rd.include(request, response);
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
