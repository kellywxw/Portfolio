<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <div id="banner"></div><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChopChop User Login</title>
<link href="css/TableStyle.css" rel="stylesheet" >
</head>
<body>
   <h1>User Login</h1>
	<form action="userlogin" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<label for="password">Password</label>
			<input id="password" name="password">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="userCreate"><a href="usercreate">Create User</a></div>
	<br/>
		<h1>User Profile</h1>
        <table>
            <tr>
                <th>UserName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>ProfilePicture</th>
                <th>About</th>
                <th>Age</th>
                <th>Gender</th>
                <th>Calendar</th>
                <th>Delete Profile</th>
                <th>Update Profile</th>
            </tr>
                <tr>
                    <td><c:out value="${users.getUserName()}" /></td>
                    <td><c:out value="${users.getFirstName()}" /></td>
                    <td><c:out value="${users.getLastName()}" /></td>
                    <td><c:out value="${users.getEmail()}" /></td>
                    <td><c:out value="${users.getProfilePicture()}" /></td>
                    <td><c:out value="${users.getAbout()}" /></td>
                    <td><c:out value="${users.getAge()}" /></td>
                    <td><c:out value="${users.getGender()}" /></td>
                    <td><a href="usercalendar?username=<c:out value="${users.getUserName()}"/>">Calendar</a></td>
                    <td><a href="userdelete?username=<c:out value="${users.getUserName()}"/>">Delete</a></td>
                    <td><a href="userupdate?username=<c:out value="${users.getUserName()}"/>">Update</a></td>
                </tr>
            
       </table>
</body>
</html>
