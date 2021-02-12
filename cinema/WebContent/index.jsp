<%@page import="com.busvancar.cinema.Movies"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import = "java.io.*, java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>

<%
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

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VitivCinema</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
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
		font-size:30px;
		color:#33ecff;
		padding:20px;
	}
	a{
		margin:30px;
	}
	
	#greeting{
		font-size:30px;
		 font-family: "Sofia", sans-serif;
	}
	.languages{
	
		font-size:30px;
		font-family: "Sofia", sans-serif;
		text-align:right;
	}
</style>
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
			
	        
	        <div>
	          <c:set var = "user" scope = "session" value = "${user}"/>
	      		<c:if test = "${user.email != null}">
	         		<div><span id="greeting"><%  out.print(hello); %>, <c:out value = "${user.firstName}"/>!</span> <a class="btn btn-lg btn-outline-info" href="logout"><%  out.print(logout); %></a> | <a class="btn btn-lg btn-outline-info" href="changePassword.jsp"><%  out.print(changePassword); %></a>
						<c:if test = "${user.admin > 0}">
			         		 | <a class="btn btn-lg btn-outline-info" href="administration"><%  out.print(managerboard); %></a>
			     		</c:if>     
			     	</div>	
	     		</c:if>
		        <c:if test = "${user.email == null}" >
	         		<a class="btn btn-lg btn-outline-info" href="signin.jsp"><%  out.print(signin); %></a> | <a class="btn btn-lg btn-outline-info" href="login.jsp"><%  out.print(login); %></a>
	     		</c:if>
	        </div>
	 	</div>
	</nav>
<hr/>

  <div class="alert alert-success"> <%  out.print(greeting); %>
 	 <c:set var = "language" scope = "session" value = "${l10n}" />
  		<div class="languages">
      		<c:if test = "${l10n == 'uk_UA'}">
      		     Українська  <a class="btn btn-md btn-info" href="/cinema/?language=en_GB"> English </a>
      		</c:if>  
  			<c:if test = "${l10n == 'en_GB'}">
  				  	<a class="btn btn-md btn-info" href="/cinema/?language=uk_UA"> Українська </a>  English
      		</c:if>
      	</div>
  </div>
<hr/>

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
					<div><%  out.print(genre); %> : <c:out value="${movie.movieGenre}"/> </div>
					<div><%  out.print(datetime); %> : <b><fmt:formatDate value="${movie.dateTime}" pattern="dd.MM.yyyy (HH:mm)" /></b></div>
					<div> <%  out.print(price); %>: <c:out value="${movie.price}"/> $$ </div>
					<hr/>
					
					<div> <a class="btn btn-outline-danger" href="availability?movie_session=<c:out value="${movie.sessionId}"/>"><%  out.print(checkavailability); %></a></div>
				</div>
			</div>
			<hr/>
		</c:forEach>
		
</div>
</body>
</html>