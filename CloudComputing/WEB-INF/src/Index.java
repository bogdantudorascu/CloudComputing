import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;

public class Index extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
			Connection conn = null;
			PreparedStatement ps = null;
			
		try {
			String sql = "SELECT * FROM apps";

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud","root","mysql");
            ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            ArrayList<ArrayList<String>> Rows = new ArrayList<ArrayList<String>>();

            while (rs.next()){
                ArrayList<String> row = new ArrayList<String>();
                row.add(rs.getString("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("description"));
                row.add(rs.getString("url"));
                row.add(rs.getString("img"));
                row.add(rs.getString("price"));
                row.add(rs.getString("folder_name"));
                row.add(rs.getString("developer"));
                row.add(rs.getString("salary"));
                Rows.add(row);
            }
            
            request.setAttribute("result", Rows);
        	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
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
			
		
	
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			doGet(request,response);
	}
}
