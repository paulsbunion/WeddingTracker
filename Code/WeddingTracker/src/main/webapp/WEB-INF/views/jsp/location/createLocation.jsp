<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wedding Tracker - Create a Location</title>
	</head>
	
	<body>
		<div>
			<h2>Create Location</h2>
			
			<form:form method="POST" action="/WeddingTracker/addLocation">
				<table>
					<tr>
						<td><form:label path = "street">Street Address</form:label></td>
						<td><form:input path = "street"/></td>
					</tr>
					<tr>
						<td><form:label path = "city">City</form:label></td>
						<td><form:input path = "city" /></td>
					</tr>
					<tr>
						<td><form:label path = "state">State</form:label></td>
						<td><form:input path = "state"/></td>
					</tr>
					<tr>
						<td><form:label path = "zip">Zip</form:label></td>
						<td><form:input path = "zip" /></td>
					</tr>
					<tr>
						<td><form:label path = "description">Description (Optional)</form:label></td>
						<td><form:input path = "description" /></td>
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