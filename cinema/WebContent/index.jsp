<%@page import="com.busvancar.cinema.Movies"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import = "java.io.*, java.util.*" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>
<%@ page import = "com.busvancar.cinema.Genre"%>
<%
String genreDropdownMenu_en_GB = Genre.getGenreOptions(Genre.genres_en_GB);
String genreDropdownMenu_uk_UA = Genre.getGenreOptions(Genre.genres_uk_UA);


Locale locale = new Locale((String) session.getAttribute("l10n"));
ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
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



%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VitivCinema</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.x-git.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">
  
     
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
 	    padding: 20px;
 	   flex-wrap: wrap;
 	   justify-content: space-start;
 	   margin:20px;
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

<body onload="setTodaysDate();">

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
	     		<span class="languages"> <%  out.print(greeting); %>	</span>
		     		
		     		
		     		
		     		
		      <form class="form-inline">
    			 <span><input class="form-control" type="search" placeholder="Enter movie title" aria-label="Search"></span>
   				 <span>
   				 	 <button class="btn btn-md btn-outline-info" type="submit">
   				 		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
 							 <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
						</svg> 
					</button>
				</span>
 			 </form>		
		     		
		     		
		     		
      		
      	
      		

<div class="dropdown">
  <a type="button" class="btn btn-md btn-outline-info  dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
    
					<c:if test = "${l10n == 'uk_UA'}">
	      		    	 Українська
	      			</c:if>  
	  				<c:if test = "${l10n == 'en_GB'}">
	  				  	English
	      			</c:if>



  </a>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    <li><a class="dropdown-item" href="/cinema/?language=uk_UA">Українська</a></li>
    <li><hr/></li>
    <li><a class="dropdown-item" href="/cinema/?language=en_GB">English</a></li>
  </ul>
</div>
        
        
        
 <div class="dropdown">
  <a class="btn btn-md btn-outline-info  dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
    		
    	<c:choose>  
		    <c:when test="${sortBy == '2'}">  
		        <%  out.print(sort); %>  <%  out.print(bySeats); %> 
		    </c:when>  
		    <c:when test="${sortBy == '1'}">  
		        <%  out.print(sort); %>  <%  out.print(byPrice); %> 
		    </c:when>  
		    <c:otherwise>  
		        <%  out.print(sort); %>  <%  out.print(byDate); %> 
		    </c:otherwise>  
		</c:choose>  
    	
		
		

  </a>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    <li><a class="dropdown-item" href="/cinema/?sortBy=0"><%  out.print(byDate); %> </a></li>
    <li><hr/></li>
    <li><a class="dropdown-item" href="/cinema/?sortBy=1"><%  out.print(byPrice); %> </a></li>
     <li><hr/></li>
    <li><a class="dropdown-item" href="/cinema/?sortBy=2"><%  out.print(bySeats); %> </a></li>
  </ul>
</div>
       
       
       
 <div class="dropdown">
  <a class="btn btn-md btn-outline-info  dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
    	<c:if test = "${ascDesc == '0'}">
	    	<!-- CHEVRON DOWN START-->
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-down" viewBox="0 0 16 16">
		  			<path fill-rule="evenodd" d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
				</svg>
				<!-- CHEVRON DOWN END DESC-->
	     </c:if>  
	  	 <c:if test = "${ascDesc == '1'}">
	  	  <!-- CHEVRON UP START-->
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-up" viewBox="0 0 16 16">
 					 <path fill-rule="evenodd" d="M7.646 4.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1-.708.708L8 5.707l-5.646 5.647a.5.5 0 0 1-.708-.708l6-6z"/>
				</svg>
				<!-- CHEVRON UP END  ASC-->
	     </c:if>
  </a>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    <li>
    	<a class="dropdown-item" href="/cinema/?ascDesc=0">
    			<!-- CHEVRON DOWN START-->
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-down" viewBox="0 0 16 16">
		  			<path fill-rule="evenodd" d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
				</svg>
				<!-- CHEVRON DOWN END-->
				<%  out.print(desc); %>  
		</a></li>
    <li><hr/></li>
    <li>
    	<a class="dropdown-item" href="/cinema/?ascDesc=1">
    		<!-- CHEVRON UP START-->
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-up" viewBox="0 0 16 16">
 					 <path fill-rule="evenodd" d="M7.646 4.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1-.708.708L8 5.707l-5.646 5.647a.5.5 0 0 1-.708-.708l6-6z"/>
				</svg>
				<!-- CHEVRON UP END-->
				<%  out.print(asc); %>  
		</a>
	</li>
  </ul>
</div>
       
       
       
       
        
        
 <div class="dropdown">
  <a class="btn btn-md btn-outline-info  dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
    		
					
	  				  	${genreCategory}
	      			
	
  </a>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
  
  					<c:if test = "${l10n == 'uk_UA'}">
	  				  	<%  out.print(genreDropdownMenu_uk_UA); %> 
	      			</c:if>  
	  				<c:if test = "${l10n == 'en_GB'}">
	  				  	<%  out.print(genreDropdownMenu_en_GB); %> 
	      			</c:if>
  
  		
  </ul>
</div>



<div>
	<form class="form-inline" action="/cinema">
	  <span><input type="date" id="date" class="form-control"  value="${currentdate}" min="${currentdate}" name="date"   /></span>
	  <span><input type="submit" class="btn btn-md btn-outline-info"  value="OK"></span>
	</form>
</div>





        
        
	        </div>
	</nav>
<br/>
<br/>

<hr/>

	<div class="grid">
		<c:forEach var="movie" items="${schedule}">
 			<div class="movie">
				<div>
					<img src="images/<c:out value="${movie.moviePoster}"/>" width="250px" /> 
				</div>
				<div class="description">
					<h1> <%  out.print(title); %>: <c:out value="${movie.movieTitle}"/></h1>
				 	<div><%  out.print(description); %> :  
					<c:if test = "${l10n == 'uk_UA'}">
      		     		<c:out value="${movie.movieDescriptionUa}"/>
      				</c:if>  
  					<c:if test = "${l10n == 'en_GB'}">
  				   		<c:out value="${movie.movieDescriptionEn}"/>
      		  		</c:if>
					 </div>

					<div> <%  out.print(duration); %>: <b> <c:out value="${movie.movieDuration}"/></b>  <%  out.print(mins); %></div>
					<div><%  out.print(genre); %> :
						<c:if test = "${l10n == 'uk_UA'}">
      		     			<c:out value="${movie.movieGenreUa}"/>
      					</c:if>  
  						<c:if test = "${l10n == 'en_GB'}">
  				   			<c:out value="${movie.movieGenre}"/>
      		  			</c:if>
					</div>
					<div><%  out.print(datetime); %> : <b><fmt:formatDate value="${movie.dateTime}" pattern="dd.MM.yyyy (HH:mm)" /></b></div>
					<div> <%  out.print(price); %>: <c:out value="${movie.price}"/> $$ </div>
					<div> <%  out.print(available); %>: <c:out value="${movie.availableSeats}"/> <%  out.print(availableSeats); %> </div>
					
					<hr/>
					
					<div> <a class="btn btn-outline-danger" href="availability?movie_session=<c:out value="${movie.sessionId}"/>"><%  out.print(checkavailability); %></a></div>
				</div>
				<hr/>
			</div>
			
		</c:forEach>
	</div>
		
		<div class="message">${message}</div>
</div>
</body>
</html>