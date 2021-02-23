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

import com.busvancar.cinema.User;

import static org.mockito.Matchers.anyInt;

import junit.framework.Assert;

public class UserTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock  private int id;
	 @Mock	private String firstName;
	 @Mock	private String lastName;
	 @Mock	private String email;
	 @Mock	private String password;
	 @Mock	private int admin;
	 @Mock	private double revenue;
	 @Mock	private long generatedLong;
	 @Mock  private User user;


	
	 
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
	void setId() {
    	user = new User();
    	int value = anyInt();
    	user.setId(value);
    	Assert.assertEquals(user.getId(), value);
 	}
    
    @Test
   	void getId() {
    	user = new User();
    	int value = anyInt();
       	user.setId(value);
       	Assert.assertEquals(user.getId(), value);
    }
    
    @Test
    public void setFirstName() {
    	user = new User();
    	String value = "FirstName";
    	user.setFirstName(value);
    	Assert.assertEquals(user.getFirstName(), value);
	}
    
    @Test
	public void getFirstName() {
    	user = new User();
    	String value = "FirstName";
    	user.setFirstName(value);
    	Assert.assertEquals(user.getFirstName(), value);
	}
    
    @Test
    public void setLastName() {
    	user = new User();
    	String value = "LastName";
    	user.setLastName(value);
    	Assert.assertEquals(user.getLastName(), value);
	}
    
    @Test
	public void getLastName() {
    	user = new User();
    	String value = "LastName";
    	user.setLastName(value);
    	Assert.assertEquals(user.getLastName(), value);
	}
    
    @Test
    public void setEmail() {
    	user = new User();
    	String value = "Email";
    	user.setEmail(value);
    	Assert.assertEquals(user.getEmail(), value);
	}
    
    @Test
	public void getEmail() {
    	user = new User();
    	String value = "Email";
    	user.setEmail(value);
    	Assert.assertEquals(user.getEmail(), value);
	}
    
    @Test
    public void setAdmin() {
    	user = new User();
    	int value = anyInt();
    	user.setAdmin(value);
    	Assert.assertEquals(user.getAdmin(), value);
	}
    
    @Test
	public void getAdmin() {
    	user = new User();
    	int value = anyInt();
    	user.setAdmin(value);
    	Assert.assertEquals(user.getAdmin(), value);
	}
    
    @Test
   	public void setPassword() {
    	user = new User();
    	String value = "Password";
       	user.setPassword(value);
       	Assert.assertEquals(user.getPassword(), value);
    }
    
    @Test
    public void getPassword() {
    	user = new User();
    	String value = "Password";
    	user.setPassword(value);
    	Assert.assertEquals(user.getPassword(), value);
	}
    
    @Test
   	public void setRevenue() {
    	user = new User();
    	double value = 1.23;
    	user.setRevenue(value);
       	Assert.assertEquals(user.getRevenue(), value);
    }
    
    @Test
    public void getRevenue() {
    	user = new User();
    	double value = 1.23;
    	user.setRevenue(value);
    	Assert.assertEquals(user.getRevenue(), value);
	}
    
    @Test
   	public void setConfirmationCode() {
    	user = new User();
    	long value = 100L;
    	user.setConfirmationCode(value);
       	Assert.assertEquals(user.getConfirmationCode(), value);
    }
    
    @Test
   	public void getConfirmationCode() {
    	user = new User();
    	long value = 100L;
    	user.setConfirmationCode(value);
       	Assert.assertEquals(user.getConfirmationCode(), value);
    }
}
