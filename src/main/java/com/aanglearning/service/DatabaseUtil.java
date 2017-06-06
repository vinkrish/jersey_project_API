package com.aanglearning.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseUtil {
	private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/thyreportdb");
        dataSource.setUsername("root");
        dataSource.setPassword("thisisntfunmysql");
    }

    private DatabaseUtil() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
