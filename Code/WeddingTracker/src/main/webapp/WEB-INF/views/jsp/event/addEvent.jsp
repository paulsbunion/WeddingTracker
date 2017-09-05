<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
		
   <head>
      	<title>Wedding Tracker - Event Added</title>
   </head>

   <body>
   		
	   	<div>
	     	<h2>Submitted Event Information</h2>
	      	<table>
		         <tr>
		            <td>Event Name</td>
		            <td>${eventName}</td>
		         </tr>
		<!--          <tr> -->
		<!--             <td>Type</td> -->
		<%--             <td>${type}</td> --%>
		<!--          </tr> -->
		         <tr>
		            <td>Date</td>
		            <td>${eventDate}</td>
		         </tr>
		         <tr>
		            <td>Start Time</td>
		            <td>${startTime}</td>
<%-- 		            <td>${startTimeHr}:${startTimeMn}</td> --%>
		         </tr>
		         <tr>
		            <td>Duration</td>
		            <td>${duration}</td>
		         </tr>
		         <tr>
		            <td>Extra Cost</td>
		            <td>${extraCost}</td>
		         </tr>
		         <tr>
		            <td>Notes</td>
		            <td>${notes}</td>
		         </tr>
		     </table>  
    	</div>
      
      	<div>
      	<p>Eventid: ${eventId}</p>
      	<ul>
      		<li><a href="/WeddingTracker/createTimeline/${eventId}/${startTime}">Add a Timeline</a></li>    
      		<li><a href="/WeddingTracker">Home</a></li>
      	</ul>
      	</div>
	</body>
</html>
