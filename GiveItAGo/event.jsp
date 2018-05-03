<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Give It A Go App - Event</title>
<%@ include file="_header.jsp" %>
</head>
<body>
<%@ include file="_navbar.jsp" %>
<div class="container my-3">
	<main>
		<c:choose>
			<c:when test="${not empty error}">
				<p>${error}</p>
			</c:when>
			<c:otherwise>
				<h1>Event Detail</h1>
				<div class="row">
					<div class="col-4"><img src="${data.photo}" class="img-fluild img-thumbnail event-img" alt="event-image"></div>
					<div class="col-8">
						<h2>${data.name}</h2>
						<h4 class="mb-2 text-muted">Event date: <fmt:formatDate pattern="dd/MM/yyyy" value="${data.date}" /></h3>
						<p>${data.description}</p>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</main>
</div>
</body>
</html>