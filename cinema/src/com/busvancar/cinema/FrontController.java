package com.busvancar.cinema;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController {
	
	private Dispatcher dispatcher;

	   public FrontController(){
	      dispatcher = new Dispatcher();
	   }

	   private boolean isAuthenticUser(){
	      System.out.println("User is authenticated successfully.");
	      return true;
	   }

	   private void trackRequest(HttpServletRequest req){
	      System.out.println("Page requested: " + req.getLocalName());
	   }

	   public void dispatch(User user, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
	      trackRequest(req);
	      
	      if(isAuthenticUser()){
	         dispatcher.dispatch(user, req, res);
	      }	
	   }

}
