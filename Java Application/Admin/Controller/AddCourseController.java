/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.AddStudentController.depts;
import static Controller.UpdateCourseController.lects;
import DAO.DBAddition;
import DAO.DBConnection;
import DAO.DBModification;
import DTO.Course;
import DTO.Department;
import DTO.Lecturer;
import DTO.Student;
import Main.AddItemController;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class AddCourseController implements Initializable {
    
    static ObservableList<Department> depts;
    static private int selectedDeptID;
    static private String selectedDept;
    
    static ObservableList<Lecturer> lects;
    static private int selectedLectID;
    static private String selectedLect;

    @FXML
    private TextField titleField;
    @FXML
    private TextField cHoursField;
    @FXML
    private Button addCourseButton;
    @FXML
    private ChoiceBox<String> deptBox;
    @FXML
    private ChoiceBox<String> lectBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Assign the departments names
        try {
            getDeptsNames();
            // Add the department names to the departmentNames observable list
            ObservableList<String> departmentNames = FXCollections.observableArrayList();
            depts.forEach(department -> departmentNames.add(department.getDeptName()));
            deptBox.setItems(departmentNames);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Handle selection change event and retrive the selected department and its corresponding ID
        deptBox.setOnAction(e -> {
            selectedDept = deptBox.getValue();
            Department selectedDepartment = depts.stream().filter(department -> department.getDeptName().equals(selectedDept)).findFirst().orElse(null);
            selectedDeptID = selectedDepartment.getDeptId();
        });
        
        // Assign the lecturers names
        try {
            getLectsNames();
            // Add the department names to the departmentNames observable list
            ObservableList<String> lecturersNames = FXCollections.observableArrayList();
            lects.forEach(lecturer -> lecturersNames.add(lecturer.getFullName()));
            lectBox.setItems(lecturersNames);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Handle selection change event and retrive the selected department and its corresponding ID
        lectBox.setOnAction(e -> {
            selectedLect = lectBox.getValue();
            Lecturer selectedLecturer = lects.stream().filter(lecturer -> lecturer.getFullName().equals(selectedLect)).findFirst().orElse(null);
            selectedLectID = selectedLecturer.getLectId();
        });
        
        addCourseButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            String courseTitle = titleField.getText();
            int cHours = parseInt(cHoursField.getText());
            Course newCourse = new Course(courseTitle, cHours, selectedDeptID, selectedLectID);
            try {
                if(DBAddition.addCourse(newCourse))
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Your course " + newCourse.getTitle()+ " was added successfuly.");
                    alert.showAndWait();
                    Stage stage = (Stage) (addCourseButton.getScene().getWindow());
                    stage.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    public void getDeptsNames() throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Departments", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Department> deptsList = new ArrayList<Department>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                deptsList.add(new Department(resultSet.getInt(1), resultSet.getString(2)));
            }
            depts = FXCollections.observableArrayList(deptsList);
        }
        statement.close();
    }
    
    // This method is reposnsible of retrieving all the lecturers data that is stored in the database
    public void getLectsNames() throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT LecturerID, FullName FROM Lecturers", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Lecturer> lectsList = new ArrayList<Lecturer>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                lectsList.add(new Lecturer(resultSet.getInt(1), resultSet.getString(2)));
            }
            lects = FXCollections.observableArrayList(lectsList);
        }
        statement.close();
    }
}
