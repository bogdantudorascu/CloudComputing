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
import javax.servlet.http.HttpSession;

public class Profile extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("id") != null) {
			Connection conn = null;
			PreparedStatement ps = null;
			
			try {
				String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY timestamp DESC";
	
	            Class.forName("com.mysql.jdbc.Driver");
	
	            conn = DriverManager.getConnection(Constants.DATABASE_URL,Constants.DATABASE_USER,Constants.DATABASE_PASSWORD);
	            ps = conn.prepareStatement(sql);
	            ps.setString(1,session.getAttribute("id").toString());
	            
	            ResultSet rs = ps.executeQuery();
	            
	            ArrayList<ArrayList<String>> Rows = new ArrayList<ArrayList<String>>();

	            while (rs.next()){
	                ArrayList<String> row = new ArrayList<String>();
	                row.add(rs.getString("app"));
	                row.add(rs.getString("amount"));
	                row.add(rs.getString("deduct"));
	                row.add(rs.getString("before_amount"));
	                row.add(rs.getString("after_amount"));
	                row.add(rs.getString("timestamp"));
	                Rows.add(row);
	            }
	            
	            request.setAttribute("result", Rows);
            	RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
            	rd.forward(request, response);
	        	
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
			
		} else {
			request.setAttribute("error", "Please log in first!");
			RequestDispatcher rd = request.getRequestDispatcher("/");
	    	rd.forward(request, response);
		}
		
		
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			doGet(request,response);
	}

}
