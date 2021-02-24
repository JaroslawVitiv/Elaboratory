package test;


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

import com.busvancar.cinema.Movie;
import com.busvancar.cinema.MovieDAO;
import com.busvancar.cinema.Ticket;
import com.busvancar.cinema.TicketDAO;
import com.busvancar.cinema.User;
import com.busvancar.cinema.UserDAO;

import static org.mockito.Matchers.anyInt;

import junit.framework.Assert;

public class UserDAOTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock  private User user = new User();
	 @Mock private String email = "email";
	 @Mock private String password = "password";
	 @Mock private String input = "input";
	 @Mock private boolean updated = true;
	 @Mock private List<User> uList = new ArrayList<>();
	 @Mock private LocalDate start = LocalDate.of(2007, 11, 12);
	 @Mock private LocalDate end = LocalDate.of(2007, 12, 12);



	 

	 
	@Before
    public void setup() {
		MockitoAnnotations.initMocks(this);
		logger.info("startup - creating DB connection");
    }

    @After
    public void tearDown() {
    	user = null;
        logger.info("closing DB connection");
    }

    @Test
	public void findUser() {
    	UserDAO uDao = Mockito.mock(UserDAO.class);
    	User u = Mockito.mock(User.class);
		Mockito.when(uDao.findUser(email, password)).thenReturn(u);
		
		Assert.assertNotNull(u);
 	}
    
    @Test
   	public void getMd5() {
       	UserDAO uDao = Mockito.mock(UserDAO.class);
       	
   		Mockito.when(uDao.getMd5(input)).thenReturn(email);
   		
   		Assert.assertNotNull(email);
    }
    
    @Test
   	public void isValid() {
       	UserDAO uDao = Mockito.mock(UserDAO.class);
        Mockito.when(uDao.isValid(email)).thenReturn(updated);
   		Assert.assertTrue(updated);
    }
    
    @Test
   	public void isInserted() {
       	UserDAO uDao = Mockito.mock(UserDAO.class);
       	Mockito.when(uDao.isInserted(email)).thenReturn(updated);
   		Assert.assertTrue(updated);
    }
    
    
    @Test
   	public void insertUser() throws SQLException {
    	User us = Mockito.mock(User.class);
    	UserDAO uDao = Mockito.mock(UserDAO.class);
       	Mockito.when(uDao.insertUser(us)).thenReturn(updated);
   		Assert.assertTrue(updated);
    }
     
    
    @Test
   	public void updatePassword() {
       	UserDAO uDao = Mockito.mock(UserDAO.class);
       	Mockito.when(uDao.updatePassword(email, password)).thenReturn(updated);
   		Assert.assertTrue(updated);
    }
    
    @Test
   	public void getTopKeyCustomers() throws SQLException {
    	int limit = 11;
       	UserDAO uDao = Mockito.mock(UserDAO.class);
       	Mockito.when(uDao.getTopKeyCustomers(limit, start, end)).thenReturn(uList);
   		Assert.assertEquals(0, uList.size());
    }
    
    @Test
   	public void getUser() throws SQLException {
    	Ticket ticket = Mockito.mock(Ticket.class);
       	UserDAO uDao = Mockito.mock(UserDAO.class);
       	Mockito.when(uDao.getUser(ticket)).thenReturn(user);
   		Assert.assertNotNull(user);
   }
    
    @Test
   	public void confirm() {
       	UserDAO uDao = Mockito.mock(UserDAO.class);
       	Mockito.when(uDao.confirm(user)).thenReturn(updated);
   		Assert.assertTrue(updated);
    }
}
