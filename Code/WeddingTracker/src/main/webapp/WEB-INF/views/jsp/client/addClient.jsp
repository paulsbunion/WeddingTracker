<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
   	<meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Wedding Tracker - Client Added</title>
   </head>

   <body>
   	<div>
      <h2>Submitted Client Information</h2>
      <table>
         <tr>
            <td>Client Name</td>
            <td>${firstName} ${lastName}</td>
         </tr>
         <tr>
            <td>Address</td>
            <td>${address}</td>
         </tr>
         <tr>
            <td>Phone Number</td>
            <td>${phoneNumber}</td>
         </tr>
         <tr>
            <td>Email</td>
            <td>${email}</td>
         </tr>
         <tr>
            <td>Auto Remind</td>
            <td>${autoRemind}</td>
         </tr>
      </table>
      </div>  
      
      <div>
      	<a href="/WeddingTracker/">Home</a>
      </div>
   </body>
   
</html>