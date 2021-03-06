<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Cooking - Edit recipe</title>
<%@ include file="_header.jsp" %>
</head>
<body>
<%@ include file="_navbar.jsp" %>
<div class="container my-3">
	<h1>Edit recipe form</h1>
	<div class="col-8">
		<c:set var="path" value="${pageContext.request.contextPath}" />
		<form method="POST" action="${path}/Edit" enctype="multipart/form-data">
		 	<div class="form-group">
				<label for="name">Recipe name</label>
		    	<input type="text" name="name" class="form-control" id="name" value="${data.name}" placeholder="Enter recipe name" required>
		  	</div>
		  	<div class="form-group">
		    	<label for="description">Recipe description</label>
		    	<textarea type="text" name="description" class="form-control" id="description" placeholder="Enter recipe description" rows="5" required>${data.description}</textarea>
		  	</div>
		  	<div class="form-group">
		    	<label for="time">Cooking Time</label>
		    	<input type="text" name="time" class="form-control" id="time" value="${data.time}" placeholder="Enter estimate cooking time " required></input>
	  		</div>
		  	<div class="row">
		  		<div class="col-3">
		  			<label>Current photo</label>
		  			<img src="${data.photo}" class="img-fluild img-thumbnail recipe-img-small" alt="recipe-image">
		  		</div>
		  		<div>
		  			<div class="form-group">
				    	<label for="photo">Recipe photo</label>
				    	<input type="file" name="photo" class="form-control-file" id="photo" accept=".jpg, .jpeg, .png" required>
				  	</div>
		  		</div>
		  	</div>
		  	<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
</div>
</body>
</html>