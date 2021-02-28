package test;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.busvancar.cinema.CinemaHallServlet;
import com.busvancar.cinema.ConnectionPool;
import com.busvancar.cinema.Movie;
import com.busvancar.cinema.MovieDAO;
import com.busvancar.cinema.Movies;
import com.busvancar.cinema.Ticket;

import junit.framework.Assert;

public class MoviesTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock private int page = 3;
	 
	 
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
   	public void getPagination() throws SQLException  {
    	HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    	Movies movies = Mockito.spy(Movies.class);
       	String result =  "";
       	Assert.assertEquals(movies.getPagination(page, req), result);
      	  
   	}
    
    @Test
   	public void setGenres() throws SQLException  {
    	Movies movies = Mockito.spy(Movies.class);
    	HttpSession session = Mockito.mock(HttpSession.class);
    	List<String> genres = movies.setGenres("en_GB", session);
    	
       	int expected =  8;
       	Assert.assertEquals(genres.size(), expected);
      	  
   	}
  
	
}
