<nav class="navbar navbar-inverse">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/CloudComputing/">Student Lifestyle</a>
      </div>
      
      <div id="navbar" class="navbar-collapse collapse">
      <% if (session.getAttribute("username") == null) { %>
        <form action="SignIn" method="POST" class="navbar-form navbar-right">
          <div class="form-group">
            <input type="text" name="email" placeholder="Email" class="form-control">
          </div>
          <div class="form-group">
            <input type="password" name="password" placeholder="Password" class="form-control">
          </div>
          <button type="submit" class="btn btn-success">Sign in</button>
	   <a href="/CloudComputing/SignUp" class="btn btn-primary">Sign up</a>
        </form>
       <% } else {%>
       
       <form action="SignOut" method="POST" class="navbar-form navbar-right">
          <button type="submit" class="btn btn-success">Log out</button>
        </form>
        
        <div class="navbar-right navbar-form">
       	<span class="profile-status" >Hi, <%=session.getAttribute("username") %>, you have <%=session.getAttribute("peanuts") %> peanuts!</span>
        <a href="/CloudComputing/Profile" class="btn btn-primary">Profile</a>
       </div>
       
    	<% } %>
        
         
      </div><!--/.navbar-collapse -->
    </div>
  </nav>