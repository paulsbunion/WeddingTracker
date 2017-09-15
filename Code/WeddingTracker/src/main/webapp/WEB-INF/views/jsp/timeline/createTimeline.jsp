<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Wedding Tracker - Add Timeline to Event</title>
		
		<!-- core -->
        <script type="text/javascript" src="../resources/core/js/jquery-3.2.1.js"></script>
<!--         <link rel="stylesheet" type="text/css" href="resources/core/css/jquery-ui.css"/> -->
        
        <!-- date picker -->
        <script type="text/javascript" src="../resources/core/js/jquery-ui.js"></script>
        <link rel="stylesheet" type="text/css" href="../resources/core/css/jquery-ui.css"/>
        
        <!-- time picker -->
        <script type="text/javascript" src="../resources/timePicker/js/jquery.ptTimeSelect.js"></script>
        <link rel="stylesheet" type="text/css" href="../resources/timePicker/css/jquery.ptTimeSelect.css"/>
	</head>
	<body>
		<div>
			<h2>Create a Timeline</h2>

			<form:form method = "post" action="/WeddingTracker/addTimeline">
				<table>
					<tr>
						<td>Change Start Time</td>
<%-- 						<td><form:input path="startTime"/></td> --%>
					<td>
						<div id="sample1" class="ui-widget-content">
							<form:input name="s1Time2" type="text" path="startTime" />
						</div>
					</td>
					<form:input type="hidden" path="eventId"/>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="Submit">
				</table>
			</form:form>
			
		</div>
		
		<script type="text/javascript">
        $(document).ready(function(){
            // find the input fields and apply the time select to them.
            $('#sample1 input').ptTimeSelect();
        });
    </script>
    
    <script type="text/javascript">
    	$(document).ready( function() {
    		$( "#datepicker" ).datepicker();
    	});
    </script>
	</body>
</html>