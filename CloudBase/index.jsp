<%@page import="java.util.ArrayList" %>

<jsp:include page="components/header.jsp" >
  <jsp:param name="title" value="CloudBase Home Page" />
 </jsp:include>
    
 <jsp:include page="components/navbar.jsp" />
 

  <div class="container">
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
</div>

<% if(session.getAttribute("role")!= null && session.getAttribute("role").equals("1")) {%>
	<div class="container text-center">
		<button class="btn btn-primary hidden-form" id="create-app-button">Show create app form </button>
		<button class="btn btn-primary hidden-form" id="send-peanuts-button">Show peanuts form</button>
	</div>
	<div class="container add-app-form">
     <form action="CreateApp" method="POST">
	 <div class="form-group" >
		<label for="name">Name*</label>
		<input type="text" name="name" class="form-control" id="name" required placeholder="Application display name">
	  </div>
	  <div class="form-group" >
		<label for="folder_name">Folder Name*</label>
		<input type="text" name="folder_name" class="form-control" id="folder_name" required placeholder="Application directory on the server">
	  </div>
	  <div class="form-group" >
		<label for="url">Link*</label>
		<input type="text" name="url" class="form-control" id="url" required placeholder="Full URL to the application">
	  </div>
	  <div class="form-group" >
		<label for="img">Image Url</label>
		<input type="text" name="img" class="form-control" id="img" placeholder="Image preview URL">
	  </div>
	  <div class="form-group" >
		<label for="price">Price*</label>
		<input type="text" name="price" class="form-control" required id="price" placeholder="Price per use">
	  </div>
	  <div class="form-group" >
		<label for="description">Description</label>
		<textarea  name="description" class="form-control" id="description" required placeholder="Enter App Description"></textarea>
	  </div>
	  <div class="form-group" >
		<label for="developer">Developer*</label>
		<input  name="developer" class="form-control" id="developer" required placeholder="Application developer username"></textarea>
	  </div>
	  <div class="form-group" >
		<label for="salary">Developer salary*</label>
		<input  name="salary" class="form-control" id="salary" required placeholder="How many peanuts the developer gets per app use"></textarea>
	  </div>
	  
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
   </div>
   <div class="container send-peanuts-form">
     <form action="SendPeanuts" method="POST">
	 <div class="form-group" >
		<label for="username">Username*</label>
		<input type="text" name="username" class="form-control" id="username" required placeholder="Receiver's username">
	  </div>
	  <div class="form-group" >
		<label for="amount">Amount*</label>
		<input type="text" name="amount" class="form-control" id="amount" required placeholder="Peanuts amount">
	  </div>
	  <div class="form-group" >
		<label for="reason">Reason*</label>
		<input type="text" name="reason" class="form-control" id="reason" required placeholder="Reason">
	  </div>
	  
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
   </div>
<% }%>


<%  
ArrayList<ArrayList<String>> rs = new  ArrayList<ArrayList<String>>(); 
	rs = ( ArrayList<ArrayList<String>>) request.getAttribute("result");	
	if(rs == null || rs.isEmpty()){ 
%>
<div class="container text-center apps">
		<h5> No app added yet!</h5>
</div>
<% } else { %>
<div class="container apps">
	<%for(int i=0; i<rs.size(); i++){%> 
   	 <div class="row app-container">
   	 	<% ArrayList<String> row = (ArrayList<String>) rs.get(i);%>
        	<div class="col-12 col-sm-3 app-preview">
	         <img class="responsive" src="<%= row.get(4) %>">
	       </div>
	       <div class="col-12 col-sm-9 app-description">
	          <h3><%= row.get(1) %></h3>
	          <h5>Price per use: <%= row.get(5) %></h5>
	          <p><%= row.get(2) %></p>
	          <% if(session.getAttribute("username")!= null) {%>
	          	<% if(Integer.parseInt(session.getAttribute("peanuts").toString()) - Integer.parseInt(row.get(5)) > 0) {%>
		          <form action="OpenApp" method="POST" target='_blank'>
		          	  <div class="form-group" >
		          	  	<input name="id" type="hidden" value="<%=session.getAttribute("id")%>" class="form-control">
		          	  	<input name="url" type="hidden" value="<%=row.get(3)%>" class="form-control">
		          	  	<input name="price" type="hidden" value="<%=row.get(5)%>" class="form-control">
		          	  	<input name="name" type="hidden" value="<%=row.get(1)%>" class="form-control">
		          	  	<input name="developer" type="hidden" value="<%=row.get(7)%>" class="form-control">
		          	  	<input name="salary" type="hidden" value="<%=row.get(8)%>" class="form-control">
		          	  	<input name="folder_name" type="hidden" value="<%=row.get(6)%>" class="form-control">
					  </div>
						  <button type="submit" class="btn btn-success">Open App</button>
					</form>
				<%} else {%>
					<h5> You don't have enough money to open this app!</h5>
				<% } %> 
	           <%} else {%>
	           	<h5> <strong>Please log in in order to use this app!</strong></h5>
	           <% } %> 
	          <% if(session.getAttribute("role")!= null && session.getAttribute("role").equals("1")) {%>
	          	<p>Folder name <%=row.get(6)%> (admin only)</p>
	          	<p>Developer <%=row.get(7)%> (admin only)</p>
	          	<p>Salary <%=row.get(8)%> (admin only)</p>
	          	 <form action="RemoveApp" method="POST">
	          	  <div class="form-group" >
	          	  	<input name="id" type="hidden" value="<%=row.get(0)%>" class="form-control">
					<input name="name" type="hidden" value="<%=row.get(1)%>" class="form-control">
				  </div>
					  <button type="submit" class="btn btn-danger">Remove</button>
				</form>
	          <%} %>
	       </div>
		</div>
	<% } %>
</div>
<% } %>




 <jsp:include page="components/footer.jsp" />