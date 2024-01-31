/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class LecturersController implements Initializable {
    
    static ObservableList<LecturerDept> lecturers;
    private static boolean lectsLoaded = false;

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
                getCourses();
                lectsLoaded = true;
                // Set the items after adding the columns
                lecturersTable.setItems(lecturers);
            } catch (SQLException ex) {
                Logger.getLogger(LecturersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void getCourses() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM lects_info", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<LecturerDept> lecturersList = new ArrayList<LecturerDept>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                LecturerDept lects = new LecturerDept(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6)
                );
                lecturersList.add(lects);
            }
            lecturers = FXCollections.observableArrayList(lecturersList);
        }
        statement.close();
    }
    
}
