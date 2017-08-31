<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Wedding Tracker - Event Updated</title>
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
<!--             <td>Event Type</td> -->
<%--             <td>${eventType}</td> --%>
<!--          </tr> -->
         <tr>
            <td>Date</td>
            <td>${eventDate}</td>
         </tr>
         <tr>
            <td>Start Time</td>
            <td>${startTime}</td>
         </tr>
         <tr>
            <td>Notes</td>
            <td>${notes}</td>
         </tr>
      </table>
      </div>  
      
      <div>
      <ul>
      	<li><a href="/WeddingTracker/listEvents">Event List</a></li>
      	<li><a href="/WeddingTracker/">Home</a></li>
      </ul>
      </div>
   </body>
   
</html>