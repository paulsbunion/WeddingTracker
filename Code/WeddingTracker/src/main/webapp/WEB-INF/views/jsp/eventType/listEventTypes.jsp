<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wedding Tracker - List of EventTypes</title>
	</head>
	
	<body>
		<div>
			<h2>List of EventTypes</h2>
			
			<spring:url value="/createEventType" var="createEventTypeUrl" />
			<button onclick="location.href='${createEventTypeUrl}'">New EventType</button>
			
			<table>
				<thead>
					<tr>
						<th>Description</th>
						<th>Base Cost</th>
					</tr>
				</thead>
				
				<c:forEach items="${eventTypeList}" var="eventType">
				<tr>
					<td><c:out value="${eventType.eventType}"/></td>
					<td><c:out value="${eventType.baseCost}"/></td>
					
					<td>
					<spring:url value="/editEventType/${eventType.eventTypeId}" var="editEventTypeUrl" />
						<nobr>
							<button onclick="location.href='${editEventTypeUrl}'">Edit</button>
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
