/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DTO.CourseDept;
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
public class CoursesController implements Initializable {
    
    static ObservableList<CourseDept> courses;
    private static boolean coursesLoaded = false;

    @FXML
    private TableView<CourseDept> coursesTable;
    @FXML
    private TableColumn<CourseDept, String> courseColumn;
    @FXML
    private TableColumn<CourseDept, Integer> cHoursColumn;
    @FXML
    private TableColumn<CourseDept, String> deptColumn;
    @FXML
    private TableColumn<CourseDept, String> lectColumn;
    @FXML
    private TableColumn<CourseDept, Integer> numOfStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the cell value factory for the course name column
        courseColumn.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        
        // Set the cell value factory for the credit hours column
        cHoursColumn.setCellValueFactory(
                new PropertyValueFactory<>("creditHours")
        );
        
        // Set the cell value factory for the department name column
        deptColumn.setCellValueFactory(
                new PropertyValueFactory<>("deptName")
        );
        
        // Set the cell value factory for the lecturer name column
        lectColumn.setCellValueFactory(
                new PropertyValueFactory<>("lectName")
        );
        
        // Set the cell value factory for the number of students column
        numOfStudents.setCellValueFactory(
                new PropertyValueFactory<>("numberOfStudents")
        );
        
        if (!coursesLoaded) {
            try {
                getCourses();
                coursesLoaded = true;
                // Set the items after adding the columns
                coursesTable.setItems(courses);
            } catch (SQLException ex) {
                Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void getCourses() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM courses_info", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<CourseDept> coursesList = new ArrayList<CourseDept>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                CourseDept courseDept = new CourseDept(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
                );
                coursesList.add(courseDept);
            }
            courses = FXCollections.observableArrayList(coursesList);
        }
        statement.close();
    }
    
}
