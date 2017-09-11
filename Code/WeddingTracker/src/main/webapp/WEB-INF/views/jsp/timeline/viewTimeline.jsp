<%@page import="java.sql.Time"%>
<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Wedding Tracker - Timeline for <c:out value="${eventName}"/></title>
	</head>
	
	<body>
		<div>
	      	<a href="/WeddingTracker/">Home</a>
	      </div>
		<div>
			<h2><c:out value="${eventName}"/> <fmt:formatDate pattern="MM/dd/yyy" value="${event.eventDate}"/></h2>
			
<!-- 			for each timeslice, if the location has changed or a photographer was added, draw a line -->
			<table>
<!-- 				<thead> -->
<!-- 					<tr> -->
<!-- 						<th></th> -->
<!-- 						<th>Event Name</th> -->
<!-- 						<th>Date</th> -->
<!-- 						<th>Start Time</th> -->
<!-- 						<th>Duration</th> -->
<!-- 					</tr> -->
<!-- 				</thead> -->
				
				<c:if test="${not empty timeSlices}">
					<c:forEach items="${timeSlices}" var="timeSlice">
					<tr>
						<c:set var = "startTime" value ="${timeSlice.startTime}"/>
						<c:set var = "durationHr" value ="${timeSlice.durationHr}"/>
						<c:set var = "durationMin" value ="${timeSlice.durationMin}"/>
						<c:set var = "description" value ="${timeSlice.description}"/>
						<td><fmt:formatDate type="time" timeStyle="short" value="${timeSlice.startTime}"/>
						<c:set var="endDuration" value ='<%=Time.valueOf("" + (String)(pageContext.getAttribute("startTime").toString())) %>' />
						
						<c:if test="${not empty durationHr}">
							<c:set var="endDuration" value='<%=Time.valueOf((((Time)pageContext.getAttribute("endDuration")).getHours() + Integer.valueOf((String)pageContext.getAttribute("durationHr")) ) + ":00:00") %>' />
						</c:if>
						
						<c:if test="${not empty durationMin}">
							<c:set var="endDuration" value='<%=Time.valueOf( (((Time)pageContext.getAttribute("endDuration")).getHours()) + ":" 
							+  (((Time)pageContext.getAttribute("endDuration")).getMinutes() + Integer.valueOf((String)pageContext.getAttribute("durationMin")) ) 
							+ ":00") %>' />
						</c:if>
						
						<c:if test="${endDuration ne startTime}">
							-<fmt:formatDate type="time" timeStyle="short" value="${endDuration}"/>
						</c:if>
							
						<c:if test="${not empty description}">
							<c:out value=" ${description}"></c:out>
						</c:if>
<%-- 							</c:if> --%>
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
