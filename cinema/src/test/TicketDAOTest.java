package test;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.busvancar.cinema.Ticket;
import com.busvancar.cinema.TicketDAO;
import com.busvancar.cinema.User;

import junit.framework.Assert;

public class TicketDAOTest {
	private static Logger logger = null;
	
	 @Mock private Ticket ticket;
	 @Mock private boolean created = true;
	 @Mock private boolean removed = true;
	 @Mock private boolean remove = true;
	 @Mock private boolean updated = true;

	 @Mock private int tickets = 11;
	 @Mock private Ticket[] ticketArray;
	 @Mock private List<Ticket> tList = new ArrayList<>();
	 @Mock private User user = new User();
	 @Mock private double sum = 1.23;
	 @Mock private String token = "";
	 @Mock private int seats = 22;
	 @Mock private int movieSession = 33;
	 @Mock private int ticketId = 44;
	 @Mock private LocalDate start = LocalDate.of(2007, 11, 12);
	 @Mock private LocalDate end = LocalDate.of(2007, 12, 12);
	 @InjectMocks TicketDAO tDao = new TicketDAO();
	 @Rule public MockitoRule rule = MockitoJUnit.rule();
	
	 
	
	 	 
	@Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
		logger.info("startup - creating DB connection");
    }

    @After
    public void tearDown() {
    	tDao = null;
    	ticket = null;
        logger.info("closing DB connection");
    }

    @Test
	public void isCreated() {
    	tDao =  Mockito.mock(TicketDAO.class);
    	ticket = Mockito.mock(Ticket.class);
    	ticket.setSessionId(5);
    	ticket.setSeat(96);
    	
    	Mockito.when(tDao.isCreated(ticket)).thenReturn(created);
		Assert.assertTrue(created);
 	}
    
    @Test
	public void createTicket() {
    	tDao = Mockito.mock(TicketDAO.class);
		Mockito.when(tDao.createTicket(ticket)).thenReturn(created);
		Assert.assertTrue(created);
 	}
    
    @Test
   	public void removeTicket() {
    	Ticket t = Mockito.mock(Ticket.class);
    	tDao = Mockito.mock(TicketDAO.class);
       	
   		Mockito.when(tDao.removeTicket(t)).thenReturn(remove);
   		Assert.assertTrue(remove);
    }
  
    @Test
   	public void getAllTickets() {
    	int value = 99;
       	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getAllTickets(value)).thenReturn(ticketArray);
   		Assert.assertNull(ticketArray);
    }
    
    @Test
   	public void getTicketCount() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getTicketCount(token)).thenReturn(tickets);
   		Assert.assertEquals(11, tickets);
    }
    
    
    @Test
   	public void getBookedUnpaidTickets() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getBookedUnpaidTickets(token)).thenReturn(tList);
   		Assert.assertEquals(0, tList.size());
    }
    
    @Test
   	public void removeFromInvoice() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.removeFromInvoice(ticket)).thenReturn(removed);
   		Assert.assertTrue(removed);
    }
    
    @Test
   	public void purgeAllUnpaidTickets() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.doNothing().when(tDao).purgeAllUnpaidTickets(token);
    }
    
    @Test
   	public void setUserId2pay() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.setUserId2pay(user, token)).thenReturn(updated);
   		Assert.assertTrue(updated);
    }
    
    @Test
   	public void getTodaysSum() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getTodaysSum(user, token)).thenReturn(sum);
   		Assert.assertEquals(sum, 1.23);   	
    }
    
    @Test
   	public void clearAllUnpaid() {
    	tDao = Mockito.mock(TicketDAO.class);
    	Mockito.doNothing().when(tDao).clearAllUnpaid();
    }
    
    @Test
   	public void updateMovieSessionAvailableSeats() {
    	tDao = Mockito.mock(TicketDAO.class);
    	Mockito.doNothing().when(tDao).updateMovieSessionAvailableSeats(movieSession, seats);
   		
    }
    
    @Test
   	public void getBookedSeats() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getBookedSeats(movieSession)).thenReturn(seats);
   		Assert.assertEquals(seats, 22);
   		
    }
    
    
    @Test
   	public void getTicket() {
    	tDao = Mockito.mock(TicketDAO.class);
    	ticket = Mockito.spy(Ticket.class);
   		Mockito.when(tDao.getTicket(ticketId, token)).thenReturn(ticket);
   		Assert.assertNotNull(ticket);   		
    }
    
    
    @Test
   	public void getIncomes() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getIncomes(start, end)).thenReturn(sum);
   		Assert.assertEquals(sum, 1.23);   		
    }
    
    @Test
   	public void getTicketsBought() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getTicketsBought(start, end)).thenReturn(tickets);
   		Assert.assertEquals(tickets, 11);   		
    }
    
    @Test
   	public void getAveTicketPrice() {
    	tDao = Mockito.mock(TicketDAO.class);
   		Mockito.when(tDao.getAveTicketPrice(start, end)).thenReturn(sum);
   		Assert.assertEquals(sum, 1.23);   		
    }   
}
