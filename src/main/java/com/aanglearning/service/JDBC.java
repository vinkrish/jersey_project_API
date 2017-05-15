package com.aanglearning.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

	private static Connection connection = null;
    private final static String ADDRESS  = "jdbc:mysql://localhost";
    private final static String DATABASE = "thyreportdb";
    private final static String USER     = "root";
    private final static String PASSWORD = "thisisntfunmysql";
    private final static String PORT     = "3306";
    private final static String DRIVER   = "com.mysql.jdbc.Driver";
    
    private final static String RDS_USERNAME = "thyreportmaster";
    private final static String RDS_PASSWORD = "thyreportmasterpassword";
    private final static String RDS_HOSTNAME = "jdbc:mysql://aa1pdu7coqe6qol.cfgwfijr5mxp.us-west-2.rds.amazonaws.com";
    private final static String RDS_DB_NAME = "ebdb";

    private static void loadDriver() {
        try {
            Class.forName(DRIVER);
        }
        catch (Exception e) {
            errorHandler("Failed to load the driver " + DRIVER, e);
        }
    }
    
    private static void loadConnection() {
        try {
            connection = DriverManager.getConnection(getFormatedUrl(), USER, PASSWORD);
        }
        catch (SQLException e) {
            errorHandler("Failed to connect to the database " + getFormatedUrl(), e);         
        }
    }
    
    private static void loadAwsConnection() {
        try {
            connection = DriverManager.getConnection(getAwsFormatedUrl(), RDS_USERNAME, RDS_PASSWORD);
        }
        catch (SQLException e) {
            errorHandler("Failed to connect to the database " + getFormatedUrl(), e);         
        }
    }

    private static String getFormatedUrl() {
        return ADDRESS + ":" + PORT + "/" + DATABASE;
    }
    
    private static String getAwsFormatedUrl() {
    	return RDS_HOSTNAME + ":" + PORT + "/" + RDS_DB_NAME;
    }
   
    public static Connection getRemoteConnection() {
    	if (System.getenv("RDS_HOSTNAME") != null) {
            try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbName = System.getenv("RDS_DB_NAME");
            String userName = System.getenv("RDS_USERNAME");
            String password = System.getenv("RDS_PASSWORD");
            String hostname = System.getenv("RDS_HOSTNAME");
            String port = System.getenv("RDS_PORT");
            String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
            Connection con = DriverManager.getConnection(jdbcUrl, userName, password);
            return con;
          }
          catch (ClassNotFoundException e) { errorHandler("Class Not Found Exception", e); }
          catch (SQLException e) { e.printStackTrace(); }
          }
          return null;
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            loadDriver();
            loadAwsConnection();
        }
        return connection;
    }
    
    public static Connection getLocalConnection() {
        if (connection == null) {
            loadDriver();
            loadConnection();
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection == null) {
            errorHandler("No connection found", null);
        }
        else {
            try {
                connection.close();
                connection = null;
            }
            catch (SQLException e) {
                errorHandler("Failed to close the connection", e);
            }
        }
    }
    
    private static void errorHandler(String message, Exception e) {
        System.out.println(message);  
        if (e != null) System.out.println(e.getMessage());   
    }
	
}
