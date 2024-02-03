/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBAddition {
    public static boolean addStudent(Student newStudent) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Students(FullName, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID) VALUES(?, ?, ?, ?, ?, ?)");
        statement.setString(1, newStudent.getFullName());
        statement.setString(2, newStudent.getGender());
        statement.setDate(3, newStudent.getDateOfBirth());
        statement.setString(4, newStudent.getPersonalPhoto());
        statement.setString(5, newStudent.getContactNumber());
        statement.setInt(6, newStudent.getDeptId());
        int result = statement.executeUpdate();
        if(result == 1) {
            statement.close();
            return true;
        } else {
            statement.close();
            return false;
        }
    }
}
