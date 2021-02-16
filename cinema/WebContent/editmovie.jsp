<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Editing movie</title>
</head>
<body>
<div>
<div> Editing movie / Редагування фільму: ${title} (N ${movie_id}) | <a href="/administration">Home Page</a></div>
<hr/>

<div><img src="/cinema/images/${poster}" width="200px" /></div>
<div>English description: ${descriptionEn}</div>
<div>Опис на українській мові: ${descriptionUa}</div>
<div>Genre: ${genre}</div>
<div>Duration: ${duration} minutes</div>
<div>Basic price: ${price} uah</div>
<hr/>
<div><a href="#">Enter new session details </a></div>
<hr/>
<div></div>
<div>${listOfAllSessions}</div>
<div></div>
<form method="post" action="addsession">

<input type="hidden" name="movieId" id="movieId" value="${movie_id}">
<input type="date" name="sessionDate" id="sessionDate" max="9999-12-12T00:00:00.00" required/>
<input type="time" name="sessionTime" id="sessionTime" max="9999-12-12T00:00:00.00" min="09:00" max="22:00" required/>

<br/><br/>
<input type="text" placeholder="Basic price for the session" name="sessionPrice" id="sessionPrice" value="${price}" required/>
<br/><br/>

<input type="submit" value="OK"/>
</form>
</div>
</body>

</html>