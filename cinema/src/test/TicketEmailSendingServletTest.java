package test;


import java.sql.Connection;
import java.util.Properties;

import javax.mail.Session;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.busvancar.cinema.TicketEmailSendingServlet;
import com.busvancar.cinema.User;
import com.sun.jdi.connect.Transport;

import junit.framework.Assert;

public class TicketEmailSendingServletTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock private int page = 3;
	 @Mock private Session mailSession;
	 @Mock private User user;
	 @Mock private String text;

	 
	 
	@Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
		logger.info("startup - creating DB connection");
    }

    @After
    public void tearDown() {
        logger.info("closing DB connection");
    }

    
    @Test
   	public void getProperties()  {
    	TicketEmailSendingServlet tess = Mockito.spy(TicketEmailSendingServlet.class);
       	Assert.assertNotNull(tess.getProperties()); 	  
   	}
    
    @Test
   	public void getMessage()  {
    	User user = Mockito.spy(User.class);
    	user.setEmail("admin@admin.com");
    	TicketEmailSendingServlet tess = Mockito.spy(TicketEmailSendingServlet.class);
    	Transport transport = Mockito.spy(Transport.class);
       	Assert.assertNotNull(tess.getMessage(mailSession, user, text));
      	  
   	}
   
  
	
}
