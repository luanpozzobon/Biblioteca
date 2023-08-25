package com.luan.getlib.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class Database {
    private static final String PROPERTIES = "config.properties";

    public static Connection getConnection() throws SQLException{
        Properties prop = new Properties();
        try(InputStream inputStream = Database.class.getClassLoader().getResourceAsStream(PROPERTIES)){
            prop.load(inputStream);
            return DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a aquisição da URL: " + e);
            return null;
        }
    }
}
