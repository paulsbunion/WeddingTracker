<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wedding Tracker - Edit an Event Type</title>
	</head>
	
	<body>
		<div>
			<h2>Edit an Event Type</h2>
			
			<form:form method="POST" action="/WeddingTracker/editEventType">
				<table>
					<tr>
						<td><form:label path = "eventType">Description</form:label></td>
						<td><form:input path = "eventType"/></td>
					</tr>
					<tr>
						<td><form:label path = "baseCost">Base Cost</form:label></td>
						<td><form:input path = "baseCost"/></td>
					</tr>
					
					<tr>
						<td><form:hidden path="eventTypeId"/></td>
					</tr>	
					<tr>
						<td colspan="2">
							<input type="submit" name="submitCancelParam" value = "Submit">
							<input type="submit" name="submitCancelParam" value = "Cancel">
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