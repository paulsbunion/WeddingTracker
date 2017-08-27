<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Wedding Tracker - Location Added</title>
   </head>

   <body>
   	<div>
      <h2>Submitted Location Information</h2>
      <table>
         <tr>
            <td>Address</td>
            <td>${street}, ${city}, ${state}, ${zip}</td>
         </tr>
         <tr>
            <td>Description</td>
            <td>${description}</td>
         </tr>
      </table>
      </div>  
      
      <div>
      	<a href="/WeddingTracker/">Home</a>
      </div>
   </body>
   
</html>