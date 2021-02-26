package test;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import com.busvancar.cinema.MovieSession;
import com.busvancar.cinema.MovieSessionDAO;
import com.busvancar.cinema.TicketDAO;

import junit.framework.Assert;

public class MovieSessionDAOTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	  @Mock private Connection mockConnection;
	  @Mock private Statement mockStatement;
	  @Mock private boolean disconnected;
	  @Mock private boolean connected;
	  @Mock private Movie movie;
	  @Mock private MovieSession ms;
	  @Mock private MovieSessionDAO msDao;
	  @Mock private boolean inserted;
	  @Mock private boolean created = true;
	  @Mock private boolean removed = true;
	  @Mock private boolean updated;
	  @Mock private List<MovieSession> mList;
	  @Mock private int sortBy = 0;
	  @Mock private int ascDesc = 0;
	  @Mock private int genreIndex = 1;
	  @Mock private int sessionId = 6;
	  @Mock private int page = 7;
	  @Mock private int coins = 100;
	  @Mock private int seat = 5;
	  @Mock private int views = 12;
	  @Mock private double price = 100.00;
	  @Mock private LocalDate chosendate =  LocalDate.of(2007, 11, 12);
	  @Mock private LocalDate start = LocalDate.of(2007, 11, 12);
	  @Mock private LocalDate end = LocalDate.of(2007, 12, 12);

	 
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
	public void insertMovieSession2db() throws SQLException  {
		ms = Mockito.mock(MovieSession.class);
		msDao = Mockito.mock(MovieSessionDAO.class);
    	
    	Mockito.when(msDao.insertMovieSession2db(ms)).thenReturn(created);
		Assert.assertTrue(created);
	}
	
	@Test
	public void getAllMovieSessions() throws SQLException  {
		movie = Mockito.mock(Movie.class);
		msDao = Mockito.mock(MovieSessionDAO.class);
    	
    	Mockito.when(msDao.getAllMovieSessions(movie)).thenReturn(mList);
		Assert.assertNull(mList);
	}
	
	@Test
	public void removeMovieSession() throws SQLException  {
		ms = Mockito.mock(MovieSession.class);
		msDao = Mockito.mock(MovieSessionDAO.class);
    	
    	Mockito.when(msDao.removeMovieSession(ms)).thenReturn(removed);
		Assert.assertTrue(removed);
	}
	
	@Test
	public void getSchedule() throws SQLException{
		movie = Mockito.mock(Movie.class);
		msDao = Mockito.mock(MovieSessionDAO.class);
    	
    	Mockito.when(msDao.getSchedule(sortBy, ascDesc, genreIndex, chosendate)).thenReturn(mList);
		Assert.assertNull(mList);
	}
	
	@Test
	public void getMovieSession() throws SQLException{
		msDao = Mockito.mock(MovieSessionDAO.class);
		
		Mockito.when(msDao.getMovieSession(sessionId)).thenReturn(ms);
		Assert.assertNull(ms);
	}
	
	@Test
	public void getMovieSessionBasePrice() throws SQLException{
		msDao = Mockito.mock(MovieSessionDAO.class);
		
		Mockito.when(msDao.getMovieSessionBasePrice(sessionId)).thenReturn(coins);
		Assert.assertEquals(coins, 100);
	}
	
	@Test
	public void getPrice() throws SQLException{
		msDao = Mockito.mock(MovieSessionDAO.class);
		
		Mockito.when(msDao.getPrice(coins, seat)).thenReturn(price);
		Assert.assertEquals(price, 100.00);
	}
	
	@Test
	public void getSchedule2() throws SQLException{
		msDao = Mockito.mock(MovieSessionDAO.class);
		mList = new ArrayList<>();
		
		Mockito.when(msDao.getSchedule(page)).thenReturn(mList);
		Assert.assertNotNull(mList);
	}
	
	@Test
	public void getViews() throws SQLException{
		msDao = Mockito.mock(MovieSessionDAO.class);
		movie = Mockito.mock(Movie.class);
		
		Mockito.when(msDao.getViews(movie)).thenReturn(views);
		Assert.assertEquals(views, 12);
	}
	
	@Test
	public void getIncome() throws SQLException{
		msDao = Mockito.mock(MovieSessionDAO.class);
		movie = Mockito.mock(Movie.class);
		
		Mockito.when(msDao.getIncome(movie)).thenReturn(price);
		Assert.assertEquals(price, 100.00);
	}
	
	@Test
	public void getMovieSessions() throws SQLException{
		msDao = Mockito.mock(MovieSessionDAO.class);
		movie = Mockito.mock(Movie.class);
		
		Mockito.when(msDao.getMovieSessions(start, end)).thenReturn(coins);
		Assert.assertEquals(coins, 100);
	}
}
