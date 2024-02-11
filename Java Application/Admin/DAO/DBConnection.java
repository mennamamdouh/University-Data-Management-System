/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mennatallah
 */
public class DBConnection {
    private volatile static Connection dbconnection;
    static String connection_string;
    static String username;
    static String password;
    
    private DBConnection(){
        
    }
    
    public static Connection getConnection() throws SQLException{
        if(dbconnection == null){
            Dotenv dotenv = Dotenv.configure().directory("/DAO/.env").load();
            connection_string = dotenv.get("CONNECTION_STRING");
            username = dotenv.get("DB_USERNAME");
            password = dotenv.get("DB_PASSWORD");
            dbconnection = DriverManager.getConnection(connection_string, username, password);
        }
        return dbconnection;
    }
}
