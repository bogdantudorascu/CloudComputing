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

public class CreateApp extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.sendRedirect(Constants.ROOT_URL);
    }


	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
			Connection conn = null;
			PreparedStatement ps = null;
			
		try {
			String name = request.getParameter("name");
			String img = request.getParameter("img");
			String url = request.getParameter("url");
			String description = request.getParameter("description");
			String price = request.getParameter("price");
			String folder_name = request.getParameter("folder_name");
			String developer = request.getParameter("developer");
			String salary = request.getParameter("salary");
			
			String sql = "INSERT INTO apps(name,url,img,description,price,folder_name,developer,salary) VALUES (?,?,?,?,?,?,?,?)";

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Constants.DATABASE_URL,Constants.DATABASE_USER,Constants.DATABASE_PASSWORD);
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,url);
            ps.setString(3,img);
            ps.setString(4,description);
            ps.setString(5,price);
            ps.setString(6,folder_name);
            ps.setString(7,developer);
            ps.setString(8,salary);
            
            int success = ps.executeUpdate();
            
            if(success>0) {
            	request.setAttribute("success", "You have added a new app!");
            	RequestDispatcher rd = request.getRequestDispatcher("/Index");
            	rd.forward(request, response);
            } else {
            	request.setAttribute("error", "There was a problem with your submit!");
            	RequestDispatcher rd = request.getRequestDispatcher("/Index");
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
