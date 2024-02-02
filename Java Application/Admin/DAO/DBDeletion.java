/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.StudentDept;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDeletion {
    public static void deleteStudent(StudentDept delStudent) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("DELETE FROM Students WHERE StudentID = ?");
        statement.setInt(1, delStudent.getStudentId());
        boolean result = statement.execute();
        statement.close();
    }
}
