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
String genreDropdownMenu_en_GB = Genre.getGenreOptions(Genre.genres_en_GB);
String genreDropdownMenu_uk_UA = Genre.getGenreOptions(Genre.genres_uk_UA);
LocalDate currentdate = LocalDate.now();

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
String enterMovieTitle = rb.getString("enterMovieTitle");
String thisWeek = rb.getString("thisWeek");
String nextWeek = rb.getString("nextWeek");
String previousWeek = rb.getString("previousWeek");



ServletContext sc = this.getServletContext();
int currentUsers = 1;
if(sc.getAttribute("currentUsers")!=null){
 	 currentUsers = (Integer)sc.getAttribute("currentUsers");
}
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

 <script type="text/javascript">
 	function searching(){
 		if(document.getElementById("search").value.length > 2){
 		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("grid").innerHTML = this.responseText;
		      getTicketAmount();
		    }
		  };
		  xhttp.open("GET", "/cinema/search?request="+document.getElementById("search").value, true);
		  xhttp.send();
 		}
 	}
 </script>
     
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
	
	
	
	
.center-block {
    float: none;
    margin-left: auto;
    margin-right: auto;
}

.input-group .icon-addon .form-control {
    border-radius: 0;
}

.icon-addon {
    position: relative;
    color: #555;
    display: block;
}

.icon-addon:after,
.icon-addon:before {
    display: table;
    content: " ";
}

.icon-addon:after {
    clear: both;
}

.icon-addon.addon-md .glyphicon,
.icon-addon .glyphicon, 
.icon-addon.addon-md .fa,
.icon-addon .fa {
    position: absolute;
    z-index: 2;
    left: 10px;
    font-size: 14px;
    width: 20px;
    margin-left: -2.5px;
    text-align: center;
    padding: 5px 0;
    top: 1px
}

.icon-addon.addon-md .form-control,
.icon-addon .form-control {
    padding-left: 30px;
    float: left;
    font-weight: normal;
}


.icon-addon .form-control:focus + .glyphicon,
.icon-addon:hover .glyphicon,
.icon-addon .form-control:focus + .fa,
.icon-addon:hover .fa {
    color: #2580db;
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
		     		
		     		
		     		
		     		
		      <form class="form-inline" action="/cinema">
		      	 <div class="form-group">
	                <div class="icon-addon addon-md">
	                    <input type="search" placeholder="<% out.print(enterMovieTitle); %>" class="form-control" name="search" id="search" onkeyup="searching();" >
	                    <label for="search" class="glyphicon glyphicon-search" rel="tooltip" title="search">
	                    	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
 								 <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
							</svg> 
	                    </label>
	                </div>
            	</div>
		 	 </form>		
		     		
		     		
		     		
      		
      	
      		

<div class="dropdown">
  <a type="button" class="btn btn-md btn-outline-info  dropdown-toggle"  id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
    
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
	  <span><input type="date" id="date" class="form-control"  value="${chosendate}" min="<%  out.print(currentdate); %>" name="date"   /></span>
	  <span><input type="submit" class="btn btn-md btn-outline-info"  value="OK"></span>
	</form>
</div>

<div>(<% out.print(currentUsers); %>)<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
  <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
</svg>
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
  <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
</svg></div>





        
        
	        </div>
	</nav>
<br/>
<br/>

<hr/>

	<div id="grid">
	  <div class="grid" >
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
					<div><hr/></div>
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
	
		<div class="container">
			${pagination}
			<div>
			   <nav aria-label="Page navigation example" id="pagination">
				   <ul class="pagination">
				   		<li class="page-item"><a class="page-link" ${pWeek} ><< <% out.print(previousWeek); %></a></li>
				   		<li class="page-item"><a class="${page1}" href="/cinema/?page=1"><% out.print(thisWeek); %></a></li>
				   		<li class="page-item"><a class="${page2}" href="/cinema/?page=2">+1</a></li>  
				   		<li class="page-item"><a class="${page3}" href="/cinema/?page=3">+2</a></li>   
				   		<li class="page-item"><a class="${page4}" href="/cinema/?page=4">+3</a></li>  
				   		<li class="page-item"><a class="${page5}" href="/cinema/?page=5">+4</a></li> 
				   		<li class="page-item"><a class="${page6}" href="/cinema/?page=6">+5</a></li>  
				   		<li class="page-item"><a class="${page7}" href="/cinema/?page=7">+6</a></li> 
				   		<li class="page-item"><a class="${page8}" href="/cinema/?page=8">+7</a></li>  
				   		<li class="page-item"><a class="${page9}" href="/cinema/?page=9">+8</a></li> 	
				   		<li class="page-item"><a class="${page10}" href="/cinema/?page=10">+9</a></li> 		
				   		<li class="page-item"><a class="page-link" ${nWeek}><% out.print(nextWeek); %> >></a></li> 		
				   		
				   		 		
				   </ul>
				</nav>
			</div>	
			
			<div class="message">${message}</div>
		
		</div>
</div>	


</div>
</body>
</html>