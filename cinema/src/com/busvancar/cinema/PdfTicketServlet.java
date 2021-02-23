package com.busvancar.cinema;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PdfTicketGeneratorServlet
 * @author Vitiv
 * creates .pdf files for each ticket that user can download from his profile
 */
public class PdfTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PdfTicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws DocumentException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			int ticketId = Integer.parseInt(request.getParameter("ticketId"));
			String sessionToken = request.getParameter("sessionToken");
			TicketDAO tDao = new TicketDAO();
			Ticket ticket  = tDao.getTicket(ticketId, sessionToken);
			//get the output stream for writing binary data in the response.
			ServletOutputStream os = response.getOutputStream();
			//set the response content type to PDF
			response.setContentType("application/pdf charset=UTF-8"); 
			
			//create a new document
			Document doc = new Document();

			//create some special styles and font sizes
			Font bfBold18 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0)); 
			
		 	    

			try {
				
				//create an instance of the PdfWriter using the output stream
				PdfWriter.getInstance(doc, os); 
				
				//document header properties
				doc.addAuthor("JaroslawVitiv");
				doc.addCreationDate();
				doc.addProducer();
				doc.addCreator("VitivCinema.com");
				doc.addTitle("TicketPDF");
				doc.setPageSize(PageSize.LETTER);
				doc.open();
				
				//start a new page here
				doc.newPage();
				//create a new paragraph for each country
				Paragraph paragraph = new Paragraph();
				//create an anchor
				Anchor anchor = new Anchor("VitivCinema Ticket", bfBold18);
				anchor.setName("AnchorName");
				//add the anchor to the paragraph
				paragraph.add(anchor);
				//add the paragraph to the document
				doc.add(paragraph);
				
				request.setCharacterEncoding("UTF-8");
				HttpSession session = request.getSession();
		    	Locale locale = new Locale((String) session.getAttribute("l10n"));
				ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
				String seat = rb.getString("seat");
				String row = rb.getString("row");
				String movieTitle = rb.getString("movieTitle");
				String duration =  rb.getString("duration");
				String datetime  = rb.getString("datetime");
				String price =  rb.getString("price");
				String mins = rb.getString("mins");
				String ticketTr = rb.getString("ticket");
				
				BaseFont unicode = BaseFont.createFont("arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				Font bf12 = new Font(unicode, 12);
				    	   
				//add some detail information about the country
				doc.add( new Paragraph("-------------------------------------------", bf12));
				doc.add( new Paragraph(ticketTr+" NN: "+ticket.getTicketId(), bf12));
				doc.add( new Paragraph(movieTitle+": "+ticket.getMovieTitle(), bf12));
				doc.add( new Paragraph(duration+": "+ticket.getMovieDuration() +" "+mins , bf12));
				doc.add( new Paragraph(seat+": "+ticket.getSeat() , bf12));
				doc.add( new Paragraph(row+": "+ticket.getRow() , bf12));
				doc.add( new Paragraph(price+": "+ticket.getPrice()+" ($$)", bf12));
				doc.add( new Paragraph(datetime+": "+String.format("%te.%1$tm.%1$tY (%1$TH:%1$TM)", ticket.getMovieSessionTime()), bf12));
				doc.add( new Paragraph("-------------------------------------------", bf12));

				doc.close(); 
				
			} catch (DocumentException e){
				logger.warn(e);
			} catch(Exception e) {
				logger.warn(e);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processData(request, response);
	}

}
