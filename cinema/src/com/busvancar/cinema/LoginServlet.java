
package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	

	protected void processData(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		UserDAO uDao = new UserDAO();
		
		String email = request.getParameter("email");
		String password = uDao.getMd5(request.getParameter("psw"));
		
		User user = uDao.findUser(email, password);
		
		
		if(user.getFirstName()!=null){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			session.setMaxInactiveInterval(60*60);
			response.sendRedirect(request.getContextPath());
			
			
		}else{
			response.setCharacterEncoding("UTF-8");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			out.println("<font color=red>Either email address name or password is wrong.  Щось не те</font>");
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
