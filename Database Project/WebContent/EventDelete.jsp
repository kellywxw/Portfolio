<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete an Event</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<body>
	<h1>Delete an Event</h1>
	<form action="eventdelete" method="post">
		<p>
			<div <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				<label for="itemId">ItemId</label>
				<input id="itemId" name="itemId" value="${fn:escapeXml(param.itemId)}">
			</div>
		<p>
			<span id="submitButton" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
			<input type="submit">
			</span>
		</p>
	</form>
	<br/><br/>
	
</body>
</html>