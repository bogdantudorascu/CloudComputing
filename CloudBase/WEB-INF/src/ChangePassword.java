import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePassword extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.sendRedirect(Constants.ROOT_URL);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
			Connection conn = null;
			PreparedStatement ps = null;
			
	        String new_password = request.getParameter("new-password");
	        String confirm_password = request.getParameter("confirm-password");
	        
	        HttpSession session = request.getSession(false);
	        
			if(new_password.equals(confirm_password)) 
				try {
		            
		           
					String securedPassword = BCrypt.hashpw(new_password, BCrypt.gensalt(12));
					String sql = "UPDATE users SET password = ? WHERE username = ?";
		
		            Class.forName("com.mysql.jdbc.Driver");
		
		            conn = DriverManager.getConnection(Constants.DATABASE_URL,Constants.DATABASE_USER,Constants.DATABASE_PASSWORD);
		            ps = conn.prepareStatement(sql);
		            ps.setString(1,securedPassword);
		            ps.setString(2,session.getAttribute("username").toString());
		            
		            
		            int success = ps.executeUpdate();
		            
		            if(success>0) {
		            	request.setAttribute("success", "You have successfully changed your password!");
		            	RequestDispatcher rd = request.getRequestDispatcher("/Profile");
		            	rd.forward(request, response);
		            } else {
		            	request.setAttribute("error", "There was a problem with changing your password!");
		            	RequestDispatcher rd = request.getRequestDispatcher("/Profile");
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
		 else {
	     	request.setAttribute("error", "Your passwords do not match!");
	     	RequestDispatcher rd = request.getRequestDispatcher("/Profile");
	     	rd.forward(request, response);
	     }
	
	
	    }
	
}