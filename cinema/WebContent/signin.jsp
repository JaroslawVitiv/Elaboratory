<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import = "java.io.*, java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.*, java.util.ResourceBundle "%>

<%
Locale locale = new Locale((String) session.getAttribute("l10n"));
ResourceBundle rb = ResourceBundle.getBundle("l10n_"+session.getAttribute("l10n"), locale);
String login = rb.getString("login");
String mailPswPlease = rb.getString("mailPswPlease");
String emailAddress = rb.getString("emailAddress");
String password = rb.getString("password");
String homePage = rb.getString("homePage");
String signin = rb.getString("signin");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up VitivCinema</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box}

/* Full-width input fields */
input[type=email], input[type=password], input[type=text] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;
  border: none;
  background: #f1f1f1;
}

input[type=submit] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;
  border: none;
  background-color: #337dff;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  opacity: 0.9;
  float: left;
}

input[type=email]:focus, input[type=password]:focus, input[type=text]:focus {
  background-color: #ddd;
  outline: none;
}

hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 25px;
}

/* Set a style for all buttons */
a {
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
  opacity: 0.9;
}

a:hover {
  opacity:1;
}

/* Extra styles for the cancel button */
.cancelbtn {
  padding: 14px 20px;
  background-color: #f44336;
}

/* Float cancel and signup buttons and add an equal width */
.cancelbtn, .signupbtn {
  float: left;
  width: 50%;
}

/* Add padding to container elements */
.container {
  padding: 16px;
}

/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

/* Change styles for cancel button and signup button on extra small screens */
@media screen and (max-width: 300px) {
  .cancelbtn, .signupbtn {
     width: 100%;
  }
}

#loginError{
	color:red;
	font-size:30px;
	padding:20px;
	text-align:center;
}
</style>
</head>
<body>
<div class="container" >

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
		<div>
            <a type="button" class="cancelbtn" href="/cinema/?language=<% out.print(session.getAttribute("l10n")); %>"><% out.print(homePage); %></a>
            <a type="button" class="signupbtn" href="signin.jsp" ><% out.print(login); %></a>
		</div>
	</div>
</div>
</body>
</html>