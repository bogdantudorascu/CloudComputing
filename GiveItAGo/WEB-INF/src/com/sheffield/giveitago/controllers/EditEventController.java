package com.sheffield.giveitago.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.sheffield.giveitago.beans.EventBean;
import com.sheffield.giveitago.db.DBConnectionManager;

/**
 * Servlet implementation class CreateEventController
 */
@SuppressWarnings("serial")
@MultipartConfig
public class EditEventController extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEventController() {
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
		
		String applicationPath = request.getServletContext().getContextPath();
		String filePath = applicationPath + '/' + (String) getServletContext().getInitParameter("uploadPath");
		
		String address = null;
		String id = request.getPathInfo().substring(1);
		if (isInt(id) == true) {
			address = "/edit_event.jsp";
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
						event.setDate(rs.getDate(7));
					} while (rs.next());
					System.out.println(event.getDate());
					session.setAttribute("data", event);
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
		// Get DB info from context
		ServletContext ctx = request.getServletContext();
		DBConnectionManager db = (DBConnectionManager) ctx.getAttribute("db");
		Connection con = db.getConnection();
		HttpSession session = request.getSession();
		
		EventBean event = (EventBean) session.getAttribute("data");
		int id = event.getId();
		
		// Get input paramter
		String name =  request.getParameter("name");
		String description = request.getParameter("description");
		String dateStr = request.getParameter("date");
		Part photo = request.getPart("photo");
		
		// Convert date (string) to date (java.sql.Date)
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date dateSql = new java.sql.Date(date.getTime());	
		
		String applicationPath = request.getServletContext().getRealPath("");
		String uploadPath = (String) getServletContext().getInitParameter("uploadPath");
		String fileName = photo.getSubmittedFileName();
		String filePath = applicationPath + File.separator + uploadPath;
		
		File saveDir = new File(filePath);
		if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
		photo.write(filePath + File.separator + fileName);
		System.out.println(filePath);
		// Save event date to DB
		String query = "UPDATE event SET name=?, description=?, photo=?, date=? WHERE id=" + id;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, fileName);
			ps.setDate(4, dateSql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("success", "Successfully edit event");
		response.sendRedirect(request.getContextPath());
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
