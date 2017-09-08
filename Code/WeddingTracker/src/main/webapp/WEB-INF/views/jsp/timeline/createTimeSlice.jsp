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
						<td>Duration</td>
<%-- 						<td><form:input path = "durationHr" />Hr</td> --%>
<%-- 						<td><form:input path = "durationMin" />Min</td> --%>
						<td><form:select path="durationHr" items = "${hrMap}" /> Hr
						<form:select path="durationMin" items = "${minMap}"/> Min</td>
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