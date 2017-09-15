<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Wedding Tracker - Home</title>
	</head>

	<body>
		<div>
			<spring:url value="/logout" var="logoutUrl" />
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h2>Welcome to the Event Manager, ${pageContext.request.userPrincipal.name} <button onclick="location.href='${logoutUrl}'">Logout</button></h2>
			</c:if>
		</div>
		
		<div>
		<table>
		
		<spring:url value="/listClients" var="listClientsUrl" />
		<spring:url value="/listEvents" var="listEventsUrl" />
		<spring:url value="/listPhotographers" var="listPhotographersUrl" />
		<spring:url value="/listEventTypes" var="listEventTypesUrl" />
		<spring:url value="/listLocations" var="listLocationsUrl" />
		
			
			<tr>
				<td><button onclick="location.href='${listEventsUrl}'">Events</button></td>
				<td><button onclick="location.href='${listClientsUrl}'">Clients</button></td>
				<td><button onclick="location.href='${listPhotographersUrl}'">Photographers</button></td>
				<td><button onclick="location.href='${listLocationsUrl}'">Locations</button></td>
			</tr>
		</table>
			<ul>
<!-- 				<li><a href="/WeddingTracker/listClients">Clients</a></li> -->
<!-- 				<li><a href="/WeddingTracker/listPhotographers">Photographers</a></li> -->
<!-- 				<li><a href="/WeddingTracker/listEventTypes">EventTypes</a></li> -->
<!-- 				<li><a href="/WeddingTracker/listEvents">Events</a></li> -->
<!-- 				<li><a href="/WeddingTracker/createEventType">Add an EventType</a></li>  -->
<!-- 				<li><a href="/WeddingTracker/createEvent">Add an Event</a></li> -->
<!-- 				<li><a href="/WeddingTracker/createClient">Add a Client</a></li> -->
<!-- 				<li><a href="/WeddingTracker/createPhotographer">Add a Photographer</a></li> -->
<!-- 				<li><a href="/WeddingTracker/createLocation">Add a Location</a></li> -->
<!-- 				<li><a href="/WeddingTracker/listClients">List Clients</a></li> -->
<!-- 				<li><a href="/WeddingTracker/listPhotographers">List Photographers</a></li> -->
<!-- 				<li><a href="/WeddingTracker/listEventTypes">List EventTypes</a></li> -->
<!-- 				<li><a href="/WeddingTracker/listEvents">List Events</a></li> -->
			</ul>
		</div>
	</body>
</html>