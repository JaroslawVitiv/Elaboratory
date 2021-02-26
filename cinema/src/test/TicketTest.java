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
	 @Mock private int value = 2;
	 
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
    	ticket.setTicketId(value);
    	Assert.assertEquals(ticket.getTicketId(), value);
 	}
    
    @Test
   	void getTicketId() {
    	ticket = new Ticket();
       	ticket.setTicketId(value);
       	Assert.assertEquals(ticket.getTicketId(), value);
    }
    
    @Test
    public void setSeat() {
    	ticket = new Ticket();
    	ticket.setSeat(value);
    	Assert.assertEquals(ticket.getSeat(), value);
	}
    
    @Test
	public void getSeat() {
    	ticket = new Ticket();
    	ticket.setSeat(value);
    	Assert.assertEquals(ticket.getSeat(), value);
	}
    
    @Test
    public void setSessionId() {
    	ticket = new Ticket();
    	ticket.setSessionId(value);
    	Assert.assertEquals(ticket.getSessionId(), value);
	}
    
    @Test
	public void getSessionId() {
    	ticket = new Ticket();
    	ticket.setSessionId(value);
    	Assert.assertEquals(ticket.getSessionId(), value);
	}
    
    @Test
    public void setPrice() {
    	ticket = new Ticket();
    	double dValue = 1.23;
    	ticket.setPrice(dValue);
    	Assert.assertEquals(ticket.getPrice(), dValue);
	}
    
    @Test
	public void getPrice() {
    	ticket = new Ticket();
    	double dValue = 1.23;
    	ticket.setPrice(dValue);
    	Assert.assertEquals(ticket.getPrice(), dValue);
	}
    
    @Test
    public void setGenre() {
    	ticket = new Ticket();
    	String line = "Genre";
    	ticket.setGenre(line);
    	Assert.assertEquals(ticket.getGenre(), line);
	}
    
    @Test
	public void getGenre() {
    	ticket = new Ticket();
    	String line = "Genre";
    	ticket.setGenre(line);
    	Assert.assertEquals(ticket.getGenre(), line);
	}
    
    @Test
   	public void setPurchaserId() {
    	ticket = new Ticket();
    	ticket.setPurchaserId(value);
       	Assert.assertEquals(ticket.getPurchaserId(), value);
    }
    
    @Test
    public void getPurchaserId() {
    	ticket = new Ticket();
    	ticket.setPurchaserId(value);
       	Assert.assertEquals(ticket.getPurchaserId(), value);
	}
    
    @Test
   	public void setSessionToken() {
    	ticket = new Ticket();
    	String line = "Token";
    	ticket.setSessionToken(line);
       	Assert.assertEquals(ticket.getSessionToken(), line);
    }
    
    @Test
    public void getSessionToken() {
    	ticket = new Ticket();
    	String line = "Token";
    	ticket.setSessionToken(line);
       	Assert.assertEquals(ticket.getSessionToken(), line);
	}
    
    @Test
   	public void setTime() {
    	ticket = new Ticket();
    	Timestamp time = new Timestamp(1111111111);
    	ticket.setTime(time);
       	Assert.assertEquals(ticket.getTime(), time);
    }
    
    @Test
   	public void getTime() {
    	ticket = new Ticket();
    	Timestamp time = new Timestamp(1111111111);
    	ticket.setTime(time);
       	Assert.assertEquals(ticket.getTime(), time);
    }
    
    @Test
   	public void setMovieTitle() {
    	ticket = new Ticket();
    	String line = "MovieTitle";
    	ticket.setMovieTitle(line);
       	Assert.assertEquals(ticket.getMovieTitle(), line);
    }
    
    @Test
   	public void getMovieTitle() {
    	ticket = new Ticket();
    	String line = "MovieTitle";
    	ticket.setMovieTitle(line);
       	Assert.assertEquals(ticket.getMovieTitle(), line);
    }
    
    @Test
   	public void setMovieDuration() {
    	ticket = new Ticket();
    	ticket.setMovieDuration(value);
       	Assert.assertEquals(ticket.getMovieDuration(), value);
    }
    
    @Test
   	public void getMovieDuration() {
    	ticket = new Ticket();
    	ticket.setMovieDuration(value);
       	Assert.assertEquals(ticket.getMovieDuration(), value);
    }
    
    @Test
   	public void setMovieSessionTime() {
    	ticket = new Ticket();
    	Timestamp time = new Timestamp(1111111111);
    	ticket.setMovieSessionTime(time);
       	Assert.assertEquals(ticket.getMovieSessionTime(), time);
    }
    
    @Test
   	public void getMovieSessionTime() {
    	ticket = new Ticket();
    	Timestamp time = new Timestamp(1111111111);
    	ticket.setMovieSessionTime(time);
       	Assert.assertEquals(ticket.getMovieSessionTime(), time);
    }
    
    @Test
   	public void setRow() {
    	ticket = new Ticket();
    	ticket.setRow(value);
       	Assert.assertEquals(ticket.getRow(), value);
    }
    
    @Test
   	public void getRow() {
    	ticket = new Ticket();
    	ticket.setRow(value);
       	Assert.assertEquals(ticket.getRow(), value);
    }
}
