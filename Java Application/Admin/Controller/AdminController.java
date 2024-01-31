package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AdminController implements Initializable {

    @FXML
    private TabPane adminTabPane;
    @FXML
    private Tab studentsTab;
    @FXML
    private Tab coursesTab;
    @FXML
    private Tab deptsTab;
    @FXML
    private Tab lecturersTab;
    @FXML
    private Tab summaryTab;
    @FXML
    private Tab alertsTab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /* Adjust the height and width of the tabs in the header */
        adminTabPane.setTabMinHeight(200);
        adminTabPane.setTabMaxHeight(100);
        adminTabPane.getStyleClass().add("horizontal-tab-pane");
        
        /* To rotate all tabs in the header */
        rotateTabHeader(studentsTab);
        rotateTabHeader(coursesTab);
        rotateTabHeader(deptsTab);
        rotateTabHeader(lecturersTab);
        rotateTabHeader(summaryTab);
        rotateTabHeader(alertsTab);
        
        /* Set the content of each tab */
        try {
            studentsTab.setContent(FXMLLoader.load(getClass().getResource("/View/Students.fxml")));
            coursesTab.setContent(FXMLLoader.load(getClass().getResource("/View/Courses.fxml")));
            deptsTab.setContent(FXMLLoader.load(getClass().getResource("/View/Departments.fxml")));
            lecturersTab.setContent(FXMLLoader.load(getClass().getResource("/View/Lecturers.fxml")));
            summaryTab.setContent(FXMLLoader.load(getClass().getResource("/View/Summary.fxml")));
            alertsTab.setContent(FXMLLoader.load(getClass().getResource("/View/Alerts.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void rotateTabHeader(Tab tab) {
        Label emptyLabel = new Label();
        tab.setGraphic(emptyLabel);
        Platform.runLater(() -> {
            Parent tabContainer = tab.getGraphic().getParent().getParent();
            tabContainer.setRotate(90);
            tabContainer.setTranslateY(-50);
        });
    }
}