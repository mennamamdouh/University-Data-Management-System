/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DTO.CourseDept;
import DTO.Student;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class EnrolledStudentsController implements Initializable {
    
    static ObservableList<Student> allEnrolledStudents;
    private static boolean coursesLoaded = false;
    static int selectedCourseId;
    
    public void passCourseID(CourseDept selectedCourse) {
        selectedCourseId = selectedCourse.getCourseId();
    }

    @FXML
    private TableView<Student> enrolledStudentsTable;
    @FXML
    private TableColumn<Student, String> imageColumn;
    @FXML
    private TableColumn<Student, String> studentNameColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        // Set the cell value factory for the image column
        imageColumn.setCellValueFactory(
                new PropertyValueFactory<>("personalPhoto")
        );
        
        // Set the cell factory for the image column
        imageColumn.setCellFactory(new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
            @Override
            public TableCell<Student, String> call(TableColumn<Student, String> param) {
                return new TableCell<Student, String>(){
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
        studentNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("fullName")
        );
        
        Label noContentLabel = new Label("No students enrolled yet.");
        enrolledStudentsTable.setPlaceholder(noContentLabel);
        
        if (!coursesLoaded) {
            try {
                // Load the students data in the enrolledStudents table
                getEnrolledStudents();
                coursesLoaded = true;
                enrolledStudentsTable.setItems(allEnrolledStudents);
            } catch (SQLException ex) {
                Logger.getLogger(EnrolledStudentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {    // If it's not the first time to load the enrolledStudents list, clear it first then load the targeted data.
            allEnrolledStudents.clear();
            try {
                getEnrolledStudents();
                enrolledStudentsTable.setItems(allEnrolledStudents);
            } catch (SQLException ex) {
                Logger.getLogger(EnrolledStudentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    
    public Circle createCircularImageView(double size) {
        Circle clip = new Circle(size / 2);
        clip.setCenterX(size / 2);
        clip.setCenterY(size / 2);
        return clip;
    }
    
    public static void getEnrolledStudents() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT StudentID, FullName, PersonalPhoto FROM enrolled_students WHERE CourseID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, selectedCourseId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Student> enrolledStudents = new ArrayList<Student>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                Student enrolledStudent = new Student(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
                );
                enrolledStudents.add(enrolledStudent);
            }
            allEnrolledStudents = FXCollections.observableArrayList(enrolledStudents);
        }
        statement.close();
    }
}
