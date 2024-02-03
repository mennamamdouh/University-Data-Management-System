/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DAO.DBDeletion;
import DTO.StudentDept;
import java.io.IOException;
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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class StudentsController implements Initializable {

    static ObservableList<StudentDept> students;
    private static boolean studentsLoaded = false;
    private static boolean selected = false;
    StudentDept selectedStudent;
    
    @FXML
    private TableView<StudentDept> studentsTable;
    @FXML
    private TableColumn<StudentDept, String> studentImage;
    @FXML
    private TableColumn<StudentDept, String> studentName;
    @FXML
    private TableColumn<StudentDept, String> studentDept;
    @FXML
    private TableColumn<StudentDept, Double> studentCGPa;
    @FXML
    private TableColumn<StudentDept, Integer> studentCHours;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private Button updateStudentButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set the cell value factory for the studentImage column
        studentImage.setCellValueFactory(
                new PropertyValueFactory<>("personalPhoto")
        );
        
        // Set the cell factory for the studentImage column
        studentImage.setCellFactory(new Callback<TableColumn<StudentDept, String>, TableCell<StudentDept, String>>() {
            @Override
            public TableCell<StudentDept, String> call(TableColumn<StudentDept, String> param) {
                return new TableCell<StudentDept, String>(){
                    private final ImageView imageView = new ImageView();
                    
                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);

                        if (empty || imagePath == null) {
                            setGraphic(null);
                        } else {
                            Image image = new Image(imagePath);
                            // declaring width and height for the image 
                            double width = 50;
                            double height = 50;
                            double radius = 50;
                            imageView.setImage(image);
                            // setting the width and height for the image
                            imageView.setFitWidth(width);
                            imageView.setFitHeight(height);
                            imageView.setClip(createCircularImageView(radius));

                            setGraphic(imageView);
                        }
                    }
                };
            }
        });
        
        // Set the cell value factory for the studentName column
        studentName.setCellValueFactory(
                new PropertyValueFactory<>("fullName")
        );
        
        // Create a custom cell factory for the department column to display the department names
        studentDept.setCellValueFactory(
                new PropertyValueFactory<>("deptName")
        );
        
        // Set the cell value factory for the studentCGPa column
        studentCGPa.setCellValueFactory(
                new PropertyValueFactory<>("cgpa")
        );
        
        // Set the cell value factory for the studentCHours column
        studentCHours.setCellValueFactory(
                new PropertyValueFactory<>("totalCreditHours")
        );
        
        if (!studentsLoaded) {
            try {
                getStudents();
                studentsLoaded = true;
                // Set the items after adding the columns
                studentsTable.setItems(students);
            } catch (SQLException ex) {
                Logger.getLogger(StudentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Make the table's rows clickable to be able to visit the friend's profile
        studentsTable.setRowFactory(new Callback<TableView<StudentDept>, TableRow<StudentDept>>() {
            @Override
            public TableRow<StudentDept> call(TableView<StudentDept> tv) {
                TableRow<StudentDept> userRow = new TableRow<>();
                userRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 1 && (!userRow.isEmpty())) {
                            selected = true;
                            selectedStudent = userRow.getItem();
                            UpdateStudentController updateStudent = new UpdateStudentController();
                            updateStudent.processStudentDept(selectedStudent);
                        }
                    }
                });                
                
                return userRow ;
            }
        });
        
        updateStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UpdateStudent.fxml"));
                        AnchorPane updateStudentScene = loader.load();
                        Stage blockingWindow = new Stage();
                        blockingWindow.initModality(Modality.APPLICATION_MODAL);
                        blockingWindow.getIcons().add(new Image("/resources/logo.png"));
                        blockingWindow.setTitle("Update Student");
                        blockingWindow.setScene(new Scene(updateStudentScene));
                        blockingWindow.showAndWait();
                        // Refresh the students list
                        getStudents();
                        studentsTable.setItems(students);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please click on a student to change his department.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Update Student");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        deleteStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("You're about to delete student " + selectedStudent.getFullName() + " from the system, hence his all enrollments will be deleted as well.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Student");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK){
                                try {
                                    // Perform the delete action for the student
                                    DBDeletion.deleteStudent(selectedStudent);
                                    // Refresh the students list
                                    getStudents();
                                    studentsTable.setItems(students);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StudentsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please click on a student to delete.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Student");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        addStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddStudent.fxml"));
                        AnchorPane updateStudentScene = loader.load();
                        Stage blockingWindow = new Stage();
                        blockingWindow.initModality(Modality.APPLICATION_MODAL);
                        blockingWindow.getIcons().add(new Image("/resources/logo.png"));
                        blockingWindow.setTitle("Add Student");
                        blockingWindow.setScene(new Scene(updateStudentScene));
                        blockingWindow.showAndWait();
                        // Refresh the students list
                        getStudents();
                        studentsTable.setItems(students);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
    }    
    
    public static void getStudents() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM studentds_info", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<StudentDept> studentsList = new ArrayList<StudentDept>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                StudentDept studentDept = new StudentDept(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6)
                );
                studentsList.add(studentDept);
            }
            students = FXCollections.observableArrayList(studentsList);
        }
        statement.close();
    }
    
    public Circle createCircularImageView(double size) {
        Circle clip = new Circle(size / 2);
        clip.setCenterX(size / 2);
        clip.setCenterY(size / 2);
        return clip;
    }
}