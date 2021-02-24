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

import com.busvancar.cinema.AddmovieServlet;
import com.busvancar.cinema.CheckAvailabilityServlet;
import com.busvancar.cinema.ConnectionPool;
import com.busvancar.cinema.Movie;
import com.busvancar.cinema.MovieDAO;
import com.busvancar.cinema.Ticket;

import junit.framework.Assert;

public class CheckAvailabilityServletTest {
	private static Logger logger = null;
	 @InjectMocks private Connection jdbcConnection;
	 @Mock private int rows = 3;
	 @Mock private Ticket[] seats = new Ticket[2];
	 @Mock private double price = 100;




	  
	 
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
	void getAutoGrid() throws SQLException  {
    	CheckAvailabilityServlet cas = Mockito.spy(CheckAvailabilityServlet.class);
    	String result =  "auto  auto  auto";
    	Assert.assertEquals(cas.getAutoGrid(rows).trim(), result);
   	  
	}
    
    
    @Test
   	void getSeats() throws SQLException  {
       	CheckAvailabilityServlet cas = Mockito.spy(CheckAvailabilityServlet.class);
       	String result =  "<div  id=\"cinemaHall\"> <div  class=\"grid-container\">   <div><span id=\"seat1\" ><button  onclick=\" bootbox.alert('<h2></h2>'); \" class=\"btn btn-sm btn-success \"   >1 <hr/>100.0 $$</button></span></div>  <div><span id=\"seat2\" ><button  onclick=\" bootbox.alert('<h2></h2>'); \" class=\"btn btn-sm btn-success \"   >2 <hr/>100.0 $$</button></span></div> </div></div>";
       	Assert.assertEquals(cas.getSeats(seats, price), result);
      	  
   	}
  
	
}
