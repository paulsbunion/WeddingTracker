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
						<td><form:label path = "description">Notes / Description</form:label></td>
						<td><form:input path = "description"/></td>
					</tr>
					<tr>
						<td><form:label path = "startTime">Start Time</form:label></td>
						<td><form:input path = "startTime"/></td>
					</tr>
					<tr>
						<td><form:label path = "duration">Duration</form:label></td>
						<td><form:input path = "duration"/></td>
					</tr>
					
					<tr>
						<td><form:label path = "location">Location</form:label></td>
						<td>
							<form:select path = "location">
								<form:option value="">&nbsp;</form:option>
								<form:options items = "${locationList}" />
							</form:select> 
						</td>
					</tr>
					<tr>
						<td><form:label path = "client">Client</form:label></td>
						<td>
							<form:select path = "client">
								<form:option value="">&nbsp;</form:option>
								<form:options items = "${clientList}"/>
							</form:select>
						</td>
							
					</tr>
					
					<tr>
						<td><form:label path = "photographers">Photographers</form:label></td>
						<td>
							<ul>
								<form:checkboxes element="li" path = "photographers" items = "${photographerList}" />
							</ul>
						</td>
					</tr>
					
					<tr>
						<td><form:hidden path="eventId"/></td>
						<td><form:hidden path="position"/></td>
						<td><form:hidden path="chunkId"/></td>
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