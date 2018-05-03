package com.sheffield.studentcooking.controllers;

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

import com.sheffield.studentcooking.db.DBConnectionManager;

/**
 * Servlet implementation class CreateEventController
 */
@SuppressWarnings("serial")
@MultipartConfig
public class CreateRecipeController extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateRecipeController() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = "/create_recipe.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get DB info from context
		ServletContext ctx = request.getServletContext();
		DBConnectionManager db = (DBConnectionManager) ctx.getAttribute("db");
		Connection con = db.getConnection();
		HttpSession session = request.getSession();
		
		// Get input paramter
		String name =  request.getParameter("name");
		String description = request.getParameter("description");
		String time = request.getParameter("time");
		Part photo = request.getPart("photo");
			
		// Get path to save photo
		String applicationPath = request.getServletContext().getRealPath("");
		String fileName = photo.getSubmittedFileName();
		String filePath = applicationPath + File.separator + (String) getServletContext().getInitParameter("uploadPath");
		File saveDir = new File(filePath);
		if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
		// Write photo to disk
		photo.write(filePath + File.separator + fileName);
		// Save new recipe to DB
		String query = "INSERT INTO recipes (name, description, time, photo) values (?, ?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, time);
			ps.setString(4, fileName);
			ps.executeUpdate();
			session.setAttribute("success", "Successfully added new recipe.");
		} catch (SQLException e) {
			session.setAttribute("error", "Something happened. Please try again!");
			e.printStackTrace();
		}
		response.sendRedirect("/StudentCooking");
	}

}
