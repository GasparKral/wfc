package gaspardev.controller;

import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    protected WaveFuntionColapse wfc = new WaveFuntionColapse();

    @FXML
    public void goToCreateNewMap(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateNewMap.fxml"));
            root = loader.load();

            CreateNewMapController controller = loader.getController();
            controller.setWFC(wfc);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("/css/createNewMapStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
