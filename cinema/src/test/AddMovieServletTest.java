package test;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.mock.*;
import org.mockito.stubbing.Answer;

import com.busvancar.cinema.AddmovieServlet;
import com.busvancar.cinema.ConnectionPool;
import com.busvancar.cinema.Movie;
import com.busvancar.cinema.MovieDAO;

import junit.framework.Assert;

public class AddMovieServletTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	  @Mock private String line = "11.22,33";
	 





	  
	 
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
	void replaceCommas() throws SQLException  {
    	AddmovieServlet ams = Mockito.spy(AddmovieServlet.class);
    	String result = "11.22.33";
    	Assert.assertEquals(ams.replaceCommas(line), result);
   	  
	}
    
  
	
}
