<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Wedding Tracker - Create an EventType</title>
	</head>
	
	<body>
		<div>
			<h2>Create Event Type</h2>
			
			<form:form method="POST" action="/WeddingTracker/addEventType">
				<table>
					<tr>
						<td><form:label path = "eventType">Event Type Description</form:label></td>
						<td><form:input path = "eventType" /></td>
					</tr>
					<tr>
						<td><form:label path = "baseCost">Base Cost</form:label></td>
						<td><form:input path = "baseCost"/></td>
					</tr>
					
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