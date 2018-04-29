<jsp:include page="components/header.jsp" >
  <jsp:param name="title" value="Cloud Computing" />
 </jsp:include>
    
 <jsp:include page="components/navbar.jsp" />

 
   <div class="container text-center">
<% if(request.getAttribute("error") != null){ %>
	<div class="alert alert-danger alert-dismissible">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	  <strong>Error!</strong> <%= request.getAttribute("error") %>
	</div>
		
<% } else if(request.getAttribute("success") != null) { %>
	<div class="alert alert-success alert-dismissible">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>Success!</strong> <%= request.getAttribute("success") %>
	</div>
<% } else if(request.getAttribute("warning") != null) { %>
	<div class="alert alert-warning alert-dismissible">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	  <strong>Success!</strong> <%= request.getAttribute("warning") %>
	</div>
<% } %>
     <h4>Please fill in the required information</h4>
   </div>

   <div class="container">
     <form action="SignUp" method="POST">
	 <div class="form-group" >
		<label for="exampleUsername1">Username</label>
		<input type="text" name="username" class="form-control" id="exampleUsername1" aria-describedby="emailHelp" placeholder="Enter email">
	  </div>
	  <div class="form-group" >
		<label for="exampleInputEmail1">Email address</label>
		<input type="email" name="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
		<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
	  </div>
	  <div class="form-group">
		<label for="exampleInputPassword1">Password</label>
		<input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
	  </div>
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
   </div>

 <jsp:include page="components/footer.jsp" />
