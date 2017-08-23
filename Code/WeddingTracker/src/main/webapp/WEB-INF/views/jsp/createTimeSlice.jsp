<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Wedding Tracker - Create a Time Slice</title>
	</head>
	
	<body>
		<div>
			<h2>Create Time Slice</h2>
			
			<form:form method="POST" action="/WeddingTracker/addTimeSlice">
				<table>
					<tr>
						<td><form:label path = "description">Notes / Description</form:label></td>
						<td><form:input path = "description" /></td>
					</tr>
					<tr>
						<td><form:label path = "startTime">Start Time</form:label></td>
						<td><form:input path = "startTime" /></td>
					</tr>
					<tr>
						<td><form:label path = "duration">Duration</form:label></td>
						<td><form:input path = "duration" /></td>
					</tr>
					<tr>
						<td><form:label path = "location">Location</form:label></td>
						<td><form:select path = "location" items = "${locationList}" /> </td>
					</tr>
					<tr>
						<td><form:label path = "client">Client</form:label></td>
						<td><form:select path = "client" items = "${clientList}"/></td>
					</tr>
					<tr>
						<td><form:label path = "photographers">Photographers</form:label></td>
						<td><form:select path = "photographers" items = "${photographerList}" /></td>
					</tr>

					<form:input type="hidden" path="position"/>
					<form:input type="hidden" path="eventId"/>
					
					<tr>
						<td colspan="2">
							<input type="submit" value = "Submit">
						</td>
					</tr>
				</table>
			</form:form>
			
		</div>
	</body>
	
</html>