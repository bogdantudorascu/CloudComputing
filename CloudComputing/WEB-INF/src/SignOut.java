import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOut extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.sendRedirect(Constants.ROOT_URL);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("username") != null)
			session.invalidate();
		
		session = request.getSession(false);
		if(session == null) {
	    	
	    	Connection conn = null;
			PreparedStatement ps = null;
			
		try {
			String sql = "SELECT * FROM apps";

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud","root","mysql");
            ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
    	    	ServletContext sc = getServletContext().getContext("/"+rs.getString("folder_name").trim());
            	sc.removeAttribute("username");
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
			
			System.out.println("will log out");
			request.setAttribute("success", "You have successfully logged out from all apps and the dashboard!");
	    	RequestDispatcher rd = request.getRequestDispatcher("/Index");
	    	rd.forward(request, response);
		} else {
			request.setAttribute("error", "We were unable to log you out!");
	    	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	    	rd.forward(request, response);
		}
		
		
    }
}
