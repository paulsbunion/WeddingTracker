<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Wedding Tracker - Edit Time Slice Saved</title>
</head>
<body>
	   	<div>
	     	<h2>Submitted Time Slice Information</h2>
	      	<table>
		         <tr>
		            <td>Notes / Description</td>
		            <td>${description}</td>
		         </tr>
		         <tr>
		            <td>Start Time</td>
		            <td><fmt:formatDate type="time" timeStyle="short" value="${startTime}" /></td>
		         </tr>
		         <tr>
		            <td>Duration</td>
		            <td>${timeChunk.durationHr}Hr ${timeChunk.durationMin}Min</td>
		         </tr>
		         <tr>
		            <td>Location</td>
		            <td>${location}</td>
		         </tr>
		         <tr>
		            <td>Client</td>
		            <td>${client}</td>
		         </tr>
		         <tr>
		            <td>Photographers</td>
		            <td>
		            	<ul>
		            		<c:forEach var = "setValue" items = "${photographers}" >
		            			<li>${setValue}</li>
		            		</c:forEach>
		            </td>
		         </tr>
		         <tr>
					<td>Notes</td>
<%-- 					<td>${timeChunk.notes}</td> --%>
					<td>
					<c:set var = "noteLine" value =""/>
						<%
							String str = (String)request.getAttribute("notes");
							if (str != null) {
								String[] data = (str).split("\n");
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
		     </table>  
    	</div>
      
      	<div>
      	<ul>
      		<li><a href="/WeddingTracker/listTimeSlices/${eventId}">View Timeline</a></li>
      		<li><a href="/WeddingTracker/">Home</a></li>
    	</ul>	
      	</div>
	</body>