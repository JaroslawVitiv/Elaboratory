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

import com.busvancar.cinema.Movie;

import static org.mockito.Matchers.anyInt;

import junit.framework.Assert;

public class MovieTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	  @Mock private int id;
	  @Mock private String title;
	  @Mock private String descriptionUa;
	  @Mock private String descriptionEn;
	  @Mock private int duration;
	  @Mock private String genre;
	  @Mock private String genreUa;
	  @Mock private int genreId;
	  @Mock private String poster;
	  @Mock private double price;
	  @Mock private Movie movie;

	
	 
	@Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
		logger.info("startup - creating DB connection");
    }

    @After
    public void tearDown() {
    	movie = null;
        logger.info("closing DB connection");
    }

    @Test
	void setId() {
    	movie = new Movie();
    	int value = anyInt();
    	movie.setId(value);
    	Assert.assertEquals(movie.getId(), value);
 	}
    
    @Test
   	void getId() {
    	movie = new Movie();
    	int value = anyInt();
       	movie.setId(value);
       	Assert.assertEquals(movie.getId(), value);
    }
    
    @Test
    public void setGenreId() {
    	movie = new Movie();
    	int value = anyInt();
    	movie.setGenreId(value);
    	Assert.assertEquals(movie.getGenreId(), value);
	}
    
    @Test
	public void getGenreId() {
		movie = new Movie();
    	int value = anyInt();
    	movie.setGenreId(value);
    	Assert.assertEquals(movie.getGenreId(), value);
	}
    
    @Test
    public void setTitle() {
    	movie = new Movie();
    	String value = "Cinema";
    	movie.setTitle(value);
    	Assert.assertEquals(movie.getTitle(), value);
	}
    
    @Test
	public void getTitle() {
		movie = new Movie();
    	String value = "Cinema";
    	movie.setTitle(value);
    	Assert.assertEquals(movie.getTitle(), value);
	}
    
    @Test
    public void setDescriptionUa() {
    	movie = new Movie();
    	String value = "Description";
    	movie.setDescriptionUa(value);
    	Assert.assertEquals(movie.getDescriptionUa(), value);
	}
    
    @Test
	public void getDescriptionUa() {
		movie = new Movie();
    	String value = "Description";
    	movie.setDescriptionUa(value);
    	Assert.assertEquals(movie.getDescriptionUa(), value);
	}
    
    @Test
    public void setDescriptionEn() {
    	movie = new Movie();
    	String value = "DescriptionEn";
    	movie.setDescriptionUa(value);
    	Assert.assertEquals(movie.getDescriptionUa(), value);
	}
    
    @Test
	public void getDescriptionEn() {
		movie = new Movie();
    	String value = "DescriptionEn";
    	movie.setDescriptionEn(value);
    	Assert.assertEquals(movie.getDescriptionEn(), value);
	}
    
    @Test
   	public void setDuration() {
    	movie = new Movie();
    	int value = anyInt();
       	movie.setDuration(value);
       	Assert.assertEquals(movie.getDuration(), value);
    }
    
    @Test
    public void getDuration() {
    	movie = new Movie();
    	int value = anyInt();
    	movie.setDuration(value);
    	Assert.assertEquals(movie.getDuration(), value);
	}
    
    @Test
   	public void setGenre() {
    	movie = new Movie();
    	String value = "Genre";
    	movie.setGenre(value);
       	Assert.assertEquals(movie.getGenre(), value);
    }
    
    @Test
    public void getGenre() {
    	movie = new Movie();
    	String value = "Genre";
    	movie.setGenre(value);
    	Assert.assertEquals(movie.getGenre(), value);
	}
    
    @Test
   	public void setGenreUa() {
    	movie = new Movie();
    	String value = "GenreUa";
    	movie.setGenreUa(value);
       	Assert.assertEquals(movie.getGenreUa(), value);
    }
    
    @Test
    public void getGenreUa() {
    	movie = new Movie();
    	String value = "GenreUa";
    	movie.setGenreUa(value);
    	Assert.assertEquals(movie.getGenreUa(), value);
	}
   
    @Test
   	public void setPoster() {
    	movie = new Movie();
    	String value = "Poster";
    	movie.setPoster(value);
       	Assert.assertEquals(movie.getPoster(), value);
    }
    
    @Test
    public void getPoster() {
    	movie = new Movie();
    	String value = "Puster";
    	movie.setPoster(value);
    	Assert.assertEquals(movie.getPoster(), value);
	}
    
    @Test
   	public void setPrice() {
    	movie = new Movie();
    	double value = 1.11;
    	movie.setPrice(value);
    	Assert.assertEquals(movie.getPrice(), value);
	}
    
    @Test
    public void getPrice() {
    	movie = new Movie();
    	double value = 1.11;
    	movie.setPrice(value);
    	Assert.assertEquals(movie.getPrice(), value);
	}
}
