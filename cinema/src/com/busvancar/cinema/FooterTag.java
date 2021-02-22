package com.busvancar.cinema;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

public class FooterTag extends SimpleTagSupport{
	public void doTag() throws IOException {
		JspWriter out = getJspContext().getOut();
		out.print("<footer style=\"color:white; background-color:lightblue; padding:80px; font-size:30px; \"> VitivCinema +380662551690 All rights reserved. 2021</footer>");
	}

}
