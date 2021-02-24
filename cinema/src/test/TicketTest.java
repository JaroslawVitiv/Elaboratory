package test;


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

import com.busvancar.cinema.Ticket;
import com.busvancar.cinema.User;

import static org.mockito.Matchers.anyInt;

import junit.framework.Assert;

public class TicketTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock  private int ticketId;
	 @Mock  private int seat;
	 @Mock	private int sessionId;
	 @Mock	private double price;
	 @Mock	private String genre;
	 @Mock	private int purchaserId;
	 @Mock	private String sessionToken;
	 @Mock	private Timestamp time;
	 @Mock	private String movieTitle;
	 @Mock	private int movieDuration;
	 @Mock	private Timestamp movieSessionTime;
	 @Mock	private int row; 
	 @Mock  private Ticket ticket;
	 
	@Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
		logger.info("startup - creating DB connection");
    }

    @After
    public void tearDown() {
    	ticket = null;
        logger.info("closing DB connection");
    }

    @Test
	void setTicketId() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setTicketId(value);
    	Assert.assertEquals(ticket.getTicketId(), value);
 	}
    
    @Test
   	void getTicketId() {
    	ticket = new Ticket();
    	int value = anyInt();
       	ticket.setTicketId(value);
       	Assert.assertEquals(ticket.getTicketId(), value);
    }
    
    @Test
    public void setSeat() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setSeat(value);
    	Assert.assertEquals(ticket.getSeat(), value);
	}
    
    @Test
	public void getSeat() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setSeat(value);
    	Assert.assertEquals(ticket.getSeat(), value);
	}
    
    @Test
    public void setSessionId() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setSessionId(value);
    	Assert.assertEquals(ticket.getSessionId(), value);
	}
    
    @Test
	public void getSessionId() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setSessionId(value);
    	Assert.assertEquals(ticket.getSessionId(), value);
	}
    
    @Test
    public void setPrice() {
    	ticket = new Ticket();
    	double value = 1.23;
    	ticket.setPrice(value);
    	Assert.assertEquals(ticket.getPrice(), value);
	}
    
    @Test
	public void getPrice() {
    	ticket = new Ticket();
    	double value = 1.23;
    	ticket.setPrice(value);
    	Assert.assertEquals(ticket.getPrice(), value);
	}
    
    @Test
    public void setGenre() {
    	ticket = new Ticket();
    	String value = "Genre";
    	ticket.setGenre(value);
    	Assert.assertEquals(ticket.getGenre(), value);
	}
    
    @Test
	public void getGenre() {
    	ticket = new Ticket();
    	String value = "Genre";
    	ticket.setGenre(value);
    	Assert.assertEquals(ticket.getGenre(), value);
	}
    
    @Test
   	public void setPurchaserId() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setPurchaserId(value);
       	Assert.assertEquals(ticket.getPurchaserId(), value);
    }
    
    @Test
    public void getPurchaserId() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setPurchaserId(value);
       	Assert.assertEquals(ticket.getPurchaserId(), value);
	}
    
    @Test
   	public void setSessionToken() {
    	ticket = new Ticket();
    	String value = "Token";
    	ticket.setSessionToken(value);
       	Assert.assertEquals(ticket.getSessionToken(), value);
    }
    
    @Test
    public void getSessionToken() {
    	ticket = new Ticket();
    	String value = "Token";
    	ticket.setSessionToken(value);
       	Assert.assertEquals(ticket.getSessionToken(), value);
	}
    
    @Test
   	public void setTime() {
    	ticket = new Ticket();
    	Timestamp value = new Timestamp(1111111111);
    	ticket.setTime(value);
       	Assert.assertEquals(ticket.getTime(), value);
    }
    
    @Test
   	public void getTime() {
    	ticket = new Ticket();
    	Timestamp value = new Timestamp(1111111111);
    	ticket.setTime(value);
       	Assert.assertEquals(ticket.getTime(), value);
    }
    
    @Test
   	public void setMovieTitle() {
    	ticket = new Ticket();
    	String value = "MovieTitle";
    	ticket.setMovieTitle(value);
       	Assert.assertEquals(ticket.getMovieTitle(), value);
    }
    
    @Test
   	public void getMovieTitle() {
    	ticket = new Ticket();
    	String value = "MovieTitle";
    	ticket.setMovieTitle(value);
       	Assert.assertEquals(ticket.getMovieTitle(), value);
    }
    
    @Test
   	public void setMovieDuration() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setMovieDuration(value);
       	Assert.assertEquals(ticket.getMovieDuration(), value);
    }
    
    @Test
   	public void getMovieDuration() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setMovieDuration(value);
       	Assert.assertEquals(ticket.getMovieDuration(), value);
    }
    
    @Test
   	public void setMovieSessionTime() {
    	ticket = new Ticket();
    	Timestamp value = new Timestamp(1111111111);
    	ticket.setMovieSessionTime(value);
       	Assert.assertEquals(ticket.getMovieSessionTime(), value);
    }
    
    @Test
   	public void getMovieSessionTime() {
    	ticket = new Ticket();
    	Timestamp value = new Timestamp(1111111111);
    	ticket.setMovieSessionTime(value);
       	Assert.assertEquals(ticket.getMovieSessionTime(), value);
    }
    
    @Test
   	public void setRow() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setRow(value);
       	Assert.assertEquals(ticket.getRow(), value);
    }
    
    @Test
   	public void getRow() {
    	ticket = new Ticket();
    	int value = anyInt();
    	ticket.setRow(value);
       	Assert.assertEquals(ticket.getRow(), value);
    }
}
