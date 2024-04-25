package gaspardev.controller;

import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.File;

public class CreateNewMapController {

    @FXML
    TextField inputWidth;
    @FXML
    TextField currentTilesDir;
    @FXML
    TextField currentDirConexions;
    @FXML
    Label errorShowLabel;

    WaveFuntionColapse wfc;

    protected DirectoryChooser dirChooser = new DirectoryChooser();
    protected FileChooser fileChooser = new FileChooser();

    public void setWFC(WaveFuntionColapse wfc) {
        this.wfc = wfc;
        this.currentTilesDir.setText(wfc.getTileDir());
        this.currentDirConexions.setText(wfc.getConexionsFile());
    }

    @FXML
    public void setNewDirToImages(String path) {
        wfc.setNewTilePath(path);
    }

    @FXML
    public void selectDirectory(ActionEvent event) {

        dirChooser.setInitialDirectory(new File("C:/"));
        dirChooser.setTitle("Select Tiles Directory");

        File dir = dirChooser.showDialog(null);
        if (dir != null) {
            wfc.setNewTilePath(dir.getAbsolutePath());
            currentTilesDir.setText(dir.getAbsolutePath().toString());
        }
    }

    @FXML
    public void selectConexions(ActionEvent event) {

        fileChooser.setInitialDirectory(new File("C:/"));
        fileChooser.setTitle("Select Conexions CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".csv", "*.csv"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            wfc.setNewConexionsPath(file.getAbsolutePath());
            currentDirConexions.setText(file.getAbsolutePath().toString());
        }

    }

    @FXML
    public void loadTiles(ActionEvent event) {
        try {
            wfc.loadTiles();
            errorShowLabel.setText("");
        } catch (Exception e) {
            errorShowLabel.setText("Error al cargar los patrones");
        }
    }

    @FXML
    public void loadTilesRotations(ActionEvent event) {
        try {
            wfc.loadTilesConexions();
            errorShowLabel.setText("");
        } catch (Exception e) {
            errorShowLabel.setText("Error al cargar las conexiones");
        }
    }

    @FXML
    public void goNewMap(ActionEvent event) throws Exception {

        if (!isEmpty(inputWidth.getText())) {
            try {
                wfc.setDimensions(Integer.parseInt(inputWidth.getText()), Integer.parseInt(inputWidth.getText()));
                wfc.generateTilesRotations();
                wfc.getGrid().fillSpaces();
                wfc.getGrid().connectNeighbors();
            } catch (Exception e) {
                throw new Exception("Error al dimensionar el mapa o al cargar los patrones");
            }

        } else {
            errorShowLabel.setText("Error al crear el mapa, faltan las dimensiones o error al cargar información");
            throw new Exception("Los datos no son correctos");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MapView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        MapViewController controller = loader.getController();
        controller.setInitial(wfc);

    }

    public static boolean isEmpty(String str) {

        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

}
