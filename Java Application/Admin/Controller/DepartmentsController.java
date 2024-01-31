/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DTO.StudentDept;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class DepartmentsController implements Initializable {
    
    static ObservableList<StudentDept> depts;
    private static boolean deptsLoaded = false;

    @FXML
    private TableView<StudentDept> deptsTable;
    @FXML
    private TableColumn<StudentDept, String> deptColumn;
    @FXML
    private TableColumn<StudentDept, Integer> numberOfStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the cell value factory for the department name column
        deptColumn.setCellValueFactory(
                new PropertyValueFactory<>("deptName")
        );
        
        // Set the cell value factory for the number of students column
        numberOfStudents.setCellValueFactory(
                new PropertyValueFactory<>("numberOfStudents")
        );
        
        if (!deptsLoaded) {
            try {
                detDepts();
                deptsLoaded = true;
                // Set the items after adding the columns
                deptsTable.setItems(depts);
            } catch (SQLException ex) {
                Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void detDepts() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM students_in_departments", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<StudentDept> deptsList = new ArrayList<StudentDept>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                StudentDept dept = new StudentDept(
                    resultSet.getString(1),
                    resultSet.getInt(2)
                );
                deptsList.add(dept);
            }
            depts = FXCollections.observableArrayList(deptsList);
        }
        statement.close();
    }
    
}
