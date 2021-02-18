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
 String genreOptionsUa = Genre.getGenreSelectOptions(Genre.genres_uk_UA);
 String genreOptionsEn = Genre.getGenreSelectOptions(Genre.genres_en_GB);
 LocalDate currentdate = LocalDate.now();
 
 
 String redirect="";
 User user = (User) session.getAttribute("user"); 
 if(user.getAdmin()<1 || user == null){
		redirect = "<script language=\"javascript\" type=\"text/javascript\">window.location=\"/cinema\" </script>";
}
 Locale locale = new Locale((String) session.getAttribute("l10n"));
 ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
 String greeting = rb.getString("greeting");
 String hello = rb.getString("hello");
 String managerboard = rb.getString("managerboard");
 String homePage =  rb.getString("homePage");
 String addAnewMovie = rb.getString("addAnewMovie");
 String title = rb.getString("title");
 String description = rb.getString("description");
 String duration = rb.getString("duration");
 String mins = rb.getString("mins");
 String basicPrice = rb.getString("basicPrice");
 String addPoster = rb.getString("addPoster");







 ServletContext sc = this.getServletContext();
 int currentUsers = 1;
 if(sc.getAttribute("currentUsers")!=null){
  	 currentUsers = (Integer)sc.getAttribute("currentUsers");
 }
  %>
<!DOCTYPE html>
<html>
<head>
<%= redirect %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Adding a new film</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.x-git.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">
<style type="text/css">
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
	 	grid-template-columns: auto auto auto auto ;
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
					VitivCinema <%  out.print(managerboard);%>
				  </span>
			</a>
		
	           <c:set var = "user" scope = "session" value = "${user}"/>
	      		<c:if test = "${user.email != null}">
	         		<div>
	         			<span id="greeting"><%  out.print(hello); %>, <c:out value = "${user.firstName}"/>!</span>
	         			
	         			 <a class="btn btn-md btn-outline-info" href="administration"><%  out.print(homePage); %></a>
			     	</div>	
	     		</c:if>
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
	        </div>
	</nav>
<br/><br/><br/>
	<div >
		<div>
			<% out.print(addAnewMovie); %>
			<hr/>
		</div>
		<form class="grid" accept-charset="UTF-8" action="addmovie" method="post"  enctype="multipart/form-data">
			<div>
				<input placeholder="<% out.print(title); %>" type="text" name="title" id="title">
			</div>
			<div>
				<textarea cols="30" rows="7" placeholder="<% out.print(description); %> (in English)"  name="descriptionEn" id="descriptionEn"></textarea>
			</div>
			<div>
				<textarea cols="30" rows="7" placeholder="<% out.print(description); %> (на українській)"  name="descriptionUk" id="descriptionUk"></textarea>
			</div>
			<div>
				<h6><% out.print(duration); %> (<% out.print(mins); %>)</h6>
				<input placeholder="<% out.print(duration); %> (<% out.print(mins); %>)" type="number" name="duration" id="duration" min="0" value="0"/>
			</div>
			<div>
				<select name="genre" id="genre">
					<c:if test = "${l10n == 'uk_UA'}">
					   	 <% out.print(genreOptionsUa); %>
					</c:if>  
					<c:if test = "${l10n == 'en_GB'}">
					  	 <% out.print(genreOptionsEn); %>
					</c:if>
				</select>
			</div>
			<div>
				<h6><% out.print(basicPrice); %> ($$)</h6>
				<input type="text" placeholder="00.00" name="price" id="price"  required/>
			</div>
			<div>
				<h6><% out.print(addPoster); %> </h6>
					<img id="picPreview" width="500px"/>
				
				<input type="file" style="display: none" name="poster" id="poster" />
				<input type="hidden" name="currentImage" value="${movie.poster}"/> 
			<input type="button" class="btn btn-outline-info btn-md" value="<% out.print(addPoster); %>" id="fakeBrowse" onclick="HandleBrowseClick();"/>

			<script>
			function readURL(input) {
			        if (input.files && input.files[0]) {
				            var reader = new FileReader();
				            reader.onload = function (e) {
				      	    $('#picPreview').attr('src', e.target.result);
			            }
			            
			            reader.readAsDataURL(input.files[0]);
			        }
			    }
			    
			    $("#poster").change(function(){
			        readURL(this);
			    });
			
			function HandleBrowseClick()
			{
			    var fileinput = document.getElementById("poster");
			    fileinput.click();
			}
			
			function Handlechange()
			{
			    var fileinput = document.getElementById("poster");
			    var textinput = document.getElementById("filename");
			    textinput.value = fileinput.value;
			
				if(textinput.value!=''){
					$('#choosingButton').val(' ');
				}
			}
			
			</script>
			</div>
			<div>
				<input type="submit" value="OK">
			</div>
		</form>
	
	</div>

</div>
</body>

</html>