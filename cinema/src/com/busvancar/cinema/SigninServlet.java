package com.busvancar.cinema;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
		
		long leftLimit = 1000_000_000L;
	    long rightLimit = 10_000_000_000L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		user.setConfirmationCode(generatedLong);
		
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
					sendConfirmationLink(user, request, response);
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

	private void sendConfirmationLink(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String greeting = rb.getString("greeting");

		String inOrder2becomeClientClickLinkBelow = rb.getString("inOrder2becomeClientClickLinkBelow");
		String click2identify = rb.getString("click2identify");
		String thankUSoMuch = rb.getString("thankUSoMuch");
		
		// message 2 b sent with email
		StringBuilder sb = new StringBuilder();
		sb.append("<div>"+greeting+",  "+user.getFirstName()+" "+user.getLastName()+"! </div>");
		
		sb.append("<div style=\"border: 1px solid grey;  border-radius: 5px;\">");
		sb.append("<div>"+inOrder2becomeClientClickLinkBelow+"</div>");
		sb.append("<div style=\"text-align: right; padding:10px;\"></p></div></div>");
		sb.append("<div><br><br><br><br><a style=\"border-style: solid; border-width: 2px; border-color: maroon; padding:30px; margin:30px;\" "
				+ " href=\"http://localhost:8080/cinema/confirmation?conf_code="+user.getConfirmationCode()+"&email="+user.getEmail()+"\">"+click2identify+"</a> <br><br><br><br></div>");
		sb.append("<div> "+thankUSoMuch+"!/div>");
		sb.append("<div>regards, VitivCinema</div>");
		// message 2 b sent with email //end

		
		final String username = "jaroslaw.vitiv@gmail.com";
        final String password = "jaars200219815x6q8Z3jvitiv";
        
		  String host = "smtp.gmail.com";
	 
	      // Get system properties
	      Properties properties = System.getProperties();
	 
	      
	      properties.put("mail.smtp.host", host);
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.port", "465");
	      properties.put("mail.smtp.socketFactory.port", "465");
	      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      properties.put("mail.smtp.socketFactory.fallback", "false");

	     // Get the default Session object.
	      Session mailSession = Session.getInstance(properties,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });
	      
	      mailSession.setDebug(true);
	      
	      response.setContentType("text/html");

	      try {
	    	  
	    	  Message message = new MimeMessage(mailSession);
	            message.setFrom(new InternetAddress("jaroslaw.vitiv@gmail.com"));
	          
	            message.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse(user.getEmail())
	            );
	            message.setSubject("VitivCinema");
	            message.setContent(sb.toString(), "text/html;charset=UTF-8");
	            Transport.send(message);

	            System.out.println("Done");
	    	  
	    	
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
}
