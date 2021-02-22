package com.busvancar.cinema;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class AddmovieServlet
 */


public class UpdatePicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	
	public void init() {
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}   
	
	 // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	private String UPLOAD_DIRECTORY = "images";

	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePicServlet() {
        super();
    }

   
    protected void processData(HttpServletRequest request, HttpServletResponse response)    throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	 Locale locale = new Locale((String) session.getAttribute("l10n"));
		 ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
		 String movieAddedSuccessfully = rb.getString("movieAddedSuccessfully"); 
		 String uploadWithExtensions = rb.getString("uploadWithExtensions");
		User user = (User) session.getAttribute("user");
		PrintWriter out = response.getWriter();
		
		
		int admin = 0; 
		
		if(user!=null) {
			admin = (int) user.getAdmin();
		}
		
		if(admin<1) {
			response.sendRedirect("/cinema");
		}
		
		Movie movie = new Movie();
    	MovieDAO mDao = new MovieDAO();
		
		request.setAttribute("genreOptions", "genreOptions + ex.getMessage()");
   
        if (!ServletFileUpload.isMultipartContent(request)) {
            out.println("Error: Form must have enctype=multipart/form-data.");
            out.flush();
            return;
        }

 
        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setSizeThreshold(MEMORY_THRESHOLD);

        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);

        String uploadPath = getServletContext().getRealPath("")  + UPLOAD_DIRECTORY;

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
                        if(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg") || extension.toLowerCase().equals("png") || extension.toLowerCase().equals("gif")) {
	                        movie.setPoster(fileName);
	                        item.write(storeFile);
	                        
	                        request.setAttribute("message",  "<div style=\"color:green\">"+movieAddedSuccessfully+"</div>");
                        } else {
                        	request.setAttribute("message",  "<div style=\"color:red\">"+uploadWithExtensions+"</div>");
                        }
                        
                        out.print(item);
                    } else {
                    	if("movieId".equals(item.getFieldName())) {
                    		movie.setId(Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"))); 
                    	} else {
	                    	String currentPath = uploadPath + File.separator + new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
	                    	Path path = FileSystems.getDefault().getPath(currentPath);
	                    	try {
		                       Files.deleteIfExists(path);
	                    	} catch (IOException x) {
		                       logger.info(x);
	                    	}
                    	}
                    }
                    
                    
                }
            }
            
            out.print(request.getAttribute("message"));
            
            if(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg") || extension.toLowerCase().equals("png") || extension.toLowerCase().equals("gif")) {
            	if(mDao.updatePoster(movie)) {
            		out.print("The poster was updated");
            	} else {
            		out.print("Unfortunately, the poster was not updated");
            	}
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Error: " + ex.getMessage());
            logger.warn(ex);
        }
      
        getServletContext().getRequestDispatcher("/edit?m="+movie.getId()).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)    throws ServletException, IOException {
    	  processData(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)    throws ServletException, IOException {
    	processData(request, response);
    }
	    
	
}
