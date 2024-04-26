package com.apex;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddUser
 */
public class AddUser extends HttpServlet {
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
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		/*
		 * String query = "Insert into user values('" + firstName + "','" + lastName +
		 * "','" + username + "','" + password + "')";
		 */
		// System.out.println(query);
		PrintWriter writer = response.getWriter();
		try {
			if (AddUser.validate(request)) {
				PreparedStatement statement = connection.prepareStatement("insert into user values(?,?,?,?)");
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, username);
				statement.setString(4, password);
				int result = statement.executeUpdate();
				response.setContentType("text/html");

				if (result == 1) {
					writer.append("User is added successfully.");
				} else {
					writer.append("Error adding user.");
				}
				writer.close();
				statement.close();
			} else {
				writer.print("Fields cannot be left empty");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean validate(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if ((firstName != null && !firstName.isEmpty()) && (lastName != null && !lastName.isEmpty())
				&& (username != null && !username.isEmpty()) && (password != null && !password.isEmpty())) {
			return true;
		}
		return false;

	}

}
