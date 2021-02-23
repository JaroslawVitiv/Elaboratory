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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class IdentityConfirmationServlet
 * handles incoming data from user and approves or declines 
 * identification of the user
 * @author Vitiv
 */
public class IdentityConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	} 
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
		
		HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String somethingWrongNotConfirmed =  rb.getString("somethingWrongNotConfirmed");
		String youAreConfirmed =  rb.getString("youAreConfirmed");

		
		User user = new User();
		user.setEmail(email);
		user.setConfirmationCode(confCode);
		UserDAO uDao = new UserDAO();
		String message = somethingWrongNotConfirmed;
		if(uDao.confirm(user)) {
			 message = youAreConfirmed;
		}
		logger.info(message);
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
