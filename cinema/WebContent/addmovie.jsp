<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import="com.busvancar.cinema.MovieDAO" %>

 <%	String redirect="";
 String firstName = request.getParameter("firstName"); 
 if(session.isNew()){
		redirect = "<script language=\"javascript\" type=\"text/javascript\">window.location=\"/cinema\" </script>";
}
 String genreOptions = new MovieDAO().getGenreOptions();
 
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
<%= genreOptions %>
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