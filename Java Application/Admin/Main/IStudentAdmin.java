package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class IStudentAdmin extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Admin.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/resources/logo.png"));
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
