<%@page import="com.busvancar.cinema.Movies"%> 
<%@page import="com.busvancar.cinema.User"%> 
<%@ page import = "com.busvancar.cinema.Genre"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import = "java.io.*, java.util.*" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>

 <%	
 String redirect="";
 User user = (User) session.getAttribute("user"); 
 if(user.getAdmin()<1 || user == null){
		redirect = "<script language=\"javascript\" type=\"text/javascript\">window.location=\"/cinema\" </script>";
}
 Locale locale = new Locale((String) session.getAttribute("l10n"));

 String genreOptionsUa = Genre.getGenreSelectOptions(Genre.genres_uk_UA);
 String genreOptionsEn = Genre.getGenreSelectOptions(Genre.genres_en_GB);

 
  %>
<!DOCTYPE html>
<html>
<head>
<%= redirect %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Adding a new film</title>
</head>
<body>
<div>
<div>Adding new movie / Новий фільм</div>
<hr/>
<form accept-charset="UTF-8" action="addmovie" method="post"  enctype="multipart/form-data">
<input placeholder="Title" type="text" name="title" id="title">
<br>
<textarea cols="30" rows="7" placeholder="Description in English"  name="descriptionEn" id="descriptionEn"></textarea>
<br/>
<textarea cols="30" rows="7" placeholder="Опис на українській"  name="descriptionUk" id="descriptionUk">
</textarea>
<br/>
<input placeholder="Duration in minutes" type="number" name="duration" id="duration" min="0" value="0"/>
<br/>
<select name="genre" id="genre">

					<c:if test = "${l10n == 'uk_UA'}">
	      		    	 <% out.print(genreOptionsUa); %>

	      			</c:if>  
	  				<c:if test = "${l10n == 'en_GB'}">
	  				  	 <% out.print(genreOptionsEn); %>
	      			</c:if>
</select>
<br/>
<input type="text" placeholder="00.00" name="price" id="price"  required/>
<br/>
<input type="file" name="poster" id="poster" />
<br/>
<input type="submit" value="OK">
</form>
<div><a href="/cinema">Home Page</a></div>

</div>
</body>

</html>