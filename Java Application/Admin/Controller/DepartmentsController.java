/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DAO.DBDeletion;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class DepartmentsController implements Initializable {
    
    static ObservableList<StudentDept> depts;
    private static boolean deptsLoaded = false;
    private static boolean selected = false;
    StudentDept selectedDept;
    
    @FXML
    private TableView<StudentDept> deptsTable;
    @FXML
    private TableColumn<StudentDept, String> deptColumn;
    @FXML
    private TableColumn<StudentDept, Integer> numberOfStudents;
    @FXML
    private Button addDeptButton;
    @FXML
    private Button deleteDeptButton;

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
        
        Label noContentLabel = new Label("No departments.");
        deptsTable.setPlaceholder(noContentLabel);
        
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
        
        addDeptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddDepartment.fxml"));
                        AnchorPane updateStudentScene = loader.load();
                        Stage blockingWindow = new Stage();
                        blockingWindow.initModality(Modality.APPLICATION_MODAL);
                        blockingWindow.getIcons().add(new Image("/resources/logo.png"));
                        blockingWindow.setTitle("Add Department");
                        blockingWindow.setScene(new Scene(updateStudentScene));
                        blockingWindow.showAndWait();
                        // Refresh the students list
                        detDepts();
                        deptsTable.setItems(depts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        // Make the table's rows clickable to be able to pick a department to be removed
        deptsTable.setRowFactory(new Callback<TableView<StudentDept>, TableRow<StudentDept>>() {
            @Override
            public TableRow<StudentDept> call(TableView<StudentDept> tv) {
                TableRow<StudentDept> userRow = new TableRow<>();
                userRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 1 && (!userRow.isEmpty())) {
                            selected = true;
                            selectedDept = userRow.getItem();
                        }
                    }
                });
                return userRow ;
            }
        });
        
        deleteDeptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("You're about to delete department " + selectedDept.getDeptName()+ ". Click OK to continue.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Department");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK){
                                try {
                                    // Perform the delete action for the student
                                    Boolean isDeleted = DBDeletion.deleteDept(selectedDept);
                                    if(isDeleted) {
                                        // Refresh the students list
                                        detDepts();
                                        deptsTable.setItems(depts);
                                    } else {
                                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                        alert2.setContentText("You cannot delete this department as it still has students, lecturers, or courses.");
                                        Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                                        stage2.setTitle("Delete Department");
                                        stage2.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                                        alert2.showAndWait();
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(StudentsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please click on a department to delete.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Department");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
    }
    
    public static void detDepts() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM departments_info", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<StudentDept> deptsList = new ArrayList<StudentDept>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                StudentDept dept = new StudentDept(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
                );
                deptsList.add(dept);
            }
            depts = FXCollections.observableArrayList(deptsList);
        }
        statement.close();
    }
    
}
