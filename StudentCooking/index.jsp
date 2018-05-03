<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Cooking</title>
<%@ include file="_header.jsp" %>
</head>
<body>
<%@ include file="_navbar.jsp" %>
<div class="container my-3">
	<c:if test="${not empty success}">
		<p>${success}</p>
	</c:if>
	<h1>Recipe Page</h1>
	<c:choose>
		<c:when test="${not empty data}">
			<c:forEach items="${data}" var="recipe">
				<div class="card my-3">
				  <div class="card-body">
				  	<div class="row">
				  		<div class="col-2">
				  			<img src="${recipe.photo}" class="img-fluild img-thumbnail recipe-img-small" alt="${recipe.photo}">
				  		</div>
				  		<div>
						    <h3 class="card-title">${recipe.name}</h3>
						    <h6 class="card-subtitle mb-2 text-muted">Cooking time: ${recipe.time}</h6>
						    <a href="/StudentCooking/Recipe/${recipe.id}" class="card-link">Detail</a>
				  		</div>
				  	</div>
				  </div>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<h2>There is no recipe at the moment!</h2>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>