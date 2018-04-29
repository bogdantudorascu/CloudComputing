// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class SignIn extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.sendRedirect("index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Connection conn = null;
		PreparedStatement ps = null;
		HttpSession session = request.getSession(true);
		if(session.getAttribute("username") != null) {
			request.setAttribute("warning", "You are already logged in!");
        	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        	rd.forward(request, response);
		} else {
	        try {
	            String email = request.getParameter("email");
	            String originalPassword = request.getParameter("password");
	            String databasePassword = null, databaseUsername = null, databasePeanuts = null, databaseId = null;
	
	
	            String sql = "SELECT * FROM users WHERE email = ?";
	            Class.forName("com.mysql.jdbc.Driver");
	
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud","root","mysql");
	            ps = conn.prepareStatement(sql);
	            ps.setString(1,email);
	            ResultSet rs = ps.executeQuery();
	
	            while(rs.next()){
	            	databaseId = rs.getString("id");
	            	databaseUsername = rs.getString("username");
	                databasePassword = rs.getString("password");
	                databasePeanuts = rs.getString("peanuts");
	            }
	            
	
	
	            if(BCrypt.checkpw(originalPassword, databasePassword)){
	            	session.setAttribute("id", databaseId);
	            	session.setAttribute("username", databaseUsername);
	            	session.setAttribute("peanuts", databasePeanuts);
	            	
	            	request.setAttribute("success", "You have logged in successfully!");
	            	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	            	rd.forward(request, response);
	            } else {
	            	request.setAttribute("error", "Invalid credentials!");
	            	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	            	rd.forward(request, response);
	            }
	            
	        }catch(SQLException se){
	            //Handle errors for JDBC
	            se.printStackTrace();
	         }catch(Exception e){
	            //Handle errors for Class.forName
	            e.printStackTrace();
	         }finally{
	            //finally block used to close resources
	            try{
	               if(ps!=null)
	                  conn.close();
	            }catch(SQLException se){
	            }// do nothing
	            try{
	               if(conn!=null)
	                  conn.close();
	            }catch(SQLException se){
	               se.printStackTrace();
	            }//end finally try
	         }
		}
    }
}