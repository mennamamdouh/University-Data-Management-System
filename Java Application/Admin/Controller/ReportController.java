/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DTO.Course;
import DTO.Department;
import DTO.Semester;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author mennatallah
 */
public class ReportController implements Initializable {
    
    static ObservableList<Course> coursesGPA;
    static ObservableList<Department> deptsCourses;
    static ObservableList<Semester> semestersStudents;
    
    @FXML
    private BarChart<Number, String> barChart;
    @FXML
    private PieChart departmentsPie;
    @FXML
    private PieChart semestersPie;
    @FXML
    private NumberAxis numberAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /* Setting the Bar Chart */
        try {
            getAverageGPA();
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        barChart.setTitle("Courses Average GPA");
        numberAxis.setUpperBound(4.00);
        
        // Set the data to the bar chart
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Course course : coursesGPA) {
            series.getData().add(new XYChart.Data<>(course.getAverageGPA(), course.getTitle()));
        }
        barChart.getData().add(series);
        
        /* Setting the first Pie Chart */
        try {
            getDepartmentsCourses();
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        departmentsPie.setTitle("Number of Courses per Department");
        
        // Set the data to first the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Department department : deptsCourses) {
            String departmentName = department.getDeptName();
            double numberOfCourses = department.getNumberOfCourses();

            // Format the course count with commas for large numbers (optional)
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            String formattedCourses = numberFormat.format(numberOfCourses);

            // Combine department name and formatted course count
            String labelText = departmentName + " (" + formattedCourses + ")";

            PieChart.Data data = new PieChart.Data(labelText, numberOfCourses);
            pieChartData.add(data);
        }
        departmentsPie.setData(pieChartData);
        
        /* Setting the second Pie Chart */
        try {
            getSemestersStudents();
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        semestersPie.setTitle("Number of Students per Semester");
        
        // Set the data to the second pie chart
        final ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
        for (Semester semester : semestersStudents) {
            String semesterName = semester.getSemester();
            int numberOfStudents = semester.getNumberOfStudents();

            // Format the course count with commas for large numbers (optional)
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            String formattedStudents = numberFormat.format(numberOfStudents);

            // Combine department name and formatted course count
            String labelText = semesterName + " (" + formattedStudents + ")";

            PieChart.Data data = new PieChart.Data(labelText, numberOfStudents);
            pieChartData2.add(data);
        }
        semestersPie.setData(pieChartData2);
    }
    
    public static void getAverageGPA() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM average_gpa_per_course", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Course> coursesList = new ArrayList<Course>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                Course courseGPA = new Course(
                    resultSet.getString(1),
                    resultSet.getDouble(2)
                );
                coursesList.add(courseGPA);
            }
            coursesGPA = FXCollections.observableArrayList(coursesList);
        }
        statement.close();
    }
    
    public static void getDepartmentsCourses() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM courses_in_departments", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Department> deptsInfo = new ArrayList<Department>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                Department deptCourses = new Department(
                    resultSet.getString(1),
                    resultSet.getInt(2)
                );
                deptsInfo.add(deptCourses);
            }
            deptsCourses = FXCollections.observableArrayList(deptsInfo);
        }
        statement.close();
    }
    
    public static void getSemestersStudents() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM students_per_semester", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Semester> semestersInfo = new ArrayList<Semester>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                Semester semester = new Semester(
                    resultSet.getString(1),
                    resultSet.getInt(2)
                );
                semestersInfo.add(semester);
            }
            semestersStudents = FXCollections.observableArrayList(semestersInfo);
        }
        statement.close();
    }
    
}
