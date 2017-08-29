<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Wedding Tracker - List of Clients</title>
	</head>
	
	<body>
		<div>
			<h2>List of Clients</h2>
			
			<button></button>
			
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Address</th>
						<th>Phone Number</th>
						<th>Email</th>
						<th>Auto-Remind</th>
					</tr>
				</thead>
				
				<c:forEach items="${clientList}" var="client">
				<tr>
					<td><c:out value="${client.firstName} ${client.lastName}"/></td>
					<td><c:out value="${client.address}"/></td>
					<td><c:out value="${client.phoneNumber}"/></td>
					<td><c:out value="${client.email}"/></td>
					<td><c:out value="${client.autoRemind}"/></td>
					
					<td>
					<spring:url value="/editClient/${client.clientId}" var="clientUrl" />
						<nobr>
							<button onclick="location.href='${clientUrl}'">Edit</button>
						</nobr>
				</tr>
				</c:forEach>
				
			</table>
		</div>
		
		<div>
      	<a href="/WeddingTracker/">Home</a>
      </div>
	</body>
</html>
