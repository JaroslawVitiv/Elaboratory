<%@page import="com.busvancar.cinema.Movies"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checking tickets availability</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
	.grid-container {
		
	  display: grid;
	  grid: 12% / ${autos};
	  grid-gap: 4px;
	  padding: 4px;
	  justify-content:center;
	}
	
	a{
		margin:30px;
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
		font-size:30px;
		color:#33ecff;
		padding:20px;
	}
	
	img{
		margin:30px;
		float: right;
	}
	
	#cart {
	   	 position: fixed;
		 top:5%;
		 right:15%;
		 z-index: 1;
	}
</style>

<script>
	var tickets = [];
	var count = 0;
	function add2cart(seat) {
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("cinemaHall").innerHTML = this.responseText;
		    }
		  };
		  xhttp.open("GET", "/cinema/generator?seat="+seat+"&movie_session="+<%= request.getParameter("movie_session") %> , true);
		  xhttp.send();
	}
</script>


</head>
<body>
<div class="container">
	<nav  class="navbar navbar-light navbar-expand-lg" style="background-color: #e3f2fd;">
		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
	    	<a  class="btn btn-lg btn-outline-info" href="/cinema">
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
		

	    	
	        <%= request.getAttribute("logingBoard") %>
	 
			<div id="cart">
				<a class="btn btn-lg btn-outline-danger ">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart3" viewBox="0 0 16 16">
 						 <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .49.598l-1 5a.5.5 0 0 1-.465.401l-9.397.472L4.415 11H13a.5.5 0 0 1 0 1H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l.84 4.479 9.144-.459L13.89 4H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
					</svg>
					  my cart (<span id="ticketsCount" >0</span> tickets)
					</a>
			</div>
			
		</div>
	</nav>
<hr/>



<div class="alert alert-primary text-center " role="alert">
  <h2>Наявність квитків на сеанс / Ticket availability</h2>
</div>


<div id="invoice">Invoice</div>
<div> <h2>${message}</h2></div>

		<div class="container-fluid">
			<div class="col-sm-12">
				<%= request.getAttribute("cinemaHall") %>
			</div>
		</div>
</div>








</body>
</html>