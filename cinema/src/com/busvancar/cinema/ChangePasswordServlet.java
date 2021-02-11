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
		
		
		String password = request.getParameter("psw");
		String confirmed = request.getParameter("repPsw");
		
		
		
		uDao.getMd5(request.getParameter("psw"));
		
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			user = (User) session.getAttribute("user");
		}
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/changePassword.jsp");
		
		
		if(user.getEmail()!=null) {
				if(!password.equals(confirmed) || password.isBlank()) {
					out.println("<font color=red>Паролі не співпадають. try again...</font>");
					rd.include(request, response);
				} else {
					if(uDao.updatePassword(user.getEmail(),  password)) {
						response.sendRedirect("passwordChangeSuccess.jsp");
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
