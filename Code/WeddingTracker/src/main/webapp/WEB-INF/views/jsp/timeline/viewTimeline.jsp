<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Time"%>
<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
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
					<%
						// get the notes map
						HashMap<String, String> notesMap = new HashMap<String, String>();
					try {
						notesMap = (HashMap<String, String>)request.getAttribute("notes");
					}
					catch (Exception e) {}
					%>
					<c:set var = "oldLocation" value =""/>
					<c:set var = "newLocation" value =""/>
					<c:set var = "firstRun" value ="true"/>
					
					<c:forEach items="${timeSlices}" var="timeSlice">
					<c:set var = "newLocation" value ="${timeSlice.location}"/>
					
					<c:if test="${firstRun == 'false'}">
						<c:if test="${newLocation ne oldLocation}">
						<c:set var = "oldLocation" value ="${newLocation}"/>
						
						<tr>
						<td colspan="2">
						<br>
						_______________________________________________________________________________
						<br>
						</td>
						</tr>
					</c:if>
					
					</c:if>
					<c:if test="${firstRun == 'true'}">
						<c:set var = "firstRun" value ="false"/>
						<c:set var = "oldLocation" value ="${newLocation}"/>
					</c:if>
					
					<tr>
						<c:set var = "startTime" value ="${timeSlice.startTime}"/>
						<c:set var = "durationHr" value ="${timeSlice.durationHr}"/>
						<c:set var = "durationMin" value ="${timeSlice.durationMin}"/>
						<c:set var = "description" value ="${timeSlice.description}"/>
						<td><fmt:formatDate type="time" timeStyle="short" value="${timeSlice.startTime}"/>
						<c:set var="endDuration" value ='<%=Time.valueOf("" + (String)(pageContext.getAttribute("startTime").toString())) %>' />
						
						<c:if test="${not empty durationHr && durationHr ne 0}">
							<c:set var="endDuration" value='<%=Time.valueOf((((Time)pageContext.getAttribute("endDuration")).getHours() + Integer.valueOf((String)pageContext.getAttribute("durationHr")) ) + ":00:00") %>' />
						</c:if>
						
						<c:if test="${not empty durationMin && durationMin ne 0}">
							<c:set var="endDuration" value='<%=Time.valueOf( (((Time)pageContext.getAttribute("endDuration")).getHours()) + ":" 
							+  (((Time)pageContext.getAttribute("startTime")).getMinutes() + Integer.valueOf((String)pageContext.getAttribute("durationMin")) ) 
							+ ":00") %>' />
						</c:if>
						
						<c:if test="${endDuration ne startTime}">
							-<fmt:formatDate type="time" timeStyle="short" value="${endDuration}"/>
						</c:if>
							
						<c:if test="${not empty description}">
							<c:out value=" ${description}"></c:out>
						</c:if>
						
<!-- 						print a location -->
						<c:set var = "locDesc" value ="${timeSlice.location.description}"/>
						<c:set var = "locAddr" value ="${timeSlice.location.street}, ${timeSlice.location.city}, ${timeSlice.location.state}, ${timeSlice.location.zip}"/>
<%-- 						<c:out value="${timeSlice.location.description}"></c:out> --%>
						<c:if test="${not empty newLocation}">
						<!-- if the description is empty, provide default-->
						<c:if test="${empty locDesc}">
							<c:set var="locDesc" value="Location"/>
						</c:if>
						@ <a href="http://maps.google.com/?q=${locAddr}"> ${locDesc}</a>
<%-- 							<c:out value="@ <a href ="http://maps.google.com/?q=${locAddr}</a>'"></c:out> --%>
						</c:if>
						</td>
						
					</tr>
					
<!-- 					display notes -->
					<c:if test="true">
						<tr>
							<td></td>
							<td>
								<c:set var = "noteLine" value =""/>
								<c:set var = "noteData" value ="${timeSlice.notes}"/>
<%-- 								<c:out value="${timeSlice.notes}"></c:out> --%>
									<%
									String notes = (String)pageContext.getAttribute("noteData");
									System.out.println("note data:");
									System.out.println(notes);
										if (notes != null && notes.length() > 0) {
											String[] data = (notes).split("\n");
											int rows = data.length;
											for (int i = 0; i < rows; i++) {
									%>
									<c:set var="noteLine" value ='<%=data[i] %>' />
									<c:out value="${noteLine}"/>
									<br>
									<%
											}
										}
									%>
							</td>
						</tr>
					</c:if>
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
