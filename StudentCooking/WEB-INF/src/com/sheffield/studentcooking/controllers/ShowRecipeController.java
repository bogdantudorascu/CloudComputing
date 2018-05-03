package com.sheffield.studentcooking.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.studentcooking.beans.RecipeBean;
import com.sheffield.studentcooking.db.DBConnectionManager;

/**
 * Servlet implementation class ShowEventController
 */

@SuppressWarnings("serial")
public class ShowRecipeController extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowRecipeController() {
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
		
		// Retrieve upload path
		String applicationPath = request.getServletContext().getContextPath();
		String filePath = applicationPath + '/' + (String) getServletContext().getInitParameter("uploadPath");
		
		// Get recipe id from url;
		String id = request.getPathInfo().substring(1);
		if (isInt(id) == true) {
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
					session.setAttribute("error", "Recipe not found!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			session.setAttribute("error", "Invalid recipe ID!");
		}

		String address = "/recipe.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(request, response);
		session.removeAttribute("error");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
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
