<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		     </table>  
    	</div>
      
      	<div>
      	<ul>
      		<li><a href="/WeddingTracker/listTimeSlices/${eventId}">View Timeline</a></li>
      		<li><a href="/WeddingTracker/">Home</a></li>
    	</ul>	
      	</div>
	</body>