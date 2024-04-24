package gaspardev.controller;

import gaspardev.model.Grid;
import gaspardev.model.Tile;
import gaspardev.model.Cell;
import gaspardev.model.Conexion;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class WaveFuntionColapse {

    // Direcciones absolutas por default
    protected File tilesDir = new File("C:/programar/wfc/src/main/resources/images/Basic");
    protected File conexionsFile = new File("C:/programar/wfc/src/main/resources/data/Conexions/conexions.csv");
    protected File[] tilesFiles;

    private Grid grid;
    private Tile[] tiles;

    public WaveFuntionColapse() {

    }

    public int getNumberOfTiles() {
        return this.tilesDir.listFiles().length;
    }

    public void setDimensions(int width, int height) {
        this.grid = new Grid(width, height);
    }

    public void setNewTilePath(String path) {
        this.tilesDir = new File(path);
    }

    public void setNewConexionsPath(String path) {
        this.conexionsFile = new File(path);
    }

    public Tile getTile(int index) {
        return this.tiles[index];
    }

    public void setNewTileDir(String path) {
        this.tilesDir = new File(path);
    }

    public void setNewGrid(int width, int height) {
        this.grid = new Grid(width, height);
    }

    public String getTileDir() {
        return this.tilesDir.getAbsolutePath().toString();
    }

    public String getTileDirRelative() {
        return this.tilesDir.getPath();
    }

    public String getConexionsFile() {
        return this.conexionsFile.getAbsolutePath().toString();
    }

    public Grid getGrid() {
        return this.grid;
    }

    // #region Load Tiles and Conexions

    /**
     * A method to load the tiles and connections based on the specified path.
     *
     * @throws IllegalArgumentException if the connections file does not exist or is
     *                                  not of .csv extension
     */
    public void loadTilesConexions() {

        // Comprobar que la ruta indicada es un archivo
        if (!conexionsFile.isFile()) {
            throw new IllegalArgumentException("El archivo de conexiones no existe");
        }
        // Comprobar que la ruta indicada es un archivo .csv
        if (conexionsFile.getName().contains("*.csv")) {
            throw new IllegalArgumentException("El archivo de conexiones debe ser de extensión .csv");
        }

    }

    /**
     * Loads the tiles from the specified directory.
     *
     * This method retrieves the list of files in the tiles directory and checks if
     * any valid tiles are loaded.
     * If no valid tiles are found, an IllegalArgumentException is thrown with the
     * message "No hay patrones válidos cargados".
     * Additionally, this method checks if there is a tile named "BLANK.png" in the
     * tiles directory.
     * If no tile named "BLANK.png" is found, an IllegalArgumentException is thrown
     * with the message "No hay un patrón con el nombre Blank".
     *
     * @throws IllegalArgumentException if no valid tiles are loaded or if no tile
     *                                  named "BLANK.png" is found
     */
    public void loadTiles() {

        this.tilesFiles = this.tilesDir.listFiles();
        // Comprobar que hay patrones en la ruta indicada
        if (tilesFiles == null || tilesFiles.length == 0) {
            throw new IllegalArgumentException("No hay patrones válidos cargados");
        }

    }

    // Leer los patrones y las conexiones de los mismos y generar el boiler plate
    // para el programa

    /**
     * Generates the rotations of tiles based on the loaded tiles and connections.
     *
     * This function loads the tiles and connections from the specified directories.
     * It then reads the connections file line by line and parses each line to
     * extract
     * the connection values and weights. The connection values and weights are
     * stored
     * in separate lists.
     * The function then calculates the total number of tiles by multiplying the
     * number of tiles files with the number of segments in the first connection.
     * It initializes an array of Tile objects with the calculated length.
     * The function iterates over each tile file and for each file, it creates a
     * Tile object for each segment rotation. The Tile objects are initialized with
     * the rotation, file name, corresponding connection, and weight.
     *
     * @throws Exception if there is an error reading the connections file or if no
     *                   valid tiles are loaded or if no tile named "BLANK.png" is
     *                   found
     */
    public void generateTilesRotations() {
        loadTiles();
        loadTilesConexions();

        try (BufferedReader reader = new BufferedReader(new FileReader(conexionsFile))) {
            List<Conexion> conexions = new ArrayList<>(tilesFiles.length);
            List<Short> weightS = new ArrayList<>(tilesFiles.length);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                int[] arr = new int[values.length - 1];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = Short.parseShort(values[i]);
                }
                weightS.add(Short.parseShort(values[values.length - 1]));
                conexions.add(new Conexion(arr));
            }

            this.tiles = new Tile[tilesFiles.length * conexions.get(0).getSegments()];
            int index = 0;
            for (int i = 0; i < tilesFiles.length; i++) {
                for (int rotation = 0; rotation < conexions.get(0).getSegments(); rotation++) {
                    this.tiles[index] = new Tile(rotation, tilesFiles[i].getName(), conexions.get(i), weightS.get(i));
                    index++;
                }
            }

        } catch (Exception e) {
            e.getCause();
        }
    }

    /**
     * Rotates the tiles in each cell of the grid.
     *
     * This function iterates over each row of cells in the grid and then each cell
     * within that row.
     * For each cell, it retrieves the entropy array and uses the Arrays.stream()
     * method to create a stream of the elements.
     * The forEach() method is then used to iterate over each element in the stream
     * and call the rotateTile() method on it.
     * This effectively rotates each tile in the cell.
     *
     * @param None This function does not take any parameters.
     * @return None This function does not return any value.
     */
    public void rotations() {

        for (Cell[] cellRow : this.grid.getSpaces()) {
            for (Cell cell : cellRow) {
                Arrays.stream(cell.getEntropy()).forEach(t -> t.rotateTile());
            }
        }
    }

    // #region Draw and Update Entropie

    /**
     * Sets the entropy of each cell in the grid to the tiles array.
     *
     * This function iterates over each cell in the grid directly,
     * avoiding the overhead of creating a stream and flattening it.
     *
     * @param None This function does not take any parameters.
     * @return None This function does not return any value.
     */
    public void fillEntropie() {

        Cell[][] cells = this.grid.getSpaces();
        for (int i = 0; i < cells.length; i++) {
            Cell[] cellRow = cells[i];
            for (int j = 0; j < cellRow.length; j++) {
                Cell cell = cellRow[j];
                cell.setEntropy(this.tiles);
            }
        }
    }

    /**
     * Returns the cell with the minimum entropy.
     *
     * @return the cell with the minimum entropy
     */
    public Cell getCellWithMinimumEntropy() {
        Cell[][] cells = this.grid.getSpaces();
        int minEntropyLenght = Integer.MAX_VALUE;
        Cell minEntropyCell = null;
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (!cell.isColapsed() && cell.getEntropyLenght() < minEntropyLenght) {
                    minEntropyLenght = cell.getEntropyLenght();
                    minEntropyCell = cell;
                }
            }
        }
        return minEntropyCell;
    }

    /**
     * Checks if all cells in the grid are collapsed.
     *
     * @return true if all cells are collapsed, false otherwise
     */
    public boolean checkIsAllCollapsed() {
        boolean isAllCollapsed = true;
        Cell[][] cells = this.grid.getSpaces();
        for (int i = 0; i < cells.length && isAllCollapsed; i++) {
            Cell[] cellRow = cells[i];
            for (int j = 0; j < cellRow.length && isAllCollapsed; j++) {
                if (!cellRow[j].isColapsed()) {
                    isAllCollapsed = false;
                    break;
                }
            }
        }

        return isAllCollapsed;
    }

    /**
     * Checks if the grid has any empty cells by iterating over each cell in the
     * grid
     * and checking if its entropy length is 0. Returns true if there are empty
     * cells,
     * false otherwise.
     *
     * @return true if the grid has empty cells, false otherwise
     */
    public boolean restartGrid() {
        Cell[][] cells = this.grid.getSpaces();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getEntropy().length == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Clears the entropy of each cell in the grid without creating streams.
     *
     * @param None This function does not take any parameters.
     * @return None This function does not return any value.
     */
    public void clearEntropie() {
        Cell[][] cells = this.grid.getSpaces();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                cell.setEntropy(NO_TILES);
            }
        }
    }

    public void resetColapsedFalse() {

        Cell[][] cells = this.grid.getSpaces();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                cell.setColapsed(false);
            }
        }
    }

    private static final Tile[] NO_TILES = new Tile[0];

}
