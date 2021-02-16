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
String movieRemovied = rb.getString("movieRemovied");
String movieNotRemovied = rb.getString("movieNotRemovied");
String continue2admin = rb.getString("continue2admin");




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
<title>VitivCinema message</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.x-git.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">


     
<style>
 	a{
		margin-right:100px;
		margin-left:100px;
		margin-top:3px;
		margin-bottom:3px;
		color:#33ff39;
		text-align:center;
		
	}
	.message{
		font-size:50px;
	    font-family: cursive;
		color:maroon;
		 position: absolute;
 		 top: 35%;
  		left: 35%;
	}
</style>
</head>
<body>
			<div class="message">
				<div>${message}</div>
				<div><a href="administration"><% out.print(continue2admin);%></a></div>
			</div>
</body>
</html>