<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Give It A Go App - Create Event</title>
<%@ include file="_header.jsp" %>
</head>
<body>
<!-- Navbar  -->
<%@ include file="_navbar.jsp" %>
<!-- Main Content -->
<div class="container my-3">
	<main>
		<h1>Create new event</h1>
		<div class="col-8">
			<c:set var="path" value="${pageContext.request.contextPath}" />
			<form method="POST" action="${path}/Create" enctype="multipart/form-data">
			 	<div class="form-group">
					<label for="name">Event name*</label>
			    	<input type="text" name="name" class="form-control" id="name" placeholder="Enter event name" required>
			  	</div>
			  	<div class="form-group">
			    	<label for="description">Event description*</label>
			    	<textarea type="text" name="description" class="form-control" id="description" placeholder="Enter event description" rows="5" required></textarea>
			  	</div>
			  	<div class="form-group">
			    	<label for="date">Event Date*</label>
			    	<input type="date" name="date" class="form-control" id="date" required></input>
			  	</div>
			  	<div class="form-group">
			    	<label for="photo">Recipe photo*</label>
			    	<input type="file" name="photo" class="form-control-file" id="photo" accept=".jpg, .jpeg, .png" required>
			  	</div>
			  	<p class="text-danger">*: required</p>
			  	<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</div>
	</main>
</div>
</body>
</html>