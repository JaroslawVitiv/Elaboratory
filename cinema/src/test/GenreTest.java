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
import com.busvancar.cinema.CheckAvailabilityServlet;
import com.busvancar.cinema.ConnectionPool;
import com.busvancar.cinema.Genre;
import com.busvancar.cinema.Movie;
import com.busvancar.cinema.MovieDAO;
import com.busvancar.cinema.Ticket;

import junit.framework.Assert;

public class GenreTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock private String[] genres = Genre.genres_en_GB;
	

	 
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
   	public void getGenreSelectOptions() throws SQLException  {
       	Genre genre = Mockito.spy(Genre.class);
       	String result =  "<option value=\"0\">All genres</option><option value=\"1\">comedy</option><option value=\"2\">action</option><option value=\"3\">drama</option><option value=\"4\">historic</option><option value=\"5\">cartoon</option><option value=\"6\">criminal</option><option value=\"7\">thriller</option>";
       	Assert.assertEquals(genre.getGenreSelectOptions(genres), result);
      	  
   	}
  
	
}
