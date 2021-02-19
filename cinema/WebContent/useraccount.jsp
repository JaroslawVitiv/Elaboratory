<%@page import="com.busvancar.cinema.Movies"%> 
<%@page import="com.busvancar.cinema.User"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import = "java.io.*, java.util.*, java.time.*" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>
<%@ page import = "com.busvancar.cinema.Genre"%>
<%
Locale locale = new Locale((String) session.getAttribute("l10n"));
ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
String greeting = rb.getString("greeting");
String changePassword = rb.getString("changePassword");
String hello = rb.getString("hello");
String homePage = rb.getString("homePage");
String download = rb.getString("download");
String movie = rb.getString("movie");
String datetime = rb.getString("datetime");
String price = rb.getString("price");
String duration = rb.getString("duration");
String mins = rb.getString("mins");
String row = rb.getString("row");
String seat = rb.getString("seat");






%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checking tickets availability</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.min.js"></script>
<style>
  .movie {
 	 display: flex;
 	  flex-direction: row;
 	  padding: 10px;
 	   flex-wrap: nowrap;
 	   justify-content: space-start;
 	   margin:10px;
  }
  
   .description {
 	 	display: flex;
 	   flex-direction: row;
 	    padding: 10px;
 	   flex-wrap: wrap;
 	   justify-content: space-start;
 	   margin:10px;
 	   flex-direction: column; 
  }
  #logo{
		font-size:23px;
		color:#33ecff;
		padding:8px;
	}
	a{
		margin-right:15px;
		margin-left:15px;
		margin-top:3px;
		margin-bottom:3px;
		
	}
	
	#greeting{
		font-size:15px;
		 font-family: "Sofia", sans-serif;
	}
	.languages{
	
		font-size:20px;
		font-family: "Sofia", sans-serif;
		text-align:right;
	}

	.grid{
	 	display: grid;
	 	grid-template-columns: auto auto  ;
  		padding: 10px;
	}
	
	
	
	.form-inline{
		display:flex;
		flex-direction: row; 
		padding-right:10px;
		padding-left:10px;
		
	}
	.dropdown-item{
		width:220px;
	}
	.message{
		font-size:50px;
	    font-family: cursive;
		text-align:center;
		color:maroon
	}
</style>

</head>
<body>
	<div class="container">
	<nav  class="navbar fixed-top navbar-light navbar-expand-lg" style="background-color: #e3f2fd;">
		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
	    	<a  class="navbar-brand" href="/cinema">
	    	  <span id="logo">  
	    	 	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-film" viewBox="0 0 16 16">
  					<path d="M0 1a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1V1zm4 0v6h8V1H4zm8 8H4v6h8V9zM1 1v2h2V1H1zm2 3H1v2h2V4zM1 7v2h2V7H1zm2 3H1v2h2v-2zm-2 3v2h2v-2H1zM15 1h-2v2h2V1zm-2 3v2h2V4h-2zm2 3h-2v2h2V7zm-2 3v2h2v-2h-2zm2 3h-2v2h2v-2z"/>
				</svg>
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-camera-reels-fill" viewBox="0 0 16 16">
  					<path d="M6 3a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
  					<path d="M9 6a3 3 0 1 1 0-6 3 3 0 0 1 0 6z"/>
 					<path d="M9 6h.5a2 2 0 0 1 1.983 1.738l3.11-1.382A1 1 0 0 1 16 7.269v7.462a1 1 0 0 1-1.406.913l-3.111-1.382A2 2 0 0 1 9.5 16H2a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h7z"/>
				</svg>
					VitivCinema
				  </span>
			</a>
		
	       	<div>
				<span id="greeting"><%  out.print(hello); %>, <c:out value = "${user.firstName}"/>!</span>
				 <a class="btn btn-md btn-outline-info" href="changePassword.jsp"><%  out.print(changePassword); %></a> 
				 <a class="btn btn-md btn-outline-info" href="/cinema"><%  out.print(homePage); %></a> 
	     	</div>	
	     		
	   		<span class="languages"> <%  out.print(greeting); %>	</span>
			
         </div>
	</nav>
	<br><br><br><br>
	<hr/>
	
	<div class="grid">
		<c:forEach var="ticket" items="${userOutput}">
			<div>
				<div style="border: 1px solid grey;  border-radius: 5px;">
				
					<div><% out.print(movie); %>: <b>${ticket.movieTitle}</b> |  <% out.print(price); %>: <b>${ticket.price}</b> $$</div>
					<div><% out.print(datetime); %>: <b><fmt:formatDate value="${ticket.time}" pattern="dd.MM.yyyy (HH:mm)" /></b> | <% out.print(duration); %>: <b>${ticket.movieDuration}</b> <% out.print(mins); %></div>
					<div> <% out.print(row); %>: <b>${ticket.row}</b> | <% out.print(seat); %>: <b><c:out value="${ticket.seat}"/></b></div>
					<div style="text-align: right; padding:10px;"><p>
					<a class="btn btn-danger btn-sm" href="cinematicket?ticketId=${ticket.ticketId}&sessionToken=${ticket.sessionToken}"><% out.print(download); %> .pdf</a></p></div>
					
				</div>
			</div>
			
		</c:forEach>
	</div>
	
	
</div>
</body>
</html>