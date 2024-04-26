package com.apex;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewUser
 */
public class ViewUser extends HttpServlet {
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
		try {
			PreparedStatement statement = connection.prepareStatement("select *from user");
			ResultSet rs = statement.executeQuery();
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();

			while (rs.next()) {

				writer.println(rs.getString("firstname"));
				writer.println(rs.getString("lastname"));
				writer.println(rs.getString("username"));
				writer.println(rs.getString("password"));
				writer.println("<br>");
			}
			writer.close();
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
