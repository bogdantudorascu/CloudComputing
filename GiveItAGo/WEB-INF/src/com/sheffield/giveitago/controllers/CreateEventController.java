package com.sheffield.giveitago.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.sheffield.giveitago.db.DBConnectionManager;

/**
 * Servlet implementation class CreateEventController
 */
@SuppressWarnings("serial")
@MultipartConfig
public class CreateEventController extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateEventController() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = "/create_event.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = request.getServletContext();
		DBConnectionManager db = (DBConnectionManager) ctx.getAttribute("db");
		Connection con = db.getConnection();
		HttpSession session = request.getSession();
		
		String name =  request.getParameter("name");
		String description = request.getParameter("description");
		Part photo = request.getPart("photo");
		
		String applicationPath = request.getServletContext().getRealPath("");
		String fileName = photo.getSubmittedFileName();
		String filePath = applicationPath + File.separator + (String) getServletContext().getInitParameter("uploadPath");
		File saveDir = new File(filePath);
		if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
		photo.write(filePath + File.separator + fileName);
		System.out.println(filePath);
		String query = "INSERT INTO event (name, description, photo) values (?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, fileName);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("success", "Successfully added new event");
		response.sendRedirect(request.getContextPath());
	}

}
