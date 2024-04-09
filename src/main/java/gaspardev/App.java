 package gaspardev;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage stage) throws IOException {

    try {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
       String cssStyles = this.getClass().getResource("test.css").toExternalForm();
        scene.getStylesheets().add(cssStyles);
        stage.setScene(scene);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }

   }
    

}