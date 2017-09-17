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
			
			<form:form method="POST" action="/WeddingTracker/addLocation" commandName="location">
				<table>
					<tr>
						<td><form:label path = "street">Street Address</form:label></td>
						<td><form:input path = "street"/></td>
						<td><form:errors path= "street" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path = "city">City</form:label></td>
						<td><form:input path = "city" /></td>
						<td><form:errors path= "city" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path = "state">State</form:label></td>
						<td>
							<form:select path="state">
							<form:option value="OH"/>
							<form:options items = "${stateMap}" />
							</form:select>
						</td>
						<td><form:errors path= "state" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path = "zip">Zip</form:label></td>
						<td><form:input path = "zip" /></td>
						<td><form:errors path= "zip" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path = "description">Description (Optional)</form:label></td>
						<td><form:input path = "description" /></td>
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
	</body>
	
</html>