/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mennatallah
 */
public class DBConnection {
    private volatile static Connection dbconnection;
    static String username;
    static String password;
    
    private DBConnection(){
        
    }
    
    public static Connection getConnection() throws SQLException{
        if(dbconnection == null)
            username = System.getenv("DB_USERNAME");
            password = System.getenv("DB_PASSWORD");
            dbconnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
        return dbconnection;
    }
}
