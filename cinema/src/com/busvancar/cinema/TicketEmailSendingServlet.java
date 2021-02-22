package com.busvancar.cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.ResourceBundle.*;

/**
 * Servlet implementation class TicketEmailSendingServlet
 */
public class TicketEmailSendingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketEmailSendingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String movie = rb.getString("movie");
		String datetime = rb.getString("datetime");
		String priceTr = rb.getString("price");
		String duration = rb.getString("duration");
		String mins = rb.getString("mins");
		String row = rb.getString("row");
		String seat = rb.getString("seat");
		String isSuccessfullySentCheckYourInboxInludingSPAMbox =  rb.getString("isSuccessfullySentCheckYourInboxInludingSPAMbox");
		String ticketTr =  rb.getString("ticket");
		
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));

		PrintWriter out = response.getWriter();
		TicketDAO tDao = new TicketDAO();
		Ticket ticket = tDao.getTicket(ticketId);
		UserDAO uDao = new UserDAO();
		User user = uDao.getUser(ticket);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy (HH.mm)");
		
		ticket.setTicketId(Integer.parseInt(request.getParameter("ticketId")));
		out.print(ticketTr+" NN:"+request.getParameter("ticketId")+" "+isSuccessfullySentCheckYourInboxInludingSPAMbox);
		
		// message 2 b sent with email
		StringBuilder sb = new StringBuilder();
		sb.append("<div>Вітаємо!  "+user.getFirstName()+" "+user.getLastName()+"! You have new tickets!</div>");
		
		sb.append("<div style=\"border: 1px solid grey;  border-radius: 5px;\">");
		sb.append("<div>"+movie+": <b>"+ticket.getMovieTitle()+"</b> |  "+priceTr+": <b>"+ticket.getPrice()+"</b> $$</div>");
		sb.append("<div>"+datetime+": <b>"+df.format(ticket.getTime())+"</b> | "+duration+": <b>"+ticket.getMovieDuration()+"</b> "+mins+"</div>");
		sb.append("<div>"+row+": <b>"+ticket.getRow()+"</b> | "+seat+": <b>"+ticket.getSeat()+"</b></div>");
		sb.append("<div style=\"text-align: right; padding:10px;\"></p></div></div>");
		sb.append("<div> <a href=\"https://www.busvancar.com\">click it</a> </div>");
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
	         logger.warn(mex);
	      }
	     
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
