import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;

public class Index extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
				
			System.out.println("asjdlkajsdlkjalsdkjlkadjslksdjaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");	
			String s=(String)getServletContext().getAttribute("username");
			System.out.println("value="+s);	
			
			System.out.println("got here");
        	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        	rd.forward(request, response);
		
	
    }
}
