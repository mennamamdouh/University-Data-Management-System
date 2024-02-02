/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mennatallah
 */
public class DBModification {
    public static void updateStudent(int studentId, int deptID) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("DECLARE BEGIN modify_student_dept(?, ?); END;");
        statement.setInt(1, studentId);
        statement.setInt(2, deptID);
        boolean result = statement.execute();
        statement.close();
    }
}
