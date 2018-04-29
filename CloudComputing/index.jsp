<jsp:include page="components/header.jsp" >
  <jsp:param name="title" value="Cloud Computing" />
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


   <div class="container">
     <div class="row app-container">
       <div class="col-12 col-sm-3 app-preview">
         <img class="responsive" src="https://ihg.scene7.com/is/image/ihg/crowne-plaza-new-york-2533147354-4x3">
       </div>
       <div class="col-12 col-sm-9 app-description">
          <h3>Give it a Go!</h3>
          <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text
            ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not
            only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.</p>
          <a href="application.jsp"><button type="button" class="btn btn-primary">Login to use this app</button></a>
       </div>
     </div>
   </div>
   
  
   <div class="container">
     <div class="row app-container">
       <div class="col-12 col-sm-3 app-preview">
         <img class="responsive" src="https://www.dundalkdemocrat.ie/resizer/750/563/true/1502096456792.jpg--iddt_lotto_results_.jpg?1502096456000">
       </div>
       <div class="col-12 col-sm-9 app-description">
          <h3>Random number lottery</h3>
          <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text
            ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not
            only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.</p>
          <a href="application.jsp"><button type="button" class="btn btn-primary">Login to use this app</button></a>
       </div>
     </div>
   </div>







 <jsp:include page="components/footer.jsp" />