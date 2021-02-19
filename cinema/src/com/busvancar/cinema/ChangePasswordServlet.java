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

import com.busvancar.cinema.User;
import com.busvancar.cinema.UserDAO;

/**
 * Servlet implementation class ChangePasswordServlet
 */
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
    }

	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO uDao = new UserDAO();
		PrintWriter out = response.getWriter();
		User user = null;
		HttpSession session = request.getSession();
		 Locale locale = new Locale((String) session.getAttribute("l10n"));
		 ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		 String passwordsDontCoincide = rb.getString("passwordsDontCoincide");
		 String passwordIsOK = rb.getString("passwordIsOK");
		
		String password = request.getParameter("psw");
		String confirmed = request.getParameter("repPsw");
		
		
		
		uDao.getMd5(request.getParameter("psw"));
		
		response.setCharacterEncoding("UTF-8");
		
		
		if(session.getAttribute("user")!=null) {
			user = (User) session.getAttribute("user");
		}
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/changePassword.jsp");
		
		
		if(user.getEmail()!=null) {
				if(!password.equals(confirmed) || password.isBlank()) {
					out.println("<div style=\"text-align:center\"><font color=red>"+passwordsDontCoincide+"</font></div>");
					rd.include(request, response);
				} else {
					if(uDao.updatePassword(user.getEmail(),  password)) {
						request.setAttribute("message", "<div><font color=maroon>"+passwordIsOK+"</font></div>");
						rd = getServletContext().getRequestDispatcher("/messenger.jsp");
						rd.include(request, response);
					}else {
						out.println("<font color=red>Unfortunately, the password was not updated</font>");
						rd.include(request, response);
					} 
					
				}	
				
		} else {
			response.sendRedirect("login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
