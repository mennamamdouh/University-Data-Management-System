/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBModification {
    public static void updateStudent(int studentId, int deptID) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("DECLARE BEGIN modify_student_dept(?, ?); END;");
        statement.setInt(1, studentId);
        statement.setInt(2, deptID);
        boolean result = statement.execute();
        statement.close();
    }
    
    public static void updateLecturer(int lectId, int salary) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("DECLARE BEGIN update_lecturer_salary(?, ?); END;");
        statement.setInt(1, lectId);
        statement.setInt(2, salary);
        boolean result = statement.execute();
        statement.close();
    }
    
    public static void updateCourse(int courseId, int lectId) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("DECLARE BEGIN change_course_lecturer(?, ?); END;");
        statement.setInt(1, courseId);
        statement.setInt(2, lectId);
        boolean result = statement.execute();
        statement.close();
    }
}
