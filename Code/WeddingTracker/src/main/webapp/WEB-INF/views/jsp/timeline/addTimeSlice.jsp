<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
		
   <head>
      	<title>Wedding Tracker - Time Slice Added</title>
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
		            <td>${startTime}</td>
		         </tr>
		         <tr>
		            <td>Duration</td>
		            <td>${durationHr}Hr ${durationMin}Min</td>
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
      	<p>EventId: ${eventId}</p>
      	<ul>
      		<li><a href="/WeddingTracker/createTimeSlice/${eventId}">Add another Time Slice</a></li>
      		<li><a href="/WeddingTracker/listTimeSlices/${eventId}">View Timeline</a></li>
      		<li><a href="/WeddingTracker">Home</a></li>
    	</ul>	
      	</div>
	</body>
</html>