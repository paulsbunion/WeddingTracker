<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Wedding Tracker - Time Slice Updated</title>
   </head>

   <body>
   	<div>
      <h2>Submitted Time Slice Information</h2>
      <table>
         <tr>
            <td>Description</td>
            <td>${description}</td>
            <td>Start Time</td>
            <td>${startTime}</td>
            <td>Duration</td>
            <td>${duration}</td>
            <td>Location</td>
            <td>${location}</td>
            <td>Client</td>
            <td>${client}</td>
         </tr>
         
      </table>
      </div>  
      
      <div>
      <ul>
      	<li><a href="/WeddingTracker/listTimeSlices/${eventId}">Timeline List</a></li>
      	<li><a href="/WeddingTracker/">Home</a></li>
      </ul>
      </div>
   </body>
   
</html>