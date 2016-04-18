<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a User</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<body>
	<h1>Create a User</h1>
	<form action="usercreate" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="">
		</p>
		<p>
			<label for="password">Password</label>
			<input id="password" name="password" value="">
		</p>
	
		<p>
			<label for="firstname">FirstName</label>
			<input id="firstname" name="firstname" value="">
		</p>
		<p>
			<label for="lastname">LastName</label>
			<input id="lastname" name="lastname" value="">
		</p>
		<p>
			<label for="email">Email</label>
			<input id="email" name="email" value="">
		</p>
		<p>
			<label for="profilePicture">ProfilePicture</label>
			<input id="profilePicture" name="profilePicture" value="">
		</p>
		<p>
			<label for="about">About</label>
			<input id="about" name="about" value="">
		</p>
        <p>
			<label for="age">Age</label>
			<input id="age" name="age" value="">
		</p>
		<p>
			<label for="gender">Gender</label>
			<input id="gender" name="gender" value="">
		</p>					
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>