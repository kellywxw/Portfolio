<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calendar Comments</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<h1>Calendar Comments</h1>
<br/>
<div id="commentsCreate"><a href="commentscreate?itemId=<c:out value="${itemId}"/>">Create Comment</a></div>
<br/>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>CommentId</th>
                <th>Content</th>
                <th>DateCreated</th>
                <th>Delete</th>
                <th>Update</th>
            </tr>
            <c:forEach items="${comments}" var="comments" >
                <tr>
                    <td><c:out value="${comments.getCommentId()}" /></td>
                    <td><c:out value="${comments.getContent()}" /></td>
                    <td><fmt:formatDate value="${comments.getCreated()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><a href="commentsdelete?commentId=<c:out value="${comments.getCommentId()}"/>">Delete</a></td>
                    <td><a href="commentsupdate?commentId=<c:out value="${comments.getCommentId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
