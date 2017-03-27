package com.aanglearning.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

	private static Connection connection = null;
    private final static String ADDRESS   = "jdbc:mysql://localhost";
    private final static String DATABASE = "thyreportdb";
    private final static String USER     = "root";
    private final static String PASSWORD = "thisisntfunmysql";
    private final static String PORT     = "3306";
    private final static String DRIVER   = "com.mysql.jdbc.Driver";

    private static void loadDriver() {
        try {
            Class.forName(DRIVER);
        }
        catch (Exception e) {
            errorHandler("Failed to load the driver " + DRIVER, e);
        }
        
        /*try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }*/
    }
    
    private static void loadConnection() {
        try {
            connection = DriverManager.getConnection(getFormatedUrl(), USER, PASSWORD);
        }
        catch (SQLException e) {
            errorHandler("Failed to connect to the database " + getFormatedUrl(), e);         
        }
    }
    
    private static void errorHandler(String message, Exception e) {
        System.out.println(message);  
        if (e != null) System.out.println(e.getMessage());   
    }

    private static String getFormatedUrl() {
        //return ADDRESS + "/" + DATABASE;
        return ADDRESS + ":" + PORT + "/" + DATABASE;
    }
   
    public static Connection getConnection() {
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
	
}
