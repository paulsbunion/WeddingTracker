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
			<a href="/WeddingTracker/CreateTimeChunk/${eventId}">Add a timeline occurrence</a>
		</div>
	</body>
</html>