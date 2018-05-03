import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveApp extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.sendRedirect(Constants.ROOT_URL);
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
			Connection conn = null;
			PreparedStatement ps = null;
			
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			
			String sql = " DELETE FROM apps WHERE id = ?";

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Constants.DATABASE_URL,Constants.DATABASE_USER,Constants.DATABASE_PASSWORD);
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            
            int success = ps.executeUpdate();
            
            if(success>0) {
            	request.setAttribute("success", "You have deleted *"+name+"* from the dashboard!");
            	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            	rd.forward(request, response);
            } else {
            	request.setAttribute("error", "There was a problem with your request!");
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
