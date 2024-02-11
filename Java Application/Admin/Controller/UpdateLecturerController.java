/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.UpdateStudentController.studentId;
import DAO.DBModification;
import DTO.LecturerDept;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class UpdateLecturerController implements Initializable {
    
    static int lectId;

    public void processLectSalary(LecturerDept updatedLect) {
        lectId = updatedLect.getLectId();
    }
    
    @FXML
    private Button saveButton;
    @FXML
    private TextField salaryField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Handle the save button to update the selected option in the database through the DBModification class
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int updatedSalary = parseInt(salaryField.getText());
                    DBModification.updateLecturer(lectId, updatedSalary);
                    Stage stage = (Stage) (saveButton.getScene().getWindow());
                    stage.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        });
    }    
    
}
