package com.aanglearning.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	
	private static Connection connection = null;
	private final static String ADDRESS = "jdbc:mysql://localhost";
	private final static String DATABASE = "shikshithadb";
	private final static String USER = "root";
	private final static String PASSWORD = "thisisntfunmysql";
	private final static String PORT = "3306";
	private final static String DRIVER = "com.mysql.jdbc.Driver";

	private final static String RDS_USERNAME = "shikshithamaster";
	private final static String RDS_PASSWORD = "thisisntfunmaster";
	private final static String RDS_HOSTNAME = "jdbc:mysql://shikshitha-identifier.cfgwfijr5mxp.us-west-2.rds.amazonaws.com";
	private final static String RDS_DB_NAME = "shikshithadb";

	private static void loadDriver() {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			errorHandler("Failed to load the driver " + DRIVER, e);
		}
	}

	private static void loadConnection() throws SQLException {
		connection = DriverManager.getConnection(getFormatedUrl(), USER, PASSWORD);
	}

	private static void loadAwsConnection() throws SQLException {
		connection = DriverManager.getConnection(getAwsFormatedUrl(), RDS_USERNAME, RDS_PASSWORD);
	}

	private static String getFormatedUrl() {
		return ADDRESS + ":" + PORT + "/" + DATABASE;
	}

	private static String getAwsFormatedUrl() {
		return RDS_HOSTNAME + ":" + PORT + "/" + RDS_DB_NAME;
	}

	public static Connection getRemoteConnection() throws SQLException {
		if (System.getenv("RDS_HOSTNAME") != null && connection != null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String dbName = System.getenv("RDS_DB_NAME");
				String userName = System.getenv("RDS_USERNAME");
				String password = System.getenv("RDS_PASSWORD");
				String hostname = System.getenv("RDS_HOSTNAME");
				String port = System.getenv("RDS_PORT");
				String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
				connection = DriverManager.getConnection(jdbcUrl, userName, password);
				return connection;
			} catch (ClassNotFoundException e) {
				errorHandler("Class Not Found Exception", e);
			}
		}
		return null;
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			loadDriver();
			loadAwsConnection();
		}
		return connection;
	}

	public static Connection getLocalConnection() throws SQLException {
		if (connection == null) {
			loadDriver();
			loadConnection(); 
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection == null) {
			errorHandler("No connection found", null);
		} else {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				errorHandler("Failed to close the connection", e);
			}
		}
	}

	private static void errorHandler(String message, Exception e) {
		if (e != null)
			System.out.println(e.getMessage());
	}
}
