<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
   <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Wedding Tracker - Photographer Updated</title>
   </head>

   <body>
   	<div>
      <h2>Submitted Photographer Information</h2>
      <table>
         <tr>
            <td>Photographer Name</td>
            <td>${firstName} ${lastName}</td>
         </tr>
         
      </table>
      </div>  
      
      <div>
      <ul>
      	<li><a href="/WeddingTracker/listPhotographers">Photographer List</a></li>
      	<li><a href="/WeddingTracker/">Home</a></li>
      </ul>
      </div>
   </body>
   
</html>