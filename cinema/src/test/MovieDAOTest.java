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

import com.busvancar.cinema.ConnectionPool;
import com.busvancar.cinema.Movie;
import com.busvancar.cinema.MovieDAO;

import junit.framework.Assert;

public class MovieDAOTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	  @Mock private Connection mockConnection;
	  @Mock private Statement mockStatement;
	  @Mock private boolean disconnected;
	  @Mock private boolean connected;
	  @Mock private Movie movie;
	  @Mock private boolean inserted;
	  @Mock private boolean removed;
	  @Mock private boolean updated;




	  
	 
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
	void connect() throws SQLException  {
    	mockConnection = ConnectionPool.getInstance().getConnection();
    	Assert.assertNull(mockConnection);
   	  
	}
    
    @Test
	void disconnect() throws SQLException  {
    	jdbcConnection = Mockito.mock(Connection.class);
    	Mockito.when(jdbcConnection.isClosed()).thenReturn(disconnected);
   	    Assert.assertFalse(disconnected);
	}
    
    
    
	@Test
	void listAllMovies() throws SQLException  {

		MovieDAO mDao = Mockito.mock(MovieDAO.class);
		List<Movie> mList = new ArrayList<>();
		mList.add(new Movie());
		
		Mockito.when(mDao.listAllMovies()).thenReturn(mList);
		List<Movie> movies = mDao.listAllMovies();
		
		Assert.assertEquals(1, movies.size());
	}

	@Test
	void insertMovie2db() throws SQLException  {

		MovieDAO mDao = Mockito.mock(MovieDAO.class);
		Mockito.when(mDao.insertMovie2db(movie)).thenReturn(inserted);
		
		Assert.assertFalse(inserted);
	}
	
	@Test
	void getMovie() throws SQLException  {
		MovieDAO mDao = Mockito.mock(MovieDAO.class);
		Mockito.when(mDao.getMovie(92)).thenReturn(movie);
		Assert.assertNull(movie);
	}
	
	@Test
	void removeMovie() throws SQLException  {
		MovieDAO mDao = Mockito.mock(MovieDAO.class);
		Mockito.when(mDao.removeMovie(movie)).thenReturn(removed);
		Assert.assertFalse(removed);
	}
	
	@Test
	void updatePoster() throws SQLException  {
		MovieDAO mDao = Mockito.mock(MovieDAO.class);
		Mockito.when(mDao.updatePoster(movie)).thenReturn(updated);
		Assert.assertFalse(updated);
	}
	
	@Test
	void update() throws SQLException  {
		MovieDAO mDao = Mockito.mock(MovieDAO.class);
		Mockito.when(mDao.update(movie)).thenReturn(updated);
		Assert.assertFalse(updated);
	}
}
