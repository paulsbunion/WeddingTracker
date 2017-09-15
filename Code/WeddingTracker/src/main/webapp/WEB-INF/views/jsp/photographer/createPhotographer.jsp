<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wedding Tracker - Create a Photographer</title>
	</head>
	
	<body>
		<div>
			<h2>Create Photographer</h2>
			
			<form:form method="POST" action="/WeddingTracker/addPhotographer">
				<table>
					<tr>
						<td><form:label path = "firstName">First Name</form:label></td>
						<td><form:input path = "firstName"/></td>
					</tr>
					<tr>
						<td><form:label path = "lastName">Last Name</form:label></td>
						<td><form:input path = "lastName" /></td>
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