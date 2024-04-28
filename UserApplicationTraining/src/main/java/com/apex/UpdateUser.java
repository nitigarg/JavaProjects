package com.apex;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateUser
 */
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(context.getInitParameter("url"), context.getInitParameter("user"),
					context.getInitParameter("password"));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		/*
		 * String query = "Update user set password='"+password+"' where //
		 * username='"+username+"'";
		 */
		try {
			PreparedStatement statement = connection.prepareStatement("update user set password=? where username=?");
			statement.setString(1, password);
			statement.setString(2, username);
			int result = statement.executeUpdate();
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();

			if (result > 0) {

				writer.append("User is updated successfully.");
			} else {
				writer.append("Error updating user.");
			}
			writer.close();
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
