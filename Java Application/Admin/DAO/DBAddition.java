/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Course;
import DTO.Department;
import DTO.Lecturer;
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
    
    public static boolean addDept(Department newDept) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Departments(DepartmentName) VALUES(?)");
        statement.setString(1, newDept.getDeptName());
        int result = statement.executeUpdate();
        if(result == 1) {
            statement.close();
            return true;
        } else {
            statement.close();
            return false;
        }
    }
    
    public static boolean addLect(Lecturer newLect) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Lecturers(FullName, Salary, ContactEmail, OfficeRoom, DepartmentID)"
                                + "VALUES(?, ?, ?, ?, ?)");
        statement.setString(1, newLect.getFullName());
        statement.setInt(2, newLect.getSalary());
        statement.setString(3, newLect.getContactEmail());
        statement.setString(4, newLect.getOfficeRoom());
        statement.setInt(5, newLect.getDeptId());
        int result = statement.executeUpdate();
        if(result == 1) {
            System.out.println("Lecturer is added successfully.");
            statement.close();
            return true;
        } else {
            System.out.println("Lecturer cannot be added.");
            statement.close();
            return false;
        }
    }
    
    public static boolean addCourse(Course newCourse) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)"
                                + "VALUES(?, ?, ?, ?)");
        statement.setString(1, newCourse.getTitle());
        statement.setInt(2, newCourse.getCreditHours());
        statement.setInt(3, newCourse.getDeptId());
        statement.setInt(4, newCourse.getLectId());
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
