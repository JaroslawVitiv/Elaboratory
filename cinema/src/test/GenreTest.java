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
    
    @Test
   	public void getGenreOptions() throws SQLException  {
       	Genre genre = Mockito.spy(Genre.class);
       	String result =  "<li><a class=\"dropdown-item\" href=\"/cinema/?genre=0\">All genres</a></li><li><hr/></li><li><a class=\"dropdown-item\" href=\"/cinema/?genre=1\">comedy</a></li><li><hr/></li><li><a class=\"dropdown-item\" href=\"/cinema/?genre=2\">action</a></li><li><hr/></li><li><a class=\"dropdown-item\" href=\"/cinema/?genre=3\">drama</a></li><li><hr/></li><li><a class=\"dropdown-item\" href=\"/cinema/?genre=4\">historic</a></li><li><hr/></li><li><a class=\"dropdown-item\" href=\"/cinema/?genre=5\">cartoon</a></li><li><hr/></li><li><a class=\"dropdown-item\" href=\"/cinema/?genre=6\">criminal</a></li><li><hr/></li><li><a class=\"dropdown-item\" href=\"/cinema/?genre=7\">thriller</a></li><li><hr/></li>";
       	Assert.assertEquals(genre.getGenreOptions(genres), result);
    }
    
    @Test
   	public void getGenreList() throws SQLException  {
       	Genre genre = Mockito.spy(Genre.class);
       	int result =  8;
       	Assert.assertEquals(genre.getGenreList(genres).size(), result);
    }
  
	
}
