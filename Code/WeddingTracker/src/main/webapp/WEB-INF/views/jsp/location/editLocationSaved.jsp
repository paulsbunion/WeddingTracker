<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
   <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Wedding Tracker - Location Updated</title>
   </head>

   <body>
   	<div>
      <h2>Submitted Location Information</h2>
      <table>
         <tr>
            <td>Street Address</td>
            <td>${street}</td>
         </tr>
         <tr>
            <td>City</td>
            <td>${city}</td>
         </tr>
         <tr>
            <td>State</td>
            <td>${state}</td>
         </tr>
         <tr>
            <td>Zip</td>
            <td>${zip}</td>
         </tr>
         <tr>
            <td>Description</td>
            <td>${description}</td>
         </tr>
      </table>
      </div>  
      
      <div>
      <ul>
   		   <li><a href="/WeddingTracker/listLocations">List Locations</a></li>
   		   <li><a href="/WeddingTracker">Home</a></li>
   	  </ul>	
      </div>
   </body>
   
</html>