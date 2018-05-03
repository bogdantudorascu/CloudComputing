
<%
    String s=(String)getServletContext().getAttribute("username");
    out.println("value="+s);
%>
<% if (request.getAttribute("session") != null) { %>
	<h1>User here</h1>
<% } else  { %>
	<h1>User not here</h1>
<% } %>
