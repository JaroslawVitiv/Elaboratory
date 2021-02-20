package com.busvancar.cinema;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String enterFirstLastNames =  rb.getString("enterFirstLastNames");
		String emailNotValid =  rb.getString("emailNotValid");
		String suchEmailExists  = rb.getString("suchEmailExists");
		String newUserSussessfullyAdded = rb.getString("newUserSussessfullyAdded");
		String unfurtunatelyEmailNotInserted = rb.getString("unfurtunatelyEmailNotInserted");

		
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
			out.println("<font color=red>"+enterFirstLastNames+"</font>");
		}else if(!uDao.isValid(user.getEmail())) {
			out.println("<font color=red>"+emailNotValid+"</font>");
		} else if(uDao.isInserted(user.getEmail())) {
			out.println("<font color=red>"+suchEmailExists+"</font>");
		} else {
			try {
				if(uDao.insertUser(user)) {
					request.setAttribute("message", "<font color=green>"+newUserSussessfullyAdded+"</font>");
					rd = getServletContext().getRequestDispatcher("/messenger.jsp");
				} else {
					out.println("<font color=red>"+unfurtunatelyEmailNotInserted+"</font>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
		}
		rd.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
}
