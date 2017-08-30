<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Wedding Tracker - List of Photographer Events</title>
	</head>
	
	<body>
		<div>
			<h2>List of Photographer Events for ${photographer}</h2>
			
			
			<table>
				<thead>
					<tr>
						<th>Event Name</th>
						<th>Event Date</th>
						<th>Start Time</th>
					</tr>
				</thead>
				
				<c:forEach items="${photographerEvents}" var="event">
				<tr>
					<td><c:out value="${event.eventName}"/></td>
					<td><c:out value="${event.eventDate}"/></td>
					<td><c:out value="${event.startTime}"/></td>
					
					<td>
<%-- 					<spring:url value="/editPhotographer/${photographer.staffId}" var="editPhotographerUrl" /> --%>
						<nobr>
<%-- 							<button onclick="location.href='${editPhotographerUrl}'">Edit</button> --%>
							<button >View</button>
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
