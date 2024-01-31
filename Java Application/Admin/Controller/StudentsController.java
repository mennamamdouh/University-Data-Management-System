/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class StudentsController implements Initializable {

    static ObservableList<StudentDept> students;
    private static boolean studentsLoaded = false;
    
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
                            //Circle defaultClip = createCircularClip();
                            //imageView.setClip(defaultClip);
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
                    resultSet.getString(2),
                    resultSet.getString(1),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getString(3)
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