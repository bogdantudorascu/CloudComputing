package com.sheffield.giveitago.controllers;

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

import com.sheffield.giveitago.beans.EventBean;
import com.sheffield.giveitago.db.DBConnectionManager;

/**
 * Servlet implementation class EventController
 */
@SuppressWarnings("serial")
public class EventController extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventController() {
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
		
		String query = "SELECT * FROM event";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			List <EventBean> data = new ArrayList<EventBean>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EventBean event = new EventBean();
				event.setId(rs.getInt(1));
				event.setName(rs.getString(2));
				event.setDescription(rs.getString(3));
				event.setPhoto(filePath + "/" + rs.getString(4));
				event.setDate(rs.getDate(7));
				data.add(event);
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
