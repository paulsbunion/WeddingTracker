<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Wedding Tracker - List of Events</title>
	</head>
	
	<body>
		<div>
	      	<a href="/WeddingTracker/">Home</a>
	      </div>
		<div>
			<h2>Event Types</h2>
			<spring:url value="/listEventTypes" var="listEventTypesUrl" />
			<button onclick="location.href='${listEventTypesUrl}'">List Event Types</button>
			
			<h2>List of Events</h2>
			<spring:url value="/createEvent" var="createEventUrl" />
			<button onclick="location.href='${createEventUrl}'">New Event</button>
			
			<table>
				<thead>
					<tr>
						<th></th>
						<th>Event Name</th>
						<th>Date</th>
						<th>Start Time</th>
						<th>Duration</th>
					</tr>
				</thead>
				
				<c:forEach items="${eventList}" var="event">
				<tr>
					<td>
						<nobr>
							<spring:url value="/editEvent/${event.eventId}" var="editEventUrl" />
							<button onclick="location.href='${editEventUrl}'">Edit Event</button>
						</nobr>
					</td>
					<td><c:out value="${event.eventName}"/></td>
					<td><fmt:formatDate pattern="MM/dd/yyy" value="${event.eventDate}"/></td>
					<td><fmt:formatDate type="time" timeStyle="short" value="${event.startTime}"/></td>
					<td><c:out value="${event.duration.getHours()} Hr ${event.duration.getMinutes()} Min"/></td>
					
					
					<spring:url value="/listTimeSlices/${event.eventId}" var="editTimelineUrl" />
					<spring:url value="/viewTimeline/${event.eventId}" var="viewTimelineUrl" />
					<spring:url value="/createTimeline/${event.eventId}/${event.startTime}" var="createTimelineUrl" />
					<spring:url value="/deleteEvent/${event.eventId}" var="deleteEventUrl" />
					
					<td>
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
					
					<td>
						<nobr>
							<button onclick="location.href='${deleteEventUrl}'">Delete Event</button>
						</nobr>
					</td>
				</tr>
				</c:forEach>
				
			</table>
		</div>
		
		<div>
      	<a href="/WeddingTracker/">Home</a>
      </div>
	</body>
</html>
