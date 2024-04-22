package gaspardev.controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import gaspardev.model.Cell;

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
    private void drawMap() throws Exception {

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

        // Draw
        draw();

    }

    private void draw() {

        wfc.clearEntropie();
        wfc.fillEntropie();
        wfc.rotations();

        do {

            Cell tempCell = wfc.getCellWithMinimumEntropy().colapseTileR();
            tempCell.updateNeighborsValue();

            // Obtener la imagen de la celda
            String imagePath = "/images/Tiles/" + tempCell.getColapsedTile().getImg();
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);

            imageView.getTransforms()
                    .add(new Rotate(
                            tempCell.getColapsedTile().getRotation() * (360
                                    / tempCell.getColapsedTile().getConexcions().length),
                            image.getWidth() / 2, image.getHeight() / 2));

            grid.add(imageView, tempCell.getPosX(), tempCell.getPosY());

            if (wfc.restartGrid()) {
                draw();
            }

        } while (!wfc.checkIsAllCollapsed());
    }

}
