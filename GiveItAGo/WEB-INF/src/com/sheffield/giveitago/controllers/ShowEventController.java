package com.sheffield.giveitago.controllers;

import java.io.File;
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

import com.sheffield.giveitago.beans.EventBean;
import com.sheffield.giveitago.db.DBConnectionManager;

/**
 * Servlet implementation class ShowEventController
 */

@SuppressWarnings("serial")
public class ShowEventController extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowEventController() {
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
		
		String applicationPath = request.getServletContext().getRealPath("");
		String filePath = applicationPath + (String) getServletContext().getInitParameter("uploadPath");
		
		String id = request.getPathInfo().substring(1);
		if (isInt(id) == true) {
			String query = "SELECT * FROM event WHERE id=?";
			try {
				EventBean event = new EventBean();
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					do {
						event.setId(rs.getInt(1));
						event.setName(rs.getString(2));
						event.setDescription(rs.getString(3));
						event.setPhoto(filePath + "/" + rs.getString(4));
						event.setCreated_at(rs.getTimestamp(5));
					} while (rs.next());
					
					session.setAttribute("data", event);
				} else {
					session.setAttribute("error", "Event not found!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			session.setAttribute("error", "Invalid event ID!");
		}

		String address = "/event.jsp";
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
