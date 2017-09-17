<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
   <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Wedding Tracker - EventType Added</title>
   </head>

   <body>
   	<div>
      <h2>Submitted EventType Information</h2>
      <table>
         <tr>
            <td>Event Type Name</td>
            <td>${eventType}</td>
         </tr>
         <tr>
            <td>Base Cost</td>
            <td>${baseCost}</td>
         </tr>
      </table>
      </div>  
      
      <div>
      	<ul>
	      	<li><a href="/WeddingTracker/listEventTypes">List Event Types</a></li>
	      	<li><a href="/WeddingTracker/listEvents">List Events</a></li>
	      	<li><a href="/WeddingTracker/">Home</a></li>
      	</ul>
      	
      </div>
   </body>
   
</html>