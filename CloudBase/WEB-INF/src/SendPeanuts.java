import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendPeanuts extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.sendRedirect(Constants.ROOT_URL);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
			String amount = request.getParameter("amount");
			String username = request.getParameter("username");
			String reason = request.getParameter("reason");
			
		
			Connection conn = null;
			PreparedStatement ps = null;
			
		try {
			String sql = "SELECT * FROM users where username = ?";
			Class.forName("com.mysql.jdbc.Driver");

			 conn = DriverManager.getConnection(Constants.DATABASE_URL,Constants.DATABASE_USER,Constants.DATABASE_PASSWORD);
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            String developer_id = "";
            int peanuts_before = -1;

            while (rs.next()){
                developer_id = rs.getString("id");
                peanuts_before = rs.getInt("peanuts");
            }
            
            if(peanuts_before > 0 ) {
            	
                int peanuts_after = peanuts_before + Integer.parseInt(amount);
                
                sql = "UPDATE users SET peanuts = ? WHERE id = ?";

                
                ps = conn.prepareStatement(sql);
                ps.setInt(1,peanuts_after);
                ps.setString(2,developer_id);
                
                int user_update = ps.executeUpdate();
                
                sql = "INSERT INTO transactions(user_id,amount,deduct,before_amount,after_amount,app) VALUES (?,?,?,?,?,?)";
                
                ps = conn.prepareStatement(sql);
                ps.setString(1,developer_id);
                ps.setString(2,amount);
                ps.setString(3,"0");
                ps.setInt(4,peanuts_before);
                ps.setInt(5,peanuts_after);
                ps.setString(6, reason);
                
                int transactions_update = ps.executeUpdate();
                
                
                if(user_update > 0 && transactions_update > 0) {
                	request.setAttribute("success", "Your gift has been successfully sent!");
    		     	RequestDispatcher rd = request.getRequestDispatcher("/Index");
    		     	rd.forward(request, response);
                } else {
                	request.setAttribute("error", "There was a problem with your request!");
                	RequestDispatcher rd = request.getRequestDispatcher("/Index");
                	rd.forward(request, response);
                }
                
            } else {
            	request.setAttribute("error", "No such user exists!");
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
