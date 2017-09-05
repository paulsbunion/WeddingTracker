<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Wedding Tracker - List of Locations</title>
	</head>
	
	<body>
		<div>
			<h2>List of Locations</h2>
			
			<spring:url value="/createLocation" var="createLocationUrl" />
			<button onclick="location.href='${createLocationUrl}'">New Location</button>
			
			<table>
				<thead>
					<tr>
						<th>Street</th>
						<th>City</th>
						<th>State</th>
						<th>Zip</th>
						<th>Description</th>
					</tr>
				</thead>
				
				<c:forEach items="${locationList}" var="location">
				<tr>
					<td><c:out value="${location.street}"/></td>
					<td><c:out value="${location.city}"/></td>
					<td><c:out value="${location.state}"/></td>
					<td><c:out value="${location.zip}"/></td>
					<td><c:out value="${location.description}"/></td>
					
					<td>
					<spring:url value="/editLocation/${location.locationId}" var="editLocationUrl" />
						<nobr>
							<button onclick="location.href='${editLocationUrl}'">Edit</button>
						</nobr>
				</tr>
				</c:forEach>
				
			</table>
		</div>
		
		<div>
      	<a href="/WeddingTracker/">Home</a>
      </div>
	</body>
</html>
