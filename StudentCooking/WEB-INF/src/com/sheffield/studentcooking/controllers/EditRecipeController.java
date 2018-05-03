package com.sheffield.studentcooking.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import com.sheffield.studentcooking.beans.RecipeBean;
import com.sheffield.studentcooking.db.DBConnectionManager;

/**
 * Servlet implementation class CreateEventController
 */
@SuppressWarnings("serial")
@MultipartConfig
public class EditRecipeController extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRecipeController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = request.getServletContext();
		DBConnectionManager db = (DBConnectionManager) ctx.getAttribute("db");
		Connection con = db.getConnection();
		HttpSession session = request.getSession();
		
		// Get upload path
		String applicationPath = request.getServletContext().getContextPath();
		String filePath = applicationPath + '/' + (String) getServletContext().getInitParameter("uploadPath");
		
		String address = null;
		// Get recipe id from url
		String id = request.getPathInfo().substring(1);
		// Check if id is valid integer
		if (isInt(id) == true) {
			address = "/edit_recipe.jsp";
			String query = "SELECT * FROM recipes WHERE id=?";
			try {
				RecipeBean recipe = new RecipeBean();
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					do {
						recipe.setId(rs.getInt(1));
						recipe.setName(rs.getString(2));
						recipe.setDescription(rs.getString(3));
						recipe.setTime(rs.getString(4));
						recipe.setPhoto(filePath + "/" + rs.getString(5));
					} while (rs.next());
					session.setAttribute("data", recipe);
				} else {
					session.setAttribute("error", "Event not found!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("/error.jsp");
		}
		
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
		
		// Get event id from session
		RecipeBean recipe = (RecipeBean) session.getAttribute("data");
		int id = recipe.getId();
		
		// Get input from form
		String name =  request.getParameter("name");
		String description = request.getParameter("description");
		String time = request.getParameter("time");
		Part photo = request.getPart("photo");
		
		// Get upload path
		String applicationPath = request.getServletContext().getRealPath("");
		String uploadPath = (String) getServletContext().getInitParameter("uploadPath");
		String fileName = photo.getSubmittedFileName();
		String filePath = applicationPath + File.separator + uploadPath;
		
		File saveDir = new File(filePath);
		if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
		// Write photo to disk
		photo.write(filePath + File.separator + fileName);
		
		// Save event date to DB
		String query = "UPDATE recipes SET name=?, description=?, time=?, photo=? WHERE id=" + id;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, time);
			ps.setString(4, fileName);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("success", "Successfully edit event");
		response.sendRedirect("/StudentCooking");
	}
	
	/**
	 * Check if a string is numeric
	 * @param String s 
	 * @return boolean
	 */
	protected boolean isInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}

}
