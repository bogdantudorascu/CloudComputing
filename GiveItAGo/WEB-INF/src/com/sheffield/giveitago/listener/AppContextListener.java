package com.sheffield.giveitago.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.sheffield.giveitago.db.DBConnectionManager;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
		DBConnectionManager db = (DBConnectionManager) ctx.getAttribute("db");
		db.closeConnection();
		System.out.println("Connection to db is closed successfully.");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
		String dbUser = ctx.getInitParameter("dbUser");
		String dbPass = ctx.getInitParameter("dbPass");
		String url = ctx.getInitParameter("dbURL");
		
		DBConnectionManager db = new DBConnectionManager(url, dbUser, dbPass);
		ctx.setAttribute("db", db);
		System.out.println("Connected to db successfully.");
	}
	
}
