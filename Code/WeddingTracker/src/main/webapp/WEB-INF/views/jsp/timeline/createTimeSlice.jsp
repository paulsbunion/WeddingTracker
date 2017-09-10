<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Wedding Tracker - Create a Time Slice</title>
		
		<!-- core -->
<script type="text/javascript" src="../resources/core/js/jquery-3.2.1.js"></script>
<!--         <link rel="stylesheet" type="text/css" href="resources/core/css/jquery-ui.css"/> -->

<!-- date picker -->
<script type="text/javascript" src="../resources/core/js/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="../resources/core/css/jquery-ui.css" />

<!-- time picker -->
<script type="text/javascript"
	src="../resources/timePicker/js/jquery.ptTimeSelect.js"></script>
<link rel="stylesheet" type="text/css"
	href="../resources/timePicker/css/jquery.ptTimeSelect.css" />
	</head>
	
	<body>
		<div>
			<h2>Create Time Slice</h2>
			
			<form:form method="POST" action="/WeddingTracker/addTimeSlice">
				<table>
					<tr>
						<td><form:label path = "description">Notes / Description</form:label></td>
						<td><form:input path = "description" /></td>
					</tr>
					<tr>
						<td>Start Time</td>
					<td>
						<div id="sample1" class="ui-widget-content">
							<form:input name="s1Time2" type="text" path="startTime" />
						</div>
					</td>
					</tr>
					<tr>
						<td>Duration</td>
<%-- 						<td><form:input path = "durationHr" />Hr</td> --%>
<%-- 						<td><form:input path = "durationMin" />Min</td> --%>
						<td><form:select path="durationHr" items = "${hrMap}" /> Hr
						<form:select path="durationMin" items = "${minMap}"/> Min</td>
					</tr>
					<tr>
						<td><form:label path = "location">Location</form:label></td>
						<td>
							<form:select path = "location">
								<form:option value="">&nbsp;</form:option>
								<form:options items = "${locationList}" />
							</form:select> 
						</td>
					</tr>
					<tr>
						<td><form:label path = "client">Client</form:label></td>
						<td>
							<form:select path = "client">
								<form:option value="">&nbsp;</form:option>
								<form:options items = "${clientList}"/>
							</form:select>
						</td>
							
					</tr>
					<tr>
						<td><form:label path = "photographers">Photographers</form:label></td>
						<td>
							<ul>
								<form:checkboxes element="li" path = "photographers" items = "${photographerList}" />
							</ul>
						</td>
					</tr>

					<form:input type="hidden" path="position"/>
					<form:input type="hidden" path="eventId"/>
					
					<tr>
						<td colspan="2">
							<input type="submit" value = "Submit">
						</td>
					</tr>
				</table>
			</form:form>
			
		</div>
		
		<script type="text/javascript">
		$(document).ready(function() {
			// find the input fields and apply the time select to them.
			$('#sample1 input').ptTimeSelect();
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datepicker").datepicker();
		});
	</script>
	</body>
	
</html>