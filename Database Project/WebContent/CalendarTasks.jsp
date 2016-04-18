<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calendar Tasks</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<h1>Calendar Tasks</h1>
<br/>
<div id="taskCreate"><a href="taskcreate">Create Task</a></div>
<br/>
<body>
        <table border="1">
            <tr>
                <th>ItemId</th>
                <th>Description</th>
                <th>Share Items?</th>
                <th>Accomplished?</th>
                <th>Due Date</th>
                <th>Create Comment</th>
                <th>Comments</th>
                <th>Delete Task</th>
                <th>Update Task</th>
            </tr>
            <c:forEach items="${task}" var="task" >
                <tr>
                    <td><c:out value="${task.getItemId()}" /></td>
                    <td><c:out value="${task.getDescription()}" /></td>
                    <td><c:out value="${task.getShareItems_huh()}" /></td>
                    <td><c:out value="${task.getAccomplished_huh()}" /></td>
                    <td><fmt:formatDate value="${task.getDueDate()}" pattern="yyyy-MM-dd HH:mm:SS"/></td>
                    <td><a href="commentscreate?itemId=<c:out value="${task.getItemId()}"/>">Create Comment</a></td>
                    <td><a href="calendarcomments?itemId=<c:out value="${task.getItemId()}"/>">Comments</a></td>
                    <td><a href="taskdelete?itemId=<c:out value="${task.getItemId()}"/>">Delete</a></td>
                    <td><a href="taskupdate?itemId=<c:out value="${task.getItemId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
