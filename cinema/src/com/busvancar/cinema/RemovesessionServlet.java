package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemovesessionServlet
 */
public class RemovesessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemovesessionServlet() {
        super();
    }

    private void processData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		MovieSession mSession = new MovieSession();
		MovieSessionDAO msDao = new MovieSessionDAO();
		mSession.setSessionId(Integer.parseInt(request.getParameter("sessionId")));
		mSession.setMovieId(Integer.parseInt(request.getParameter("movieId")));

	
		out.print("<div>Movie Session removal</div><hr/>");
		try {
			out.print("<div>Session N:"+mSession.getSessionId()+"</div>");
			out.print("<div>From Movie N: "+mSession.getMovieId()+"</div>");
			
			if(msDao.removeMovieSession(mSession)) {
				out.print("<div style=\"color:green\">...was successfully removed</div>");
			} else {
				out.print("<div style=\"color:red\">...still exists and was not properly removed!</div>");
			}
			out.print("<div><a href=\"edit?m="+mSession.getMovieId()+"\">Continue...</div>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
