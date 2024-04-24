package gaspardev.controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.application.Platform;
import javafx.concurrent.Task;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import gaspardev.model.Cell;

public class MapViewController {

    WaveFuntionColapse wfc;
    private Task<Void> wfcTask;
    private Task<Void> drawTask;
    private BlockingQueue<Cell> updateQueue = new LinkedBlockingQueue<>();

    @FXML
    AnchorPane anchorPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    GridPane grid = new GridPane();
    @FXML
    Button buttonMap;

    @FXML
    public void setInitial(WaveFuntionColapse wfc) {
        this.wfc = wfc;
    }

    private void initWfcTask() {
        wfcTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                wfc.clearEntropie();
                wfc.fillEntropie();
                wfc.rotations();

                do {
                    Cell tempCell = wfc.getCellWithMinimumEntropy().colapseTileR();
                    tempCell.updateNeighborsValue();

                    if (wfc.restartGrid()) {
                        // Limpiar la cuadrícula y dibujar de nuevo
                        Platform.runLater(() -> {
                            grid.getChildren().clear();
                            try {
                                drawMap();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        break; // Salir del bucle cuando se reinicia la cuadrícula
                    }

                    // Agregar a la cola de actualizaciones
                    updateQueue.add(tempCell);

                } while (!wfc.checkIsAllCollapsed());

                return null;
            }
        };
    }

    private void initDrawTask() {
        drawTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                do {

                    if (updateQueue.isEmpty()) {
                        Thread.sleep(20);
                    }

                    Cell tempCell = updateQueue.take();
                    // Obtener la imagen de la celda
                    String imagePath = wfc.getTileDirRelative().split("resources")[1].concat("\\").replaceAll("\\\\",
                            "/")
                            + tempCell.getColapsedTile().getImg();
                    Image image = new Image(getClass().getResourceAsStream(imagePath));
                    ImageView imageView = new ImageView(image);

                    imageView.getTransforms()
                            .add(new Rotate(
                                    tempCell.getColapsedTile().getRotation() * (360
                                            / tempCell.getColapsedTile().getConexcions().length),
                                    image.getWidth() / 2, image.getHeight() / 2));

                    // Actualizar la interfaz de usuario en el hilo de JavaFX
                    Platform.runLater(() -> {
                        grid.add(imageView, tempCell.getPosX(), tempCell.getPosY());
                    });

                } while (!updateQueue.isEmpty());

                return null;
            }
        };
    }

    private void executeTasks() {
        // Ejecutar las tareas en hilos separados
        new Thread(wfcTask).start();
        new Thread(drawTask).start();
    }

    @FXML
    private void drawMap() throws Exception {

        buttonMap.setVisible(false);

        // Get grid size and create grid cells
        int cols = wfc.getGrid().getWidth();
        int rows = wfc.getGrid().getHeight();
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                grid.add(new Rectangle(20, 20), col, row);
            }
        }

        // Add grid to scroll pane
        scrollPane.setContent(grid);

        // Draw
        initTasks();
        executeTasks();
    }

    private void initTasks() {
        initWfcTask();
        initDrawTask();
    }

}
