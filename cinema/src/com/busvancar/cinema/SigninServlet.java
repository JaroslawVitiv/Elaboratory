package com.busvancar.cinema;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SigninServlet
 */
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SigninServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO uDao = new UserDAO();
		PrintWriter out = response.getWriter();
		
		User user = new User();
		
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(uDao.getMd5(request.getParameter("psw")));
		
		
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/signin.jsp");
		
		if(user.getFirstName().isBlank() || user.getLastName().isBlank()) {
			out.println("<font color=red>Enter both first and lastName. Просимо ввести прізвище та імя</font>");
			rd.include(request, response);
		}else if(!uDao.isValid(user.getEmail())) {
			out.println("<font color=red>Email is not valid. Емейл не пасує...</font>");
			rd.include(request, response);
		} else if(uDao.isInserted(user.getEmail())) {
			out.println("<font color=red>Such an email alredy exists. Емейл не пасує...</font>");
			rd.include(request, response);
		}else {
			try {
				if(uDao.insertUser(user)) {
					response.sendRedirect("loginSuccess.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			out.println("<font color=red>Unfortunately, the email was not inserted</font>");
			rd.include(request, response);
		}

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
}
