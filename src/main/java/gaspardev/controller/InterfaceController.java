package gaspardev.controller;

import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class InterfaceController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    String cssStyles = this.getClass().getResource("/css/styles.css").toExternalForm();
    WaveFuntionColapse wfc = new WaveFuntionColapse();

    @FXML
    public void goBack(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            scene = new Scene(root);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(cssStyles);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToCreateNewMap(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/CreateNewMap.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
