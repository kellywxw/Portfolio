<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Comment</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<body>
	<h1>Create a Comment</h1>
	<form action="commentscreate" method="post">
	
		<p>
			<label for="itemId">Item Id</label>
			<input id="itemId" name="itemId" value="${fn:escapeXml(param.itemId)}">
		</p>
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="">
		</p>
		<p>
			<label for="content">Content</label>
			<input id="content" name="content" value="">
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