<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
	<head>
		<title>Wedding Tracker - added Timeline</title>
	</head>
	
	<body>
		<div>
			<h2>Submitted Timeline Information</h2>
			
			<table>
				<tr>
					<td>startTime</td>
					<td>${startTime}</td>
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