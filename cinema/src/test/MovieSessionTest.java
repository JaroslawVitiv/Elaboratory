package test;


import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.busvancar.cinema.MovieSession;

import junit.framework.Assert;

public class MovieSessionTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	  @Mock private int id;
	
	  @Mock private double price;
	  @Mock private MovieSession movieSession;
	  @Mock private int value = 92;
	

	
	 
	@Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
		logger.info("startup - creating DB connection");
    }

    @After
    public void tearDown() {
    	movieSession = null;
        logger.info("closing DB connection");
    }

    @Test
	void getSessionId() {
    	movieSession = new MovieSession();
    	movieSession.setSessionId(value);
    	Assert.assertEquals(movieSession.getSessionId(), value);
 	}
    
    @Test
   	void setSessionId() {
    	movieSession = new MovieSession();
    	movieSession.setSessionId(value);
    	Assert.assertEquals(movieSession.getSessionId(), value);
    }

    @Test
    public void getMovieId() {
    	movieSession = new MovieSession();
    	movieSession.setMovieId(value);
    	Assert.assertEquals(movieSession.getMovieId(), value);
	}
    
    @Test
	public void setMovieId() {
    	movieSession = new MovieSession();
		movieSession.setMovieId(value);
    	Assert.assertEquals(movieSession.getMovieId(), value);
	}
    
    @Test
   	public void setPrice() {
    	movieSession = new MovieSession();
    	double dValue = 1.23;
    	movieSession.setPrice(dValue);
    	Assert.assertEquals(movieSession.getPrice(), dValue);
	}
    
    @Test
    public void getPrice() {
    	movieSession = new MovieSession();
    	double dValue = 1.23;
    	movieSession.setPrice(dValue);
    	Assert.assertEquals(movieSession.getPrice(), dValue);
	}
    
    @Test
    public void setDateTime() {
    	movieSession = new MovieSession();
    	Timestamp date = new Timestamp(1111111111);
    	movieSession.setDateTime(date);
    	Assert.assertEquals(movieSession.getDateTime(), date);
	}
    
    @Test
	public void getDateTime() {
    	movieSession = new MovieSession();
    	Timestamp date = new Timestamp(1111111111);
    	movieSession.setDateTime(date);
    	Assert.assertEquals(movieSession.getDateTime(), date);
	}
    
    @Test
    public void setMoviePoster() {
    	movieSession = new MovieSession();
    	String line = "Poster";
    	movieSession.setMoviePoster(line);
    	Assert.assertEquals(movieSession.getMoviePoster(), line);
	}
    
    @Test
	public void getMoviePoster() {
    	movieSession = new MovieSession();
    	String line = "Poster";
    	movieSession.setMoviePoster(line);
    	Assert.assertEquals(movieSession.getMoviePoster(), line);
	}
    
    @Test
    public void setMovieTitle() {
    	movieSession = new MovieSession();
    	String line = "MovieTitle";
    	movieSession.setMovieTitle(line);
    	Assert.assertEquals(movieSession.getMovieTitle(), line);
	}
    
    @Test
	public void getMovieTitle() {
    	movieSession = new MovieSession();
    	String line = "MovieTitle";
    	movieSession.setMovieTitle(line);
    	Assert.assertEquals(movieSession.getMovieTitle(), line);
	}
    
    @Test
    public void getMovieDescriptionEn() {
    	movieSession = new MovieSession();

    	String line = "MovieDescription";
    	movieSession.setMovieDescriptionEn(line);
    	Assert.assertEquals(movieSession.getMovieDescriptionEn(), line);
	}
    
    @Test
	public void setMovieDescriptionEn() {
    	movieSession = new MovieSession();

    	String line = "MovieDescription";
    	movieSession.setMovieDescriptionEn(line);
    	Assert.assertEquals(movieSession.getMovieDescriptionEn(), line);
	}
    
    @Test
    public void getMovieDescriptionUa() {
    	movieSession = new MovieSession();

    	String line = "MovieDescription";
    	movieSession.setMovieDescriptionUa(line);
    	Assert.assertEquals(movieSession.getMovieDescriptionUa(), line);
	}
    
    @Test
	public void setMovieDescriptionUa() {
    	movieSession = new MovieSession();

    	String line = "MovieDescription";
    	movieSession.setMovieDescriptionUa(line);
    	Assert.assertEquals(movieSession.getMovieDescriptionUa(), line);
	}
    
    @Test
   	public void getMovieDuration() {
    	movieSession = new MovieSession();

    	movieSession.setMovieDuration(value);
       	Assert.assertEquals(movieSession.getMovieDuration(), value);
    }
    
    @Test
    public void setMovieDuration() {    	movieSession = new MovieSession();

    	movieSession.setMovieDuration(value);
       	Assert.assertEquals(movieSession.getMovieDuration(), value);
	}
    
    @Test
   	public void getAvailableSeats() {    	
    	movieSession = new MovieSession();
    	movieSession.setAvailableSeats(value);
       	Assert.assertEquals(movieSession.getAvailableSeats(), value);
    }
    
    @Test
    public void setAvailableSeats() {    	
    	movieSession = new MovieSession();
    	movieSession.setAvailableSeats(value);
       	Assert.assertEquals(movieSession.getAvailableSeats(), value);
	}
    
    @Test
   	public void getMovieGenre() {    	
    	movieSession = new MovieSession();
    	String line = "Genre";
    	movieSession.setMovieGenre(line);
       	Assert.assertEquals(movieSession.getMovieGenre(), line);
    }
    
    @Test
    public void setMovieGenre() {    	movieSession = new MovieSession();

    	String line = "Genre";
    	movieSession.setMovieGenre(line);
       	Assert.assertEquals(movieSession.getMovieGenre(), line);
	}
   
    @Test
   	public void getMovieGenreUa() {
    	movieSession = new MovieSession();
    	String line = "GenreUa";
    	movieSession.setMovieGenreUa(line);
       	Assert.assertEquals(movieSession.getMovieGenreUa(), line);
    }
    
    @Test
    public void setMovieGenreUa() {    
    	movieSession = new MovieSession();
    	String line = "GenreUa";
    	movieSession.setMovieGenreUa(line);
       	Assert.assertEquals(movieSession.getMovieGenreUa(), line);
	}
   
}
