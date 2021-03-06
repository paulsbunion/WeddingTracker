<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wedding Tracker - Create an Event</title>
		<!-- core -->
        <script type="text/javascript" src="resources/core/js/jquery-3.2.1.js"></script>
<!--         <link rel="stylesheet" type="text/css" href="resources/core/css/jquery-ui.css"/> -->
        
        <!-- date picker -->
        <script type="text/javascript" src="resources/core/js/jquery-ui.js"></script>
        <link rel="stylesheet" type="text/css" href="resources/core/css/jquery-ui.css"/>
        
        <!-- time picker -->
        <script type="text/javascript" src="resources/timePicker/js/jquery.ptTimeSelect.js"></script>
        <link rel="stylesheet" type="text/css" href="resources/timePicker/css/jquery.ptTimeSelect.css"/>
	</head>
	
	<body>
		<div>
			<h2>Create Event</h2>
			
			<form:form method="POST" action="/WeddingTracker/addEvent" commandName="event">
				<table>
					<tr>
						<td><form:label path = "eventName">Event Name</form:label></td>
						<td><form:input path = "eventName" /></td>
						<td><form:errors path= "eventName" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Event Type</td>
						<td><form:select path = "eventType" items = "${eventTypeList}" /> </td>
						<td><form:errors path= "eventType" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path = "eventDate">Date</form:label></td>
						<td><form:input id="datepicker" path = "eventDate" type="text"/></td>
						<td><form:errors path= "eventDate" cssClass="error" /></td>
					</tr>
					
					<tr>
						<td><form:label path = "startTime">Start Time</form:label></td>
						<td>
							<div id="sample1" class="ui-widget-content">
								<form:input name="s1Time2" type="text" path = "startTime" />
							</div>
						</td>
					</tr>
					 
<!-- 					<tr> -->
<%-- 						<td><form:label path = "duration">Duration (optional)</form:label></td> --%>
<%-- 						<td><form:input path = "duration" /></td> --%>
<!-- 					</tr> -->
					<tr>
						<td><form:label path = "extraCost">Additional Cost</form:label></td>
						<td><form:input path = "extraCost" /></td>
					</tr>
					<tr>
						<td><form:label path = "notes">Notes</form:label></td>
						<td><form:input path = "notes" /></td>
					</tr>
					<form:hidden path="duration"/>
					<tr>
						<td colspan="2">
							<input type="submit" name="submitCancelParam" value = "Submit">
							<input type="submit" name="submitCancelParam" value = "Cancel">
						</td>
<!-- 						<td colspan="1"> -->
							
<!-- 						</td> -->
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