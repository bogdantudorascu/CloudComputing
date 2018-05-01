<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Give It A Go App</title>
<%@ include file="_header.jsp" %>
</head>
<body>
<!-- Navbar  -->
<%@ include file="_navbar.jsp" %>
<!-- Main Content -->
<div class="container my-3">
	<main>
		<c:if test="${not empty success}">
			<p>${success}</p>
		</c:if>
		<h1>Event Page</h1>
		<c:choose>
			<c:when test="${not empty data}">
				<c:forEach items="${data}" var="event">
					<div class="card my-3">
					  <div class="card-body">
					    <h5 class="card-title">${event.name}</h5>
					    <h6 class="card-subtitle mb-2 text-muted"><fmt:formatDate pattern="dd/MM/yyyy" value="${event.created_at}" /></h6>
					    <p class="card-text">${event.photo}</p>
					    <a href="#" class="card-link">Detail</a>
					  </div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<h2>There is no event at the moment!</h2>
			</c:otherwise>
		</c:choose>
	</main>
</div>
</body>
</html>