package gaspardev.controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class MapViewController {

    WaveFuntionColapse wfc;

    @FXML
    AnchorPane anchorPane;
    @FXML
    GridPane grid = new GridPane();
    @FXML
    Button buttonMap;

    @FXML
    public void setInitial(WaveFuntionColapse wfc) {
        this.wfc = wfc;
    }

    @FXML
    private void drawMap() {

        buttonMap.setVisible(false);

        // Obtener el tamaño de la grid
        int gridWidth = wfc.getGrid().getWidth();
        int gridHeight = wfc.getGrid().getHeight();

        // Crear las celdas de la grid
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Rectangle space = new Rectangle(20, 20);
                grid.add(space, i, j);
            }
        }

        this.grid.gridLinesVisibleProperty().setValue(true);

        // Agregar la grid al anchorPane
        anchorPane.getChildren().add(grid);
        wfc.draw();

        for (int i = 0; i < grid.getColumnCount(); i++) {
            for (int j = 0; j < grid.getRowCount(); j++) {
                String imagePath = "/images/Tiles/" + wfc.getGrid().getSpaces()[i][j].getColapsedTile().getImg();
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                grid.add(new ImageView(image), i, j);
            }
        }

    }

}
