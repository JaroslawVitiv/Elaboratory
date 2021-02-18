<%@ page import = "com.busvancar.cinema.Genre"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import = "java.io.*, java.util.*, java.time.*" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>
<%
request.setAttribute("options", Genre.getGenreList(Genre.genres_uk_UA));


String genreDropdownMenu_en_GB = Genre.getGenreOptions(Genre.genres_en_GB);
String genreDropdownMenu_uk_UA = Genre.getGenreOptions(Genre.genres_uk_UA);
LocalDate currentdate = LocalDate.now();

Locale locale = new Locale((String) session.getAttribute("l10n"));
ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
String editingMovie = rb.getString("editingMovie");
String hello = rb.getString("hello");
String logout = rb.getString("logout");
String login = rb.getString("login");
String managerboard = rb.getString("managerboard");
String changePassword = rb.getString("changePassword");
String homePage = rb.getString("homePage");
String changePoster = rb.getString("changePoster");
String title = rb.getString("title");
String datetime = rb.getString("datetime");
String price = rb.getString("price");
String basicPrice = rb.getString("basicPrice");
String remove = rb.getString("remove");
String genre = rb.getString("genre");
String download = rb.getString("download");
String duration = rb.getString("duration");
String mins = rb.getString("mins");
String updateInformation = rb.getString("updateInformation");
String addAnewSession = rb.getString("addAnewSession");
String updateMovie = rb.getString("updateMovie");
String update = rb.getString("update");
String close = rb.getString("close");
String sessionPrice = rb.getString("sessionPrice");






ServletContext sc = this.getServletContext();
int currentUsers = 1;
if(sc.getAttribute("currentUsers")!=null){
 	 currentUsers = (Integer)sc.getAttribute("currentUsers");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Editing movie ${title}</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.x-git.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">
<script type="text/javascript">
function openAddSession(){
	$(document).ready(function(){
	    $('#addSessionModal').modal('show');
	});
}

function openUpdating(){
	$(document).ready(function(){
	    $('#updatingModal').modal('show');
	});
}

function closeModal(){
	$(document).ready(function(){
	    $('.modal').modal('hide');
	});
}


</script>

<style type="text/css">
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
	 	grid-template-columns: auto auto auto;
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
	.custom-file-label{
		padding-right:30px;
		padding-left:30px;
		padding-top:6px;
		padding-bottom:6px;
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
				<div>
					(<% out.print(currentUsers); %>)
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



	<br/><br/><br/>
<div class="grid">
	<div>
		<h2><% out.print(editingMovie); %>: <b>${movie.title} (N ${movie.id})</b> </h2>
		<hr>
		<H3>  <b>${views}</b>(<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
					  <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
					  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
					</svg>) || <b> ${income} </b>  ($$) || <b>
					
					<c:if test = "${cinemaHallOccupationRate == 'NaN'}">
				        0.00
				    </c:if>
				    <c:if test = "${cinemaHallOccupationRate != 'NaN'}">
						${cinemaHallOccupationRate}
					</c:if>
					
					
					
					</b> (%<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pie-chart-fill" viewBox="0 0 16 16">
  <path d="M15.985 8.5H8.207l-5.5 5.5a8 8 0 0 0 13.277-5.5zM2 13.292A8 8 0 0 1 7.5.015v7.778l-5.5 5.5zM8.5.015V7.5h7.485A8.001 8.001 0 0 0 8.5.015z"/>
</svg>)
		</h3>
		<hr/>
	
		<img id="picPreview"  src="/cinema/images/${movie.poster}" width="200px" />
		<hr/>
	
		<form  action="updatepicture"   method="post"  class="form-inline"   enctype="multipart/form-data">
			<input type="file" style="display: none" onChange="Handlechange();" class="btn btn-success btn-lg" name="inpImg" id="inpImg" accept="image/jpg, image/png, image/gif, image/jpeg"/>
			<input type="hidden" name="movieId" value="${movie.id}"/>
			<input type="hidden" name="currentImage" value="${movie.poster}"/> 
			<input type="button" class="btn btn-outline-info btn-md" value="<% out.print(changePoster); %>" id="fakeBrowse" onclick="HandleBrowseClick();"/>

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
			    
			    $("#inpImg").change(function(){
			        readURL(this);
			    });
			
			function HandleBrowseClick()
			{
			    var fileinput = document.getElementById("inpImg");
			    fileinput.click();
			}
			
			function Handlechange()
			{
			    var fileinput = document.getElementById("inpImg");
			    var textinput = document.getElementById("filename");
			    textinput.value = fileinput.value;
			
				if(textinput.value!=''){
					$('#btn_upload').show();  
					$('#choosingButton').val(' ');
				}
			}
			
			</script>
		<input type="text" id="filename" style="display: none" readonly="true"/>
	    <input type="submit" class="btn btn-success btn-sm" style="display: none" id="btn_upload" value="<% out.print(download); %>"/>

	   </form>
	   <hr/>
	
		<h6>English description: </h6>
		<h4>${movie.descriptionEn}</h4>
		<hr/>
		<h6>Опис на українській мові: </h6>
		<h4> ${movie.descriptionUa}</h4>
		<hr/>
		<div><% out.print(genre); %>: 
			<c:if test = "${l10n == 'uk_UA'}">
		        ${movie.genreUa}
		    </c:if>  
		  	<c:if test = "${l10n == 'en_GB'}">
		  	  	${movie.genre}
		    </c:if>
		</div>
		
		<div><% out.print(duration); %>: ${movie.duration} <% out.print(mins); %></div>
		<div><% out.print(basicPrice); %>: ${movie.price} $$</div>
		<hr/>
		<button data-toggle="modal" onclick="openUpdating();" data-target="#updatingModal" class="btn btn-outline-info btn-md"> <% out.print(updateInformation); %> </button>
		<hr/>
		<button data-toggle="modal" onclick="openAddSession();" data-target="#addSessionModal" class="btn btn-info btn-md" style="color:white"><% out.print(addAnewSession); %> </button>
		<hr/>
		
		
	</div>

		<div>
			<c:forEach var="session" items="${listOfAllSessions}" >
				<div> <% out.print(datetime); %>: <b><fmt:formatDate value="${session.dateTime}" pattern="dd.MM.yyyy (HH:mm)" /></b> </div>
				<div><% out.print(price); %>: <b>${session.price}</b> ($$)</div>
				<form action="removesession" method="post">
					<input type="hidden" value="${session.sessionId}" name="sessionId"  />
					<input type="hidden" value="${session.movieId}" name="movieId"  />
					<input value="<% out.print(remove); %>" type="submit" />
				</form>
				<hr/>
			</c:forEach>
		</div>
	
	</div>
</div>


<!-- Modal -->
<div class="modal fade" id="updatingModal" tabindex="-1" role="dialog" aria-labelledby="updatingModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
     <form action="updatemovie" method="post">
      <div class="modal-header">
        <h5 class="modal-title" id="updatingModalLabel"><% out.print(updateMovie); %></h5>
        <button type="button" class="close" data-dismiss="modal" onclick="closeModal();" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="id" value="${movie.id}" />
        <h6><% out.print(title); %>:</h6>
        <div><input type="text" name="title" value="${movie.title}" required/></div>
        <hr/>
        
         <h6>Description in English:</h6>
		<textarea name="descriptionEn" required> ${movie.descriptionEn}</textarea>
		<hr/>
		
		<h6>Опис на українській мові:</h6>
		<textarea name="descriptionUa" required> ${movie.descriptionUa}</textarea>
		<hr>
		
		<h6><% out.print(duration); %> 	(<% out.print(mins); %>): </h6>
		<div><input name="duration" type="number" value="${movie.duration}" required/></div>
		<hr>
		
		<h6><% out.print(genre); %>:</h6>
		<div>
		   <select name="genre">
		   		<option value="${movie.genreId}"> 
			   		<c:if test = "${l10n == 'uk_UA'}">
			        	${movie.genreUa}
			   		 </c:if>  
			  		<c:if test = "${l10n == 'en_GB'}">
			  		  	${movie.genre}
			    	</c:if>
		   		</option>
				<c:forEach var="option" items="${options}" varStatus="loop">
					<option value="${loop.index}" > ${option} </option>
				</c:forEach>
			</select>
		</div>
		<hr>
		
		<h6><% out.print(basicPrice);%> ($$): </h6>		
		<div><input name="price" type="text" value="${movie.price}" required /> </div>
		<hr>      
	

      </div>
      <div class="modal-footer">
        <button type="button" onclick="closeModal();" class="btn btn-danger"><% out.print(close);%></button>
        <button type="submit" id="updateBtn" class="btn btn-primary"><% out.print(update);%></button>
      </div>
       </form>
    </div>
  </div>
</div>





<!-- Modal -->
<div class="modal fade" id="addSessionModal" tabindex="-1" role="dialog" aria-labelledby="addSessionModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
     <form action="addsession" method="post">
      <div class="modal-header">
        <h5 class="modal-title" id="addSessionModalLabel"><% out.print(addAnewSession); %></h5>
        <button type="button" class="close" data-dismiss="modal" onclick="closeModal();" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		
		<h6><% out.print(datetime); %>:</h6>
		<input type="hidden" name="movieId" id="movieId" value="${movie.id}">
		<input type="date" name="sessionDate" id="sessionDate" max="9999-12-12T00:00:00.00" required/>
		<input type="time" name="sessionTime" id="sessionTime"  required/>
		<hr/>
		<h6><% out.print(sessionPrice); %>:</h6>
		<input type="text" placeholder="<% out.print(basicPrice); %>" name="sessionPrice" id="sessionPrice" value="${movie.price}" required/>
		<br/><br/>

      </div>
      <div class="modal-footer">
        <button type="button" onclick="closeModal();" class="btn btn-danger"><% out.print(close);%></button>
        <button type="submit" id="updateBtn" class="btn btn-primary">OK</button>
      </div>
       </form>
    </div>
  </div>
</div>





</body>
</html>