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

public class OpenApp extends HttpServlet {
	
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
			String url = request.getParameter("url");
			String name = request.getParameter("name");
			String folder_name = request.getParameter("folder_name");
			String price = request.getParameter("price");
			String developer = request.getParameter("developer");
			String salary = request.getParameter("salary");
			
			
			HttpSession session = request.getSession(false);
			String old_amount = (String) session.getAttribute("peanuts");
			
			String new_amount = String.valueOf(Integer.parseInt(old_amount)-Integer.parseInt(price));
			
			String sql = "UPDATE users SET peanuts = ? WHERE id = ?";

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud","root","mysql");
            ps = conn.prepareStatement(sql);
            ps.setString(1,new_amount);
            ps.setString(2,id);
            
            int user_update = ps.executeUpdate();
            
            System.out.println("deducting peanuts from the user >> inserting transaction");
            java.util.Date date = new Date();
            Object timestamp = new java.sql.Timestamp(date.getTime());
            
            sql = "INSERT INTO transactions(user_id,amount,deduct,before_amount,after_amount,app,timestamp) VALUES (?,?,?,?,?,?,?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ps.setString(2,price);
            ps.setString(3,"1");
            ps.setString(4,old_amount);
            ps.setString(5,new_amount);
            ps.setString(6,name);
            ps.setObject(7, timestamp);
            
            int transactions_update = ps.executeUpdate();
            
            
            System.out.println("finding developer" + developer);
            sql = "SELECT * FROM users where username = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, developer);
            
            ResultSet rs = ps.executeQuery();
            int developer_old_peanuts = -1;
            int developer_id = -1;

            while (rs.next()){
            	System.out.println("developer old peanuts and id");
                developer_old_peanuts = rs.getInt("peanuts");
                developer_id = rs.getInt("id");
            }
            
            int salary_update = 0, developer_new_peanuts = 0;
            
            if(developer_old_peanuts >= 0) {
            	System.out.println("updating developers peanuts");
            	sql = "UPDATE users SET peanuts = ? WHERE username = ?";
                developer_new_peanuts = developer_old_peanuts + Integer.parseInt(salary);
                ps = conn.prepareStatement(sql);
                ps.setInt(1,developer_new_peanuts);
                ps.setString(2,developer);
                
                salary_update = ps.executeUpdate();
            }
            
            if(salary_update > 0 && developer_id > 0) {
	        	 date = new Date();
	             timestamp = new java.sql.Timestamp(date.getTime() + TimeUnit.SECONDS.toMillis(1));
            	sql = "INSERT INTO transactions(user_id,amount,deduct,before_amount,after_amount,app,timestamp) VALUES (?,?,?,?,?,?,?)";
                
                ps = conn.prepareStatement(sql);
                ps.setInt(1,developer_id);
                ps.setString(2,salary);
                ps.setString(3,"0");
                ps.setInt(4,developer_old_peanuts);
                ps.setInt(5,developer_new_peanuts);
                ps.setString(6,name);
                ps.setObject(7, timestamp);
                
                ps.executeUpdate();
            }
            
            
            
            
            if(user_update > 0 && transactions_update > 0) {
            	sql = "SELECT * FROM users where id = ? LIMIT 1";
                
                ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                
                rs = ps.executeQuery();

                while (rs.next()){
                	session.setAttribute("peanuts",rs.getString("peanuts"));
                }
            	
            	String username = (String) session.getAttribute("username");
            	ServletContext sc = getServletContext().getContext("/"+folder_name.trim());
            	   
            	sc.setAttribute("username", username);
                response.sendRedirect(url); 
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
