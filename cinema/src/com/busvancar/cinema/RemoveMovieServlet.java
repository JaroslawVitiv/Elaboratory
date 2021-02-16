package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveMovieServlet
 */
@WebServlet("/RemoveMovieServlet")
public class RemoveMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveMovieServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String movieRemovied = rb.getString("movieRemovied");
		String movieNotRemovied = rb.getString("movieNotRemovied");
		String continue2admin = rb.getString("continue2admin");
		
		PrintWriter out = response.getWriter();
		Movie movie = new Movie();
		MovieDAO mDao = new MovieDAO();
		int movieId = Integer.parseInt(request.getParameter("m"));
		movie.setId(movieId);
		
		
		try {
			if(mDao.removeMovie(movie)){
				request.setAttribute("message",movieRemovied);	
			} else {
				request.setAttribute("message", movieNotRemovied);	
			}
			request.getRequestDispatcher("messenger.jsp").forward(request,response);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/messenger.jsp");
		rd.include(request, response);
		
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
