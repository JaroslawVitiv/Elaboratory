
package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 * logs users in or declines if email or password are wrong
 * @author Vitiv
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	

	protected void processData(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		
		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String eitherPasswordEmalWrong = rb.getString("eitherPasswordEmalWrong");
		
		PrintWriter out= response.getWriter();
		UserDAO uDao = new UserDAO();
		
		String email = request.getParameter("email");
		String password = uDao.getMd5(request.getParameter("psw"));
		
		User user = uDao.findUser(email, password);
		
		
		if(user.getFirstName()!=null){
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(60*60);
			response.sendRedirect(request.getContextPath());
		} else{
			response.setCharacterEncoding("UTF-8");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			out.println("<div id=\"loginError\">"+eitherPasswordEmalWrong+"</div>");
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
