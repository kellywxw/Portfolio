<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calendar</title>
<link href="css/TableStyle.css" rel="stylesheet" >
	</head>
<h1>Calendar</h1>
<body>
	<br />
	<div id="calendarCreate">
		<a
			href="calendarcreate?username=<c:out value="${fn:escapeXml(param.username)}"/>">Create
			Calendar</a>
	</div>
	<br/>
	<table id="t02">
		<tr>
			<th>Date Created</th>
			<th>Calendar Background</th>
			<th>Public?</th>
			<th>Events</th>
			<th>Tasks</th>
			<th>Delete Calendar</th>
		</tr>
		<tr>
			<td><c:out value="${calendar.getDateCreated()}" /></td>
			<td><c:out value="${calendar.getCalendarBackground()}" /></td>
			<td><c:out value="${calendar.getIsPublic_huh()}" /></td>
			<td><a
				href="calendarevents?username=<c:out value="${username}"/>">Events</a></td>
			<td><a
				href="calendartasks?username=<c:out value="${username}"/>">Tasks</a></td>
			<td><a
				href="calendardelete?username=<c:out value="${username}"/>">Delete
					Calendar</a></td>
		</tr>
	</table>
</body>
</html>
