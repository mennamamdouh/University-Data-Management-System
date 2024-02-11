/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DBConnection;
import DTO.Alert;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class AlertsController implements Initializable {
    
    static ObservableList<Alert> alerts;
    private static boolean alertsLoaded = false;
    private boolean flag;

    @FXML
    private TableView<Alert> alertsTable;
    @FXML
    private TableColumn<Alert, String> iconColumn;
    @FXML
    private TableColumn<Alert, Timestamp> dateColumn;
    @FXML
    private TableColumn<Alert, String> anomalyTypeColumn;
    @FXML
    private TableColumn<Alert, String> messageColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the cell value factory for the icon column
        iconColumn.setCellValueFactory(
                new PropertyValueFactory<>("icon")
        );
        
        // Set the cell factory for the icon column
        iconColumn.setCellFactory(new Callback<TableColumn<Alert, String>, TableCell<Alert, String>>() {
            @Override
            public TableCell<Alert, String> call(TableColumn<Alert, String> param) {
                return new TableCell<Alert, String>(){
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
        
        // Set the cell value factory for the dateTime column
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("dateTime")
        );
        
        // Set the cell value factory for the anomaly type column
        anomalyTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("anomalyType")
        );
        
        // Set the cell value factory for the message column
        messageColumn.setCellValueFactory(
                new PropertyValueFactory<>("message")
        );
        
        Label noContentLabel = new Label("No alerts.");
        alertsTable.setPlaceholder(noContentLabel);
        
        // Get the data
        if (!alertsLoaded) {
            try {
                getAlerts();
                alertsLoaded = true;
                // Set the items after adding the columns
                alertsTable.setItems(alerts);
            } catch (SQLException ex) {
                Logger.getLogger(AlertsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Circle createCircularImageView(double size) {
        Circle clip = new Circle(size / 2);
        clip.setCenterX(size / 2);
        clip.setCenterY(size / 2);
        return clip;
    }
    
    public void getAlerts() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Notifications", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Alert> alertsList = new ArrayList<Alert>();
        if (resultSet.next()) {
            resultSet.previous();
            while (resultSet.next()) {
                Alert alert = new Alert(
                    resultSet.getInt(1),
                    resultSet.getTimestamp(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
                );
                alertsList.add(alert);
            }
            alerts = FXCollections.observableArrayList(alertsList);
        }
        statement.close();
    }
    
}
