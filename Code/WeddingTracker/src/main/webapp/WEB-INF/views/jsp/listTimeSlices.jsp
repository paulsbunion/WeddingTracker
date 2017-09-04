<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Wedding Tracker - List of Time Slices</title>
	</head>
	
	<body>
		<div>
			<h2>List of Time Slices for ${eventName}</h2>
			
			<spring:url value="/createTimeSlice/${eventId}" var="createTimeSliceUrl" />
			<button onclick="location.href='${createTimeSliceUrl}'">New Time Slice</button>
			
			
			<table>
				<thead>
					<tr>
						<th>Description</th>
						<th>Start Time</th>
						<th>Client</th>
					</tr>
				</thead>
				
				<c:if test="${not empty timeSlices}">
					<c:forEach items="${timeSlices}" var="timeSlice">
					<tr>
						<td><c:out value="${timeSlice.description}"/></td>
						<td><c:out value="${timeSlice.startTime}"/></td>
						
						<td>
							<c:if test="${not empty clientMap[timeSlice.chunkId]}">
								<c:out value="${clientMap[timeSlice.chunkId]}"/>
							</c:if>
							<c:if test="${empty clientMap[timeSlice.chunkId]}">
								<c:out value="No Client"/>
							</c:if>
						</td>
						
						<td>
						<spring:url value="/editTimeSlice/${eventId}/${timeSlice.chunkId}" var="editTimeSliceUrl" />
							<nobr>
								<button onclick="location.href='${editTimeSliceUrl}'">Edit</button>
							</nobr>
							
						</td>
						<td>
						<spring:url value="/deleteTimeSlice/${eventId}/${timeSlice.chunkId}" var="deleteTimeSliceUrl" />
							<nobr>
								<button onclick="location.href='${deleteTimeSliceUrl}'">Delete</button>
							</nobr>
						</td>
					</tr>
					</c:forEach>
				</c:if>
				
				<c:if test="${empty timeSlices}">
					<tr>
						<td><c:out value="No Time Slices"/></td>
					</tr>
				</c:if>
				
			</table>
		</div>
		
		<div>
      	<a href="/WeddingTracker/">Home</a>
      </div>
	</body>
</html>
