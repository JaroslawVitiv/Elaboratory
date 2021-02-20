package com.busvancar.cinema;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class AddmovieServlet
 */


public class AddmovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	 // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	private String UPLOAD_DIRECTORY = "images";

	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddmovieServlet() {
        super();
    }

   
    protected void processData(HttpServletRequest request, HttpServletResponse response)    throws ServletException, IOException {
    	HttpSession session = request.getSession();

		Locale locale = new Locale((String) session.getAttribute("l10n"));
		ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		String movieHasBeenAddedSuccessfully =  rb.getString("movieHasBeenAddedSuccessfully");
		String uploadWithExtensions =  rb.getString("uploadWithExtensions");
		User user = (User) session.getAttribute("user");
		int admin = user.getAdmin();
		
		if(admin<1) {
			response.sendRedirect("/cinema");
		}
		
		Movie movie = new Movie();
    	MovieDAO mDao = new MovieDAO();
		PrintWriter out = response.getWriter();
		
		request.setAttribute("genreOptions", "genreOptions + ex.getMessage()");
      
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must have enctype=multipart/form-data.");
            writer.flush();
            return;
        }
 

        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setSizeThreshold(MEMORY_THRESHOLD);

        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         

        upload.setFileSizeMax(MAX_FILE_SIZE);
         

        upload.setSizeMax(MAX_REQUEST_SIZE);

        String uploadPath = getServletContext().getRealPath("")+File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(request));
 
            String extension = "";
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        
                        extension = fileName.split("\\.")[1];
                        if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif")) {
	                        movie.setPoster(fileName);
	                        
	                        // saves the file on disk
	                        item.write(storeFile);
	                        request.setAttribute("message",  "<div style=\"color:green\">"+movieHasBeenAddedSuccessfully+"</div>");
                        } else {
                        	request.setAttribute("message",  "<div style=\"color:red\">"+uploadWithExtensions+"</div>");
                        }
                    } else {
                    	switch(item.getFieldName()) {
                    		case "title":
                    			movie.setTitle(new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"));
                    			out.print(movie.getTitle());
                    			break;
                    		case "descriptionEn":
                    			movie.setDescriptionEn(item.getString());
                    			out.print(movie.getDescriptionEn());

                    			break;
                    		case "descriptionUk":
                    			movie.setDescriptionUa(new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"));
                    			out.print(movie.getDescriptionUa());

                    			break;
                    		case "duration":
                    			movie.setDuration(Integer.parseInt(item.getString()));
                    			out.print(movie.getDuration());

                    			break;
                    		case "genre":
                    			movie.setGenreId(Integer.parseInt(item.getString()));
                    			out.print(movie.getGenreId());

                    			break;
                    		case "price":
                    			movie.setPrice(Double.parseDouble(replaceCommas(item.getString())));
                    			out.print(movie.getPrice());

                    			break;
                    		default:
                    			out.print("Vitiv");
                    			
                    			
                    	}
                    	
                    }
                    
                    
                }
            }
            if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif")) {
            	mDao.insertMovie2db(movie);
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Error: " + ex.getMessage());
        }
        
        // redirects client to message page
        getServletContext().getRequestDispatcher("/administration").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)    throws ServletException, IOException {
    	  processData(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)    throws ServletException, IOException {
    	processData(request, response);
  }
	    
	
	private String replaceCommas(String number) {
		return number.replace(',','.');
	}

}
