/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DAO.DBDeletion;
import DTO.LecturerDept;
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
public class LecturersController implements Initializable {
    
    static ObservableList<LecturerDept> lecturers;
    private static boolean lectsLoaded = false;
    private static boolean selected = false;
    LecturerDept selectedLecturer;

    @FXML
    private TableView<LecturerDept> lecturersTable;
    @FXML
    private TableColumn<LecturerDept, String> lectColumn;
    @FXML
    private TableColumn<LecturerDept, Integer> salaryColumn;
    @FXML
    private TableColumn<LecturerDept, String> emailColumn;
    @FXML
    private TableColumn<LecturerDept, String> officeRoomColumn;
    @FXML
    private TableColumn<LecturerDept, String> deptColumn;
    @FXML
    private TableColumn<LecturerDept, Integer> numOfCourses;
    @FXML
    private Button addLecturerButton;
    @FXML
    private Button deleteLecturerButton;
    @FXML
    private Button updateLecturerButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set the cell value factory for the course name column
        lectColumn.setCellValueFactory(
                new PropertyValueFactory<>("fullName")
        );
        
        // Set the cell value factory for the course name column
        salaryColumn.setCellValueFactory(
                new PropertyValueFactory<>("salary")
        );
        
        // Set the cell value factory for the course name column
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("contactEmail")
        );
        
        // Set the cell value factory for the course name column
        officeRoomColumn.setCellValueFactory(
                new PropertyValueFactory<>("officeRoom")
        );
        
        // Set the cell value factory for the course name column
        deptColumn.setCellValueFactory(
                new PropertyValueFactory<>("deptName")
        );
        
        // Set the cell value factory for the course name column
        numOfCourses.setCellValueFactory(
                new PropertyValueFactory<>("numberOfCourses")
        );
        
        if (!lectsLoaded) {
            try {
                getLecturers();
                lectsLoaded = true;
                // Set the items after adding the columns
                lecturersTable.setItems(lecturers);
            } catch (SQLException ex) {
                Logger.getLogger(LecturersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        addLecturerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddLecturer.fxml"));
                        AnchorPane updateStudentScene = loader.load();
                        Stage blockingWindow = new Stage();
                        blockingWindow.initModality(Modality.APPLICATION_MODAL);
                        blockingWindow.getIcons().add(new Image("/resources/logo.png"));
                        blockingWindow.setTitle("Add Lecturer");
                        blockingWindow.setScene(new Scene(updateStudentScene));
                        blockingWindow.showAndWait();
                        // Refresh the students list
                        getLecturers();
                        lecturersTable.setItems(lecturers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        // Make the table's rows clickable to be able to pick a lecturer to be removed or updated
        lecturersTable.setRowFactory(new Callback<TableView<LecturerDept>, TableRow<LecturerDept>>() {
            @Override
            public TableRow<LecturerDept> call(TableView<LecturerDept> tv) {
                TableRow<LecturerDept> userRow = new TableRow<>();
                userRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 1 && (!userRow.isEmpty())) {
                            selected = true;
                            selectedLecturer = userRow.getItem();
                            UpdateLecturerController updateLect = new UpdateLecturerController();
                            updateLect.processLectSalary(selectedLecturer);
                        }
                    }
                });
                return userRow;
            }
        });
        
        updateLecturerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UpdateLecturer.fxml"));
                        AnchorPane updateStudentScene = loader.load();
                        Stage blockingWindow = new Stage();
                        blockingWindow.initModality(Modality.APPLICATION_MODAL);
                        blockingWindow.getIcons().add(new Image("/resources/logo.png"));
                        blockingWindow.setTitle("Update Lecturer");
                        blockingWindow.setScene(new Scene(updateStudentScene));
                        blockingWindow.showAndWait();
                        // Refresh the students list
                        getLecturers();
                        lecturersTable.setItems(lecturers);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please click on a lecturer to update his salary.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Update Lecturer");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        deleteLecturerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("You're about to delete lecturer " + selectedLecturer.getFullName()+ ". Click OK to continue.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Lecturer");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK){
                                try {
                                    // Perform the delete action for the student
                                    Boolean isDeleted = DBDeletion.deleteLect(selectedLecturer);
                                    if(isDeleted) {
                                        // Refresh the lecturers list
                                        getLecturers();
                                        lecturersTable.setItems(lecturers);
                                    } else {
                                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                        alert2.setContentText("You cannot delete this lecturer as he/she still teaches some courses.");
                                        Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                                        stage2.setTitle("Delete Lecturer");
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
                        alert.setContentText("Please click on a lecturer to delete.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Lecturer");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
    }
    
    public static void getLecturers() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM lects_info", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<LecturerDept> lecturersList = new ArrayList<LecturerDept>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                LecturerDept lects = new LecturerDept(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
                );
                lecturersList.add(lects);
            }
            lecturers = FXCollections.observableArrayList(lecturersList);
        }
        statement.close();
    }
    
}
