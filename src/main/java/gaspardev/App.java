package gaspardev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class App extends Application {

    private Scene scene;
    private Parent root;
    String cssStyles = this.getClass().getResource("/css/styles.css").toExternalForm();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}