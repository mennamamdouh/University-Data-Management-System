/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DAO.DBModification;
import DTO.CourseDept;
import DTO.Lecturer;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class UpdateCourseController implements Initializable {
    
    static ObservableList<Lecturer> lects;
    static int courseId;
    static String courseLectName;
    private String selectedLect;
    private int selectedLectID;
    
    public void processCourseLect(CourseDept selectedCourse) {
        courseId = selectedCourse.getCourseId();
        courseLectName = selectedCourse.getLectName();
    }

    @FXML
    private ChoiceBox<String> lecturersList;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            // Call the methods which gets all the lecturers names and their corresponding IDs
            getLectsNames();
            
            // Add the department names to the departmentNames observable list
            ObservableList<String> lecturersNames = FXCollections.observableArrayList();
            lects.forEach(lecturer -> lecturersNames.add(lecturer.getFullName()));
            
            // Show data in the choice box
            lecturersList.setItems(lecturersNames);
            lecturersList.setValue(courseLectName);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Handle selection change event and retrive the selected lecturer and its corresponding ID
        lecturersList.setOnAction(e -> {
            selectedLect = lecturersList.getValue();
            courseLectName = selectedLect;
            Lecturer selectedLecturer = lects.stream().filter(department -> department.getFullName().equals(selectedLect)).findFirst().orElse(null);
            selectedLectID = selectedLecturer.getLectId();
        });
        
        // Handle the save button to update the selected option in the database through the DBModification class
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBModification.updateCourse(courseId, selectedLectID);
                    Stage stage = (Stage) (saveButton.getScene().getWindow());
                    stage.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
