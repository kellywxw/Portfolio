<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calendar Events</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<h1>Calendar Events</h1>
<br/>
<div id="eventCreate"><a href="eventcreate?username=<c:out value="${username}"/>">Create Event</a></div>
<br/>
<body>
        <table border="1">
            <tr>
                <th>ItemId</th>
                <th>Description</th>
                <th>Share Items?</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Create Comment</th>
                <th>Comments</th>
                <th>Delete Event</th>
                <th>Update Event</th>
            </tr>
            <c:forEach items="${event}" var="event" >
                <tr>
                    <td><c:out value="${event.getItemId()}" /></td>
                    <td><c:out value="${event.getDescription()}" /></td>
                    <td><c:out value="${event.getShareItems_huh()}" /></td>
                    <td><fmt:formatDate value="${event.getStartDate()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${event.getEndDate()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><a href="commentscreate?itemId=<c:out value="${event.getItemId()}"/>">Create Comment</a></td>
                    <td><a href="calendarcomments?itemId=<c:out value="${event.getItemId()}"/>">Comments</a></td>
                    <td><a href="eventdelete?itemId=<c:out value="${event.getItemId()}"/>">Delete</a></td>
                    <td><a href="eventupdate?itemId=<c:out value="${event.getItemId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
