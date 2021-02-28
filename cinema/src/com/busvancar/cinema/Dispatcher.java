package com.busvancar.cinema;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dispatcher {

	public void dispatch(User user, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(user!=null &&  user.getAdmin()>0) {
			response.sendRedirect(request.getContextPath() + "/administration");
		} else {	
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}	
	}
}
