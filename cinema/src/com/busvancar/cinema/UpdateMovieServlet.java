package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class UpdateMovieServlet
 * @author Vitiv
 * updates movie data at mySQL
 */
public class UpdateMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMovieServlet() {
        super();
    }

	private void processData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String successfullyUpdated = rb.getString("successfullyUpdated");
		String unfortunatelyNotUpdated = rb.getString("unfortunatelyNotUpdated");

		 
		PrintWriter out = response.getWriter();
		Movie movie = new Movie();
		movie.setId(Integer.parseInt(request.getParameter("id")));
		movie.setTitle(request.getParameter("title"));
		movie.setDescriptionEn(request.getParameter("descriptionEn"));
		movie.setDescriptionUa(request.getParameter("descriptionUa"));
		movie.setDuration(Integer.parseInt(request.getParameter("duration")));
		movie.setGenreId(Integer.parseInt(request.getParameter("genre")));
		movie.setPrice(Double.parseDouble(request.getParameter("price")));
		
		MovieDAO mDao = new MovieDAO();
		try {
			if(mDao.update(movie)) {
				out.print(successfullyUpdated);
			} else {
				out.print(":( "+unfortunatelyNotUpdated);
			}
			out.print(movie.getTitle());
		} catch (SQLException e) {
			logger.warn(e);
		}
        getServletContext().getRequestDispatcher("/edit?m="+movie.getId()).forward(request, response);

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
