<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Wedding Tracker - Add Timeline to Event</title>
	</head>
	<body>
		<div>
			<h2>Create a Timeline</h2>

			<form:form method = "post" action="/WeddingTracker/addTimeline">
				<table>
					<tr>
						<td>Change Start Time</td>
						<td><form:input path="startTime"/></td>
						<form:input type="hidden" path="eventId"/>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="Submit">
				</table>
			</form:form>
			
		</div>
	</body>
</html>