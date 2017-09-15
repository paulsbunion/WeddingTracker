<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wedding Tracker - added Timeline</title>
	</head>
	
	<body>
		<div>
			<h2>Submitted Timeline Information</h2>
			
			<table>
				<tr>
					<td>startTime</td>
					<td><fmt:formatDate type="time" timeStyle="short" value="${startTime}" /></td>
				</tr>
			</table>
		</div>
		
		<div>
		<ul>
			<li><a href="/WeddingTracker/createTimeSlice/${eventId}">Add a time slice</a></li>
			<li><a href="/WeddingTracker">Home</a></li>
		</ul>
		</div>
	</body>
</html>