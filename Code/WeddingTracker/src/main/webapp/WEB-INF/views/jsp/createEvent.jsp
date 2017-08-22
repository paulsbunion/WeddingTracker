<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
	<head>
		<title>Wedding Tracker - Create an Event</title>
	</head>
	
	<body>
		<div>
			<h2>Create Event</h2>
			
			<form:form method="POST" action="/WeddingTracker/addEvent">
				<table>
					<tr>
						<td><form:label path = "eventName">Event Name</form:label></td>
						<td><form:input path = "eventName" /></td>
					</tr>
					<tr>
						<td>Event Type</td>
						<td><form:select path = "eventType" items = "${eventTypeList}" /> </td>
					</tr>
<!-- 					<tr> -->
<%-- 						<td><form:label path = "type">Event Type</form:label></td> --%>
<%-- 						<td><form:input path = "type" /></td> --%>
<!-- 					</tr> -->
					<tr>
						<td><form:label path = "eventDate">Date</form:label></td>
						<td><form:input path = "eventDate" type="date"/></td>
					</tr>
					<tr>
<%-- 						<td><form:label path = "startTimeHr">Start Time</form:label></td> --%>
<!-- 						<td> -->
<!-- 							<select name = "startTimeHr"> -->
<!-- 								<option value="1">1</option> -->
<!-- 								<option value="2">2</option> -->
<!-- 								<option value="3">3</option> -->
<!-- 								<option value="4">4</option> -->
<!-- 								<option value="5">5</option> -->
<!-- 								<option value="6">6</option> -->
<!-- 								<option value="7">7</option> -->
<!-- 								<option value="8">8</option> -->
<!-- 								<option value="9">9</option> -->
<!-- 								<option value="10">10</option> -->
<!-- 								<option value="11">11</option> -->
<!-- 								<option value="12">12</option> -->
<!-- 								<option value="13">13</option> -->
<!-- 								<option value="14">14</option> -->
<!-- 								<option value="15">15</option> -->
<!-- 								<option value="16">16</option> -->
<!-- 								<option value="17">17</option> -->
<!-- 								<option value="18">18</option> -->
<!-- 								<option value="19">19</option> -->
<!-- 								<option value="20">20</option> -->
<!-- 								<option value="21">21</option> -->
<!-- 								<option value="22">22</option> -->
<!-- 								<option value="23">23</option> -->
<!-- 								<option value="24">24</option> -->
								
<!-- 							</select>hour -->
<%-- 							<form:input path = "startTimeMn" size="2" maxlength="2"/>min --%>
<!-- 						</td> -->
							
<!-- 						</select> -->
						<td><form:label path = "startTime">Start Time</form:label></td>
						<td><form:input path = "startTime" /></td>
					</tr>
					
					<tr>
						<td><form:label path = "duration">Duration (optional)</form:label></td>
						<td><form:input path = "duration" /></td>
					</tr>
					<tr>
						<td><form:label path = "extraCost">Additional Cost</form:label></td>
						<td><form:input path = "extraCost" /></td>
					</tr>
					<tr>
						<td><form:label path = "notes">Notes</form:label></td>
						<td><form:input path = "notes" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value = "Submit">
						</td>
					</tr>
				</table>
			</form:form>
			
		</div>
	</body>
	
</html>