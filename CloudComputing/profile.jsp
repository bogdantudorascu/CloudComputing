<%@page import="java.util.ArrayList" %>

<jsp:include page="components/header.jsp" >
  <jsp:param name="title" value="Cloud Computing" />
 </jsp:include>
    
 <jsp:include page="components/navbar.jsp" />
   

 
   
	
<% if(session == null || session.getAttribute("username") == null) {%>
	<div class="container text-center">
		<h4> Please log in first! </h4>
	</div>
<% } else { %>

	<div class="container text-center">

     <h4>Change your password</h4>
     
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
   
   <div class="container">
     <form action="ChangePassword" method="POST">
	  
	  <div class="form-group">
		<label for="exampleInputPassword2">New Password</label>
		<input type="password" name="new-password" class="form-control" id="exampleInputPassword2" required placeholder="New Password">
	  </div>
	  
	  <div class="form-group">
		<label for="exampleInputPassword3">Confirm Password</label>
		<input type="password" name="confirm-password" class="form-control" id="exampleInputPassword3" required placeholder="Confirm Password">
	  </div>
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
   </div>
   
   <div class="container text-center">
   		<h4>Past Transactions</h4>
   </div>
<%  

ArrayList<ArrayList<String>> rs = new  ArrayList<ArrayList<String>>(); 
	rs = ( ArrayList<ArrayList<String>>) request.getAttribute("result");	
	if(rs.isEmpty()){ 
%>
<div class="container text-center">
		<h5> No results!</h5>
</div>
<% } else { %>
<div class="container">
   <table class="table">
	<thead>
      <tr>
      	<th>App</th>
        <th>Amount</th>
        <th>Deduct</th>
        <th>Before</th>
        <th>After</th>
        <th>Date</th>
      </tr>
    </thead>
    <tbody>
	<%for(int i=0; i<rs.size(); i++){
	ArrayList<String> row = (ArrayList<String>) rs.get(i);
		if( Integer.parseInt(row.get(2)) == 0) { %> 
   	 <tr class="green-row">
   	 	<% } else { %>
   	 <tr class="red-row">
   		 <% }
   		 for(int j=0; j < row.size(); j++){ %>
   		 	<% if( j == 2 ) { %> 
		   	 	<% if( Integer.parseInt(row.get(j)) == 0) { %> 
				   	 <td>Added</td>
				   	 	<% } else { %>
				   	 <td>Deducted</td>
				   <% } %>
		   	 	<% } else { %>
		   	 <td><%= row.get(j) %></td>
		   	<% } %>
        	
		<% } %>
	</tr>
	<% } %>
	 </tbody>
  </table>
</div>
<% } %>
<% } %>
 <jsp:include page="components/footer.jsp" />
