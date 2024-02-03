/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBAddition;
import DAO.DBConnection;
import DTO.Department;
import DTO.Student;
import Main.AddItemController;
import java.io.File;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddStudentController implements Initializable {
    
    static ObservableList<Department> depts;
    static private int selectedDeptID;
    static private String selectedDept;
    
    static ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
    static private String selectedGender;
    
    static String imageURI;

    @FXML
    private TextField nameField;
    @FXML
    private TextField numberField;
    @FXML
    private Button addStudentButton;
    @FXML
    private ChoiceBox<String> genderBox;
    @FXML
    private Button choosePhotoButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<String> deptBox;

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
        
        // Assign the genders
        genderBox.setItems(genders);
        // Handle selection change event and retrive the selected gender
        genderBox.setOnAction(e -> {
            selectedGender = genderBox.getValue();
        });
        
        addStudentPhoto();
        
        addStudentButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            String studentName = nameField.getText();
            String contactNumber = numberField.getText();
            // Get the picked date
            java.sql.Date sqlDate = null;
            LocalDate localDate = datePicker.getValue();
            if (localDate != null) {
                // Convert JavaFX LocalDate to java.sql.Date
                sqlDate = Date.valueOf(localDate);
            }
            Student newStudent = new Student(studentName, selectedGender, sqlDate, imageURI, contactNumber, selectedDeptID);
            try {
                if(DBAddition.addStudent(newStudent))
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Your student " + newStudent.getFullName() + " was added successfuly.");
                    alert.showAndWait();
                    Stage stage = (Stage) (addStudentButton.getScene().getWindow());
                    stage.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void addStudentPhoto(){
        choosePhotoButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Photo");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                imageURI = selectedFile.toURI().toString();
                int indexOfResourcesString = imageURI.indexOf("resources");
                imageURI = imageURI.substring(indexOfResourcesString);
                Image image = new Image(imageURI);
            }
        });
    }
}
