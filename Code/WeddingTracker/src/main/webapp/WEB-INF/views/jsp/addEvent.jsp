<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Wedding Tracker - Event Added</title>
   </head>

   <body>
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
            <td>${startTimeHr}:${startTimeMn}</td>
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
   </body>
   
</html>
