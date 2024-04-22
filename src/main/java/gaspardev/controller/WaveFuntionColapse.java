package gaspardev.controller;

import gaspardev.model.Grid;
import gaspardev.model.Tile;
import gaspardev.model.Cell;
import gaspardev.model.Conexion;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class WaveFuntionColapse {

    protected File tilesDir = new File("C:/programar/wfc/src/main/resources/images/Tiles");
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

    public String getConexionsFile() {
        return this.conexionsFile.getAbsolutePath().toString();
    }

    // #region Load Tiles and Conexions

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

    public void loadTiles() {

        this.tilesFiles = this.tilesDir.listFiles();
        // Comprobar que hay patrones en la ruta indicada
        if (tilesFiles == null || tilesFiles.length == 0) {
            throw new IllegalArgumentException("No hay patrones válidos cargados");
        }
        // Obligatoriamente debe haber un tile Blank.png
        boolean isContainsBlank = false;
        for (File f : this.tilesFiles) {
            if (f.getName().contains("BLANK.")) {
                isContainsBlank = true;
            }
        }
        if (!isContainsBlank) {
            throw new IllegalArgumentException("No hay un patrón con el nombre Blank");
        }

    }

    public void generateTilesRotations() {
        loadTiles();
        loadTilesConexions();

        try (BufferedReader reader = new BufferedReader(new FileReader(conexionsFile))) {
            List<Conexion> conexions = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                int[] arr = new int[values.length];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = Short.parseShort(values[i]);
                }
                conexions.add(new Conexion(arr));
            }

            this.tiles = new Tile[tilesFiles.length * conexions.get(0).getSegments()];
            int index = 0;
            int filesLength = 0;
            for (File pattern : tilesFiles) {
                for (int rotation = 0; rotation < conexions.get(0).getSegments(); rotation++) {
                    this.tiles[index] = new Tile(rotation, pattern.getName(), conexions.get(filesLength), (short) 0);
                    index++;
                }
                filesLength++;
            }

        } catch (Exception e) {
            e.getCause();
        }
    }

    public void rotations() {

        for (Cell[] cellRow : this.grid.getSpaces()) {
            for (Cell cell : cellRow) {
                Arrays.stream(cell.getEntropy()).forEach(t -> t.rotateTile());
            }
        }
    }

    public Grid getGrid() {
        return this.grid;
    }

    // #region Draw and Update Entropie

    public void fillEntropie() {
        Arrays.stream(this.grid.getSpaces())
                .flatMap(Arrays::stream)
                .forEach(cell -> cell.setEntropy(this.tiles));
    }

    public Cell getCellWithMinimumEntropy() {
        return Arrays.stream(this.grid.getSpaces())
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.isColapsed())
                .min(Comparator.comparingInt(Cell::getEntropyLenght))
                .get();
    }

    public boolean checkIsAllCollapsed() {
        boolean isAllCollapsed = true;
        for (Cell[] cell : this.grid.getSpaces()) {
            for (Cell c : cell) {
                if (!c.isColapsed()) {
                    return isAllCollapsed = false;
                }
            }
        }

        return isAllCollapsed;
    }

    public boolean restartGrid() {
        boolean hasEmptyCells = Arrays.stream(grid.getSpaces())
                .flatMap(Arrays::stream)
                .anyMatch(cell -> cell.getEntropy().length == 0);

        return hasEmptyCells;
    }

    public void clearEntropie() {
        Arrays.stream(this.grid.getSpaces())
                .flatMap(Arrays::stream)
                .forEach(cell -> cell.setEntropy(new Tile[0]));
    }

}
