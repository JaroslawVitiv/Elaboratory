<%@page import="com.busvancar.cinema.Movies"%> 
<%@page import="com.busvancar.cinema.User"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>
<%@ page import = "com.busvancar.cinema.Genre"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import = "java.io.*, java.util.*, java.time.*" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>
<%
String genreDropdownMenu_en_GB = Genre.getGenreOptions(Genre.genres_en_GB);
String genreDropdownMenu_uk_UA = Genre.getGenreOptions(Genre.genres_uk_UA);



Locale locale = new Locale((String) session.getAttribute("l10n"));
ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
String ticketAvailability = rb.getString("ticketAvailability");
String greeting = rb.getString("greeting");
String signin = rb.getString("signin");
String hello = rb.getString("hello");
String logout = rb.getString("logout");
String login = rb.getString("login");
String managerboard = rb.getString("managerboard");
String changePassword = rb.getString("changePassword");
String title = rb.getString("title");
String description = rb.getString("description");
String duration =  rb.getString("duration");
String genre =  rb.getString("genre");
String datetime  = rb.getString("datetime");
String price =  rb.getString("price");
String checkavailability = rb.getString("checkavailability");
String mins = rb.getString("mins");
String myAccount = rb.getString("myAccount");
String sort = rb.getString("sort");
String byDate = rb.getString("byDate");
String byPrice = rb.getString("byPrice");
String bySeats = rb.getString("bySeats");
String asc = rb.getString("asc");
String desc = rb.getString("desc");
String available = rb.getString("available");
String availableSeats = rb.getString("availableSeats");
String enterMovieTitle = rb.getString("enterMovieTitle");
String thisWeek = rb.getString("thisWeek");
String nextWeek = rb.getString("nextWeek");
String previousWeek = rb.getString("previousWeek");
String ofTickets = rb.getString("ofTickets");



ServletContext sc = this.getServletContext();
int currentUsers = 0;
if(sc.getAttribute("currentUsers")!=null){
 	 currentUsers = (Integer)sc.getAttribute("currentUsers");
}
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
	.grid-container {
	  display: grid;
	  grid: 12% / ${autos};
	  grid-gap: 4px;
	  padding: 4px;
	  justify-content:center;
	}
	
	a{
		margin-right:15px;
		margin-left:15px;
		margin-top:3px;
		margin-bottom:3px;
	}
	
	.grid-container > div {
	  text-align: center;
	  font-size: 30px;
	  padding: 2px;
	}
	#screen-container{
		display: flex;
		flex-wrap: wrap;
		align-content:center;
		justify-content: center;
		box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
	}
	#screen {
		display: flex;
		flex-wrap: wrap;
		width: 50%;
        min-height: 20%;
        clip-path: polygon(0 0, 100% 0, 71% 100%, 30% 100%);
        height: 80px;
	    background: #33ecff;
	    position: relative;
	    color:white;
	    align-content:center;
		justify-content: center;
		text-shadow: 2px 2px 5px grey;
	    
	}
	#logo{
		font-size:23px;
		color:#33ecff;
		padding:8px;
	}
	
	img{
		margin:30px;
		float: right;
	}
	
	#cart {
	   	 position: fixed;
		 bottom:3%;
		 right:15%;
		 z-index: 1;
	}
	.languages{
		padding-right:30px;
		font-size:20px;
		font-family: "Sofia", sans-serif;
		text-align:right;
	}
</style>

<script>
	function add2cart(seat) {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("cinemaHall").innerHTML = this.responseText;
		      getTicketAmount();
		    }
		  };
		  xhttp.open("GET", "/cinema/generator?seat="+seat+"&movie_session="+<%= request.getParameter("movie_session") %> , true);
		  xhttp.send();
		 
	}
	
	
	function getTicketAmount() {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("ticketsCount").innerHTML = this.responseText;
		    }
		  };
		xhttp.open("GET", "/cinema/ticketCounter?session_token=${session_token}" , true);
		xhttp.send();		
	}
	
	function getInvoice() {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("invoice").innerHTML = this.responseText;
		    }
		  };
		xhttp.open("GET", "/cinema/invoice?session_token=${session_token}" , true);
		xhttp.send();		
	}
	
	function removeTicket(ticketNumber) {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		       document.getElementById("invoice").innerHTML = this.responseText;
		       getTicketAmount();
		       refreshCinemaHall();
		    }
		  };
		xhttp.open("GET", "/cinema/ticketremover?ticket_id="+ticketNumber+"&session_token=${session_token}" , true);
		xhttp.send();		
	}
	
	
	
	function refreshCinemaHall() {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("cinemaHall").innerHTML = this.responseText;
		      
		    }
		  };
		  xhttp.open("GET", "/cinema/cinemahall?movie_session="+<%= request.getParameter("movie_session") %> , true);
		  xhttp.send();
		 
	}
	
	function purgeAllUnpaid() {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	 refreshCinemaHall();
		      document.getElementById("invoice").innerHTML = "You removed all chosen tickets";
		      document.getElementById("ticketsCount").innerHTML = "0";
		    }
		  };
		  xhttp.open("GET", "/cinema/purgeAllUnpaid?session_token=${session_token}" , true);
		  xhttp.send();
		 
	}
	
	function payInvoice() {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	 refreshCinemaHall();
		      document.getElementById("invoice").innerHTML = this.responseText;
		      document.getElementById("ticketsCount").innerHTML = "0";
		    }
		  };
		  xhttp.open("GET", "/cinema/payInvoice?session_token=${session_token}" , true);
		  xhttp.send();
		 
	}
	
	function openModal(){
		$(document).ready(function(){
		    $('.modal').modal('show');
		});
	}
	
	function closeModal(){
		$(document).ready(function(){
		    $('.modal').modal('hide');
		});
	}
</script>


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
		
	           <c:set var = "user" scope = "session" value = "${user}"/>
	      		<c:if test = "${user.email != null}">
	         		<div>
	         			<span id="greeting"><%  out.print(hello); %>, <c:out value = "${user.firstName}"/>!</span>
	         			
	         			<!--  <a class="btn btn-md btn-outline-info" href="changePassword.jsp"><%  out.print(changePassword); %></a> --->
						 <a class="btn btn-md btn-outline-info" href="account"><%  out.print(myAccount); %></a> 
						 <a class="btn btn-md btn-outline-info" href="logout"><%  out.print(logout); %></a> 
			     		     
			     	</div>	
	     		</c:if>
		        <c:if test = "${user.email == null}" >
	         		<a class="btn btn-md btn-outline-info" href="signin.jsp"><%  out.print(signin); %></a> <a class="btn btn-md btn-outline-info" href="login.jsp"><%  out.print(login); %></a>
	     		</c:if> 
	     		<span class="languages"> <%  out.print(greeting); %> </span> 
		     		

			<div>  (<% out.print(currentUsers); %>)
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
			  <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
			</svg>
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
			  <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
			  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
			</svg>
			</div>
        </div>
	</nav>
<hr/>

<br><br><br>

<div class="alert alert-primary text-center " role="alert">
  <h2><% out.print(ticketAvailability); %></h2>
</div>


<div> <h2>${message}</h2></div>
		<div class="container-fluid">
			<div class="col-sm-12">
				${cinemaHall}
			</div>
		</div>
</div>

<div id=cart>
	<a class="btn btn-outline-danger" onclick="openModal(); getInvoice();" >
		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart3" viewBox="0 0 16 16">
 			 <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .49.598l-1 5a.5.5 0 0 1-.465.401l-9.397.472L4.415 11H13a.5.5 0 0 1 0 1H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l.84 4.479 9.144-.459L13.89 4H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
		</svg>
		(<span id="ticketsCount">0</span>) <% out.print(ofTickets); %>
	</a>
</div>


<div class="container">
	<!-- Button trigger modal -->

<!-- Modal -->
<div class="modal fade" id="ticketsCartModalLong" tabindex="-1" role="dialog" aria-labelledby="ticketsCartModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ticketsCartModalLongTitle">My tickets</h5>
        <button type="button" data-dismiss="modal" onclick="closeModal();" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div id="invoice">No tickets found</div>
      
      <hr/>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" onclick="payInvoice();"  data-dismiss="modal"> Pay my order</button>
        <button type="button" class="btn btn-secondary" onclick="closeModal();" data-dismiss="modal">No, thanks!</button>
         <button type="button" class="btn btn-danger" onclick="purgeAllUnpaid();" data-dismiss="modal">Remove all</button>
      </div>
    </div>
  </div>
</div>

</div>


</body>
</html>