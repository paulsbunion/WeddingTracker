<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Wedding Tracker - Edit an Event</title>
	</head>
	
	<body>
		<div>
			<h2>Edit an Event</h2>
			
			<form:form method="POST" action="/WeddingTracker/editEvent">
				<table>
					<tr>
						<td><form:label path = "eventName">Event Name</form:label></td>
						<td><form:input path = "eventName"/></td>
					</tr>
					<tr>
						<td>Event Type</td>
						<td><form:select path = "eventType" items = "${eventTypeList}" /> </td>
					</tr>
					<tr>
						<td><form:label path = "eventDate">Date</form:label></td>
						<td><form:input path = "eventDate"/></td>
					</tr>
					<tr>
						<td><form:label path = "startTime">StartTime</form:label></td>
						<td><form:input path = "startTime"/></td>
					</tr>
					<tr>
						<td><form:label path = "extraCost">Extra Cost</form:label></td>
						<td><form:input path = "extraCost"/></td>
					</tr>
					<tr>
						<td><form:label path = "notes">Notes</form:label></td>
						<td><form:input path = "notes"/></td>
					</tr>
					<tr>
						<td><form:hidden path="eventId"/></td>
						<td><form:hidden path="eventType"/></td>
						<td><form:hidden path="duration"/></td>
					</tr>	
					<tr>
						<td colspan="2">
							<input type="submit" value = "Submit">
						</td>
					</tr>
				</table>
			</form:form>
			
		</div>
		
		<div>
      	<a href="/WeddingTracker/">Home</a>
      </div>
	</body>
	
</html>