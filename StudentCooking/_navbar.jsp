<header>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
		 	<a class="navbar-brand" href="/StudentCooking">Student Cooking</a>
		 	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		   		<span class="navbar-toggler-icon"></span>
		 	</button>
		
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item active">
						<% if(getServletContext().getAttribute("username") != null) { %>
				    		<a class="nav-link" href="/StudentCooking/Create">Add new Recipe</a>				    		
				    	<% } %>
				   	</li>
			    </ul>
			    
			    <% if(getServletContext().getAttribute("username") != null) { %>
				    <ul class="navbar-nav">
				    	<li class="nav-item">
				    		<a class="nav-link" href="/CloudComputing">Back to Cloud Platform</a>
				    	</li>
				    </ul>
			    <% } %>
			 </div>
			 
			 
		</div>
	</nav>
</header>