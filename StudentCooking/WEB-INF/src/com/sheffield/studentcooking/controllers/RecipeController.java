package com.sheffield.studentcooking.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.studentcooking.beans.RecipeBean;
import com.sheffield.studentcooking.db.DBConnectionManager;

/**
 * Servlet implementation class EventController
 */
@SuppressWarnings("serial")
public class RecipeController extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeController() {
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
		
		// Retrieve recipes from db
		String applicationPath = request.getServletContext().getContextPath();
		String filePath = applicationPath + '/' + (String) getServletContext().getInitParameter("uploadPath");
		
		String query = "SELECT * FROM recipes";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			List <RecipeBean> data = new ArrayList<RecipeBean>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RecipeBean recipe = new RecipeBean();
				recipe.setId(rs.getInt(1));
				recipe.setName(rs.getString(2));
				recipe.setDescription(rs.getString(3));
				recipe.setTime(rs.getString(4));
				recipe.setPhoto(filePath + "/" + rs.getString(5));
				data.add(recipe);
			}
			session.setAttribute("data", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String address = "/index.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(request, response);
		session.removeAttribute("success");
		
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
