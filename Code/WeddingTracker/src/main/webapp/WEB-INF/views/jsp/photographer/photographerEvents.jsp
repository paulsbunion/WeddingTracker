<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wedding Tracker - List of Photographer Events</title>
	</head>
	
	<body>
		<div>
		<div>
      	<a href="/WeddingTracker/">Home</a>
      	<a href="/WeddingTracker/listPhotographers">Photographers</a>
      </div>
			<h2>List of Photographer Events for ${photographer}</h2>
			
			<c:if test="${not empty photographerEvents}">
				<table>
					<thead>
						<tr>
							<th></th>
							<th>Event Name</th>
							<th>Event Date</th>
							<th>Start Time</th>
						</tr>
					</thead>
					
					<c:forEach items="${photographerEvents}" var="event">
					
					<tr>
						<td>
							<nobr>
								<spring:url value="/editEvent/${event.eventId}" var="editEventUrl" />
								<button onclick="location.href='${editEventUrl}'">Edit Event</button>
							</nobr>
						</td>
						
						<td><c:out value="${event.eventName}"/></td>
						<td><c:out value="${event.eventDate}"/></td>
						<td><c:out value="${event.startTime}"/></td>
						
						<td>
						<spring:url value="/listTimeSlices/${event.eventId}" var="editTimelineUrl" />
						<spring:url value="/viewTimeline/${event.eventId}" var="viewTimelineUrl" />
						<spring:url value="/createTimeline/${event.eventId}/${event.startTime}" var="createTimelineUrl" />
						<spring:url value="/deleteEvent/${event.eventId}" var="deleteEventUrl" />
						
							<nobr>
								<button onclick="location.href='${editEventUrl}'">Edit</button>
							</nobr>
							<nobr>
								<c:if test="${not empty timelineIdMap[event.eventId]}">
									<button onclick="location.href='${editTimelineUrl}'">Edit Timeline</button>
								</c:if>
								<c:if test="${not empty timelineIdMap[event.eventId]}">
									<button onclick="location.href='${viewTimelineUrl}'">Preview</button>
								</c:if>
								<c:if test="${empty timelineIdMap[event.eventId]}">
									<button onclick="location.href='${createTimelineUrl}'">Add Timeline</button>
								</c:if>
							</nobr>
						</td>
					</tr>
					</c:forEach>
					
				</table>
			</c:if>
			
			<c:if test="${empty photographerEvents}">
				<h2><c:out value="No Events"/></h2>
			</c:if>
		</div>
		
		<div>
	      	<ul>
		      	<li><a href="/WeddingTracker/listPhotographers">List Photographers</a></li>
		      	<li><a href="/WeddingTracker/">Home</a></li>
	      </ul>
      </div>
	</body>
</html>
