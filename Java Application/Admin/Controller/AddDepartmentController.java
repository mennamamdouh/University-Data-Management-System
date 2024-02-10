/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBAddition;
import DTO.Department;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class AddDepartmentController implements Initializable {

    @FXML
    private Button addDeptButton;
    @FXML
    private TextField deptField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addDeptButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            String deptName = deptField.getText();
            Department newDept = new Department(deptName);
            try {
                if(DBAddition.addDept(newDept))
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Your department " + newDept.getDeptName()+ " was added successfuly.");
                    alert.showAndWait();
                    Stage stage = (Stage) (addDeptButton.getScene().getWindow());
                    stage.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddDepartmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
