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
<div>Просимо зареєструватись / Please Sign in</div>
<hr/>
<div>
<form method="post" action="signin">
<input type="text" placeholder="First name" name="firstName" id="firstName" required/>
<br/>
<input type="text"  placeholder="Second name" name="lastName" id="lastName" required/>
<br/>
<input type="email"  placeholder="Email address" name="email" id="email" required/>
<br/>
<input type="password"  placeholder="Password" name="psw" id="psw" required/>
<br/>
<input type="submit"  value="OK"/>
<br/>


</form>
<div><a href="/cinema">Home Page</a> | <a href="login.jsp">Login</a></div>
</div>
</body>
</html>