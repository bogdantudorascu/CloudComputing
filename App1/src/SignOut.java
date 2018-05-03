import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOut extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.sendRedirect("index.jsp");
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("username") != null)
			session.invalidate();
		
		session = request.getSession(false);
		if(session == null) {
			request.setAttribute("success", "You have successfully logged out!");
	    	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	    	rd.forward(request, response);
		} else {
			request.setAttribute("error", "We were unable to log you out!");
	    	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	    	rd.forward(request, response);
		}
		
		
    }
}
