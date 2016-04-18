<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Task</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<body>
	<h1>Create a Task</h1>
	<form action="taskcreate" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<label for="description">Description</label>
			<input id="description" name="description" value="">
		</p>
		<p>
			<label for="shareitems_huh">Share Items?</label>
		<select>
		    <option value="false">False</option>
  			<option value="true">True</option>	
		</select>
		</p>
        <p>
			<label for="accomplished_huh">Accomplished?</label>
		<select>
		    <option value="false">False</option>
  			<option value="true">True</option>	
		</select>
		</p>
		
		<p>
			<label for="duedate">Due Date (yyyy-mm-dd hh:mm:ss)</label>
			<input id="duedate" name="duedate" value="">
		</p>
		
		
<%-- 	
		<p>
			<label for="category">Category</label>
		<select>
  			<option value="work">Work</option>
  			<option value="school">School</option>
  			<option value="social">Social</option>
  			<option value="other">Other</option>
		</select>
		</p>
--%>	
		<p>
			<label for="category">Category (work, school, social, other)</label>
			<input id="category" name="category" value="">
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