/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DTO.StudentDept;
import DAO.DBModification;
import DTO.Department;
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
public class UpdateStudentController implements Initializable {

    static ObservableList<Department> depts;
    static int studentId;
    static String studentDeptName;
    private String selectedDept;
    private int selectedDeptID;
    
    public void processStudentDept(StudentDept studentDept) {
        studentId = studentDept.getStudentId();
        studentDeptName = studentDept.getDeptName();
    }
    
    @FXML
    private ChoiceBox<String> departmentsList;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Call the methods which gets all the departments names and their corresponding IDs
            getDeptsNames();
            
            // Add the department names to the departmentNames observable list
            ObservableList<String> departmentNames = FXCollections.observableArrayList();
            depts.forEach(department -> departmentNames.add(department.getDeptName()));
            
            // Show data in the choice box
            departmentsList.setItems(departmentNames);
            departmentsList.setValue(studentDeptName);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Handle selection change event and retrive the selected department and its corresponding ID
        departmentsList.setOnAction(e -> {
            selectedDept = departmentsList.getValue();
            studentDeptName = selectedDept;
            Department selectedDepartment = depts.stream().filter(department -> department.getDeptName().equals(selectedDept)).findFirst().orElse(null);
            selectedDeptID = selectedDepartment.getDeptId();
        });
        
        // Handle the save button to update the selected option in the database through the DBModification class
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBModification.updateStudent(studentId, selectedDeptID);
                    Stage stage = (Stage) (saveButton.getScene().getWindow());
                    stage.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
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