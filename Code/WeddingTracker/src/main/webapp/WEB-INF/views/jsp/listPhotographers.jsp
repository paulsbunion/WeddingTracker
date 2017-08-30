<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Wedding Tracker - List of Photographers</title>
	</head>
	
	<body>
		<div>
			<h2>List of Photographers</h2>
			
			<spring:url value="/createPhotographer" var="createPhotographerUrl" />
			<button onclick="location.href='${createPhotographerUrl}'">New Photographer</button>
			
			<table>
				<thead>
					<tr>
						<th>Name</th>
					</tr>
				</thead>
				
				<c:forEach items="${photographerList}" var="photographer">
				<tr>
					<td><c:out value="${photographer.firstName} ${photographer.lastName}"/></td>
					
					<td>
					<spring:url value="/editPhotographer/${photographer.staffId}" var="editPhotographerUrl" />
					<spring:url value="/photographerEvents/${photographer.staffId}" var="viewPhotographerEventsUrl" />
						<nobr>
							<button onclick="location.href='${editPhotographerUrl}'">Edit</button>
							<button onclick="location.href='${viewPhotographerEventsUrl}'">View Events</button>
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
