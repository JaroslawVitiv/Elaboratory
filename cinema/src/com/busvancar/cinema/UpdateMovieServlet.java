package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateMovieServlet
 */
public class UpdateMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMovieServlet() {
        super();
    }

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	private void processData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
				out.print("Successfully updated");
			} else {
				out.print(":( unfortunately not updated");
			}
			out.print(movie.getTitle());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        getServletContext().getRequestDispatcher("/edit?m="+movie.getId()).forward(request, response);

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
