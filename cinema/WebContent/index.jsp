<%@page import="com.busvancar.cinema.Movies"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>Вітаємо в Кіно / Welcome to VitivCinema <%= request.getAttribute("logingBoard") %></div>
<hr/>
<div><%= request.getAttribute("schedule") %>.</div>
</body>
</html>