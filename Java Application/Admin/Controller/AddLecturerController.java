/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBAddition;
import DAO.DBConnection;
import DTO.Department;
import DTO.Lecturer;
import static java.lang.Integer.parseInt;
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
public class AddLecturerController implements Initializable {
    
    static ObservableList<Department> depts;
    static private int selectedDeptID;
    static private String selectedDept;

    @FXML
    private TextField nameField;
    @FXML
    private TextField salaryField;
    @FXML
    private Button addLecturerButton;
    @FXML
    private ChoiceBox<String> deptBox;
    @FXML
    private TextField officeRoomField;

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
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Handle selection change event and retrive the selected department and its corresponding ID
        deptBox.setOnAction(e -> {
            selectedDept = deptBox.getValue();
            Department selectedDepartment = depts.stream().filter(department -> department.getDeptName().equals(selectedDept)).findFirst().orElse(null);
            selectedDeptID = selectedDepartment.getDeptId();
        });
        
        addLecturerButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            String lectName = nameField.getText();
            String lectContactEmail = lectName.toLowerCase().trim() + "@gmail.com";
            int lectSlary = parseInt(salaryField.getText());
            String lectOfficeRoom = officeRoomField.getText();
            Lecturer newLect = new Lecturer(lectName, lectSlary, lectContactEmail, lectOfficeRoom, selectedDeptID);
            try {
                if(DBAddition.addLect(newLect))
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Your lecturer " + newLect.getFullName() + " was added successfuly.");
                    alert.showAndWait();
                    Stage stage = (Stage) (addLecturerButton.getScene().getWindow());
                    stage.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddLecturerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    // This method is reposnsible of retrieving all the departments data that is stored in the database
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
}
