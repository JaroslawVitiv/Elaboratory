package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IdentityConfirmationServlet
 */
public class IdentityConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdentityConfirmationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		long confCode = Long.parseLong(request.getParameter("conf_code"));
		out.print(email);
		out.print(confCode);
		
		User user = new User();
		user.setEmail(email);
		user.setConfirmationCode(confCode);
		UserDAO uDao = new UserDAO();
		String message = "Something wrong... Not confirmed";
		if(uDao.confirm(user)) {
			 message = "You are confirmed";
			 
		}
		request.setAttribute("message", message);
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
