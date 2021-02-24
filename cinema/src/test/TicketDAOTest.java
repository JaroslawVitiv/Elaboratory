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

import com.busvancar.cinema.Movie;
import com.busvancar.cinema.MovieDAO;
import com.busvancar.cinema.Ticket;
import com.busvancar.cinema.TicketDAO;
import com.busvancar.cinema.User;

import static org.mockito.Matchers.anyInt;

import junit.framework.Assert;

public class TicketDAOTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock  private Ticket ticket = new Ticket();
	 @Mock private boolean created = true;
	 @Mock private boolean removed = true;
	 @Mock private boolean remove = true;

	 @Mock private boolean updated = true;

	 @Mock private int tickets = 99;
	 @Mock private Ticket[] ticketArray;
	 @Mock private List<Ticket> tList = new ArrayList<>();
	 @Mock private User user = new User();
	 @Mock private double sum = 1.23;
	 @Mock private String token = "";
	 @Mock private int seats = 99;

	 

	 
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
	public void isCreated() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
		Mockito.when(tDao.isCreated(ticket)).thenReturn(created);
		
		Assert.assertTrue(created);
 	}
    
    @Test
	public void createTicket() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
		Mockito.when(tDao.createTicket(ticket)).thenReturn(created);
		Assert.assertTrue(created);
 	}
    
    @Test
   	public void removeTicket() {
    	Ticket t = Mockito.mock(Ticket.class);
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
       	
   		Mockito.when(tDao.removeTicket(t)).thenReturn(remove);
   		Assert.assertTrue(remove);
    }
  
    @Test
   	public void getAllTickets() {
    	int value = 99;
       	TicketDAO tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getAllTickets(value)).thenReturn(ticketArray);
   		Assert.assertNull(ticketArray);
    }
    
    @Test
   	public void getTicketCount() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getTicketCount(token)).thenReturn(tickets);
   		Assert.assertEquals(99, tickets);
    }
    
    
    @Test
   	public void getBookedUnpaidTickets() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getBookedUnpaidTickets(token)).thenReturn(tList);
   		Assert.assertEquals(0, tList.size());
    }
    
    @Test
   	public void removeFromInvoice() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.removeFromInvoice(ticket)).thenReturn(removed);
   		Assert.assertTrue(removed);
    }
    
    @Test
   	public void purgeAllUnpaidTickets() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
   		Mockito.doNothing().when(tDao).purgeAllUnpaidTickets(token);
    }
    
    @Test
   	public void setUserId2pay() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.setUserId2pay(user, token)).thenReturn(updated);
   		Assert.assertTrue(updated);
    }
    
    @Test
   	public void getTodaysSum() {
    	TicketDAO tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getTodaysSum(user, token)).thenReturn(sum);
   		Assert.assertEquals(sum, 1.23);
    }
    
}
