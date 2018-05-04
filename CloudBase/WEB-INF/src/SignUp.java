import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignUp extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("username") != null) {
			request.setAttribute("error", "You already have an account!");
			RequestDispatcher rd = request.getRequestDispatcher("/Index");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
			rd.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String originalPassword = request.getParameter("password");

			String securedPassword = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));

			String sql = "INSERT INTO users(email,username,password) VALUES (?,?,?)";
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USER,
					Constants.DATABASE_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, username);
			ps.setString(3, securedPassword);
			int success = ps.executeUpdate();

			if (success > 0) {
				request.setAttribute("success", "You have registered successfully!");
				RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("error", "There was a problem with your registration!");
				request.setAttribute("username", username);
				request.setAttribute("email", email);
				request.setAttribute("password", originalPassword);
				RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (ps != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}

	}

}
