<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Calendar</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<body>
	<h1>Create a Calendar</h1>
	<form action="calendarcreate" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<label for="calendarbackground">Calendar Background</label>
			<input id="calendarbackground" name="calendarbackground" value="">
		</p>
	
		<p>
			<label for="ispublic_huh">Public?</label>
		<select>
  			<option value="false">False</option>
  			<option value="true">True</option>
		</select>
		</p>
		
		<p>
			<label for="shareWorkCategory_huh">Share Work Category?</label>
		<select>
  			<option value="false">False</option>
  			<option value="true">True</option>
		</select>
		</p>
		
		<p>
			<label for="shareShcoolCategory_huh">Share School Category?</label>
		<select>
		  	<option value="false">False</option>
  			<option value="true">True</option>
		</select>
		</p>

		<p>
			<label for="shareSocialCategory_huh">Share Social Category?</label>
		<select>
		  	<option value="false">False</option>
  			<option value="true">True</option>
		</select>
		</p>
		
		<p>
			<label for="shareOtherCategory_huh">Share Other Category?</label>
		<select>
		    <option value="false">False</option>
  			<option value="true">True</option>
		</select>
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