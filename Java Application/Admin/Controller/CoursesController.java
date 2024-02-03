/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DAO.DBDeletion;
import DTO.CourseDept;
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
public class CoursesController implements Initializable {
    
    static ObservableList<CourseDept> courses;
    private static boolean coursesLoaded = false;
    private static boolean selected = false;
    CourseDept selectedCourse;

    @FXML
    private TableView<CourseDept> coursesTable;
    @FXML
    private TableColumn<CourseDept, String> courseColumn;
    @FXML
    private TableColumn<CourseDept, Integer> cHoursColumn;
    @FXML
    private TableColumn<CourseDept, String> deptColumn;
    @FXML
    private TableColumn<CourseDept, String> lectColumn;
    @FXML
    private TableColumn<CourseDept, Integer> numOfStudents;
    @FXML
    private Button addCourseButton;
    @FXML
    private Button deleteCourseButton;
    @FXML
    private Button updateCourseButton;
    @FXML
    private Button showStudentsButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set the cell value factory for the course name column
        courseColumn.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        
        // Set the cell value factory for the credit hours column
        cHoursColumn.setCellValueFactory(
                new PropertyValueFactory<>("creditHours")
        );
        
        // Set the cell value factory for the department name column
        deptColumn.setCellValueFactory(
                new PropertyValueFactory<>("deptName")
        );
        
        // Set the cell value factory for the lecturer name column
        lectColumn.setCellValueFactory(
                new PropertyValueFactory<>("lectName")
        );
        
        // Set the cell value factory for the number of students column
        numOfStudents.setCellValueFactory(
                new PropertyValueFactory<>("numberOfStudents")
        );
        
        if (!coursesLoaded) {
            try {
                getCourses();
                coursesLoaded = true;
                // Set the items after adding the columns
                coursesTable.setItems(courses);
            } catch (SQLException ex) {
                Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Make the table's rows clickable to be able to remove or update a student
        coursesTable.setRowFactory(new Callback<TableView<CourseDept>, TableRow<CourseDept>>() {
            @Override
            public TableRow<CourseDept> call(TableView<CourseDept> tv) {
                TableRow<CourseDept> userRow = new TableRow<>();
                userRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 1 && (!userRow.isEmpty())) {
                            selected = true;
                            selectedCourse = userRow.getItem();
                            UpdateCourseController updateCourse = new UpdateCourseController();
                            updateCourse.processCourseLect(selectedCourse);
                            EnrolledStudentsController enrolledStudents = new EnrolledStudentsController();
                            enrolledStudents.passCourseID(selectedCourse);
                        }
                    }
                });
                return userRow ;
            }
        });
        
        updateCourseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UpdateCourse.fxml"));
                        AnchorPane updateStudentScene = loader.load();
                        Stage blockingWindow = new Stage();
                        blockingWindow.initModality(Modality.APPLICATION_MODAL);
                        blockingWindow.getIcons().add(new Image("/resources/logo.png"));
                        blockingWindow.setTitle("Update Course");
                        blockingWindow.setScene(new Scene(updateStudentScene));
                        blockingWindow.showAndWait();
                        // Refresh the students list
                        getCourses();
                        coursesTable.setItems(courses);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please click on a course to change his lecturer.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Update Course");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        deleteCourseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("You're about to delete course " + selectedCourse.getTitle() + " from the system. Click OK to continue.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Course");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK){
                                try {
                                    // Perform the delete action for the course
                                    boolean isDeleted = DBDeletion.deleteCourse(selectedCourse);
                                    if(isDeleted) {
                                        // Refresh the courses list
                                        getCourses();
                                        coursesTable.setItems(courses);
                                    } else {
                                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                        alert2.setContentText("You cannot delete this course as there're still some students enrolled on it.");
                                        Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                                        stage2.setTitle("Delete Course");
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
                        alert.setContentText("Please click on a course to delete.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Delete Course");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        addCourseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddCourse.fxml"));
                    AnchorPane updateStudentScene = loader.load();
                    Stage blockingWindow = new Stage();
                    blockingWindow.initModality(Modality.APPLICATION_MODAL);
                    blockingWindow.getIcons().add(new Image("/resources/logo.png"));
                    blockingWindow.setTitle("Add Course");
                    blockingWindow.setScene(new Scene(updateStudentScene));
                    blockingWindow.showAndWait();
                    // Refresh the courses list
                    getCourses();
                    coursesTable.setItems(courses);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
        
        showStudentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(selected) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EnrolledStudents.fxml"));
                        AnchorPane enrolledStudentsScene = loader.load();
                        Stage blockingWindow = new Stage();
                        blockingWindow.initModality(Modality.APPLICATION_MODAL);
                        blockingWindow.getIcons().add(new Image("/resources/student.png"));
                        blockingWindow.setTitle(selectedCourse.getTitle());
                        blockingWindow.setResizable(false);
                        blockingWindow.setScene(new Scene(enrolledStudentsScene));
                        blockingWindow.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please click on a course to show its enrolled students.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.setTitle("Enrolled Students");
                        stage.getIcons().add(new Image(this.getClass().getResource("/resources/logo.png").toString()));
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        });
    }
    
    public static void getCourses() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM courses_info", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<CourseDept> coursesList = new ArrayList<CourseDept>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                CourseDept courseDept = new CourseDept(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
                );
                coursesList.add(courseDept);
            }
            courses = FXCollections.observableArrayList(coursesList);
        }
        statement.close();
    }
}
