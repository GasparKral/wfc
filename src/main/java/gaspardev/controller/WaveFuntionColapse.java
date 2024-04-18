package gaspardev.controller;

import gaspardev.model.Grid;
import gaspardev.model.Tile;
import gaspardev.model.Cell;
import gaspardev.model.Conexion;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Arrays;
import java.util.Optional;
import java.util.Comparator;
import java.util.ArrayList;

public class WaveFuntionColapse {

    protected File tilesDir = new File("C:/programar/wfc/src/main/resources/images/Tiles");
    protected File conexionsFile = new File("C:/programar/wfc/src/main/resources/data/Conexions/conexions.csv");
    protected File[] tilesFiles;

    private Grid grid;
    private Tile[] tiles;
    private Conexion[] conexcions;

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

        try (BufferedReader br = new BufferedReader(new FileReader(conexionsFile))) {
            ArrayList<Conexion> tempConexcions = new ArrayList<>();

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] values = linea.split(",");
                short[] tempArr = new short[values.length];
                for (int i = 0; i < tempArr.length; i++) {
                    tempArr[i] = Short.parseShort(values[i]);
                }
                tempConexcions.add(new Conexion(tempArr));
            }

            this.conexcions = new Conexion[tempConexcions.size()];
            for (int i = 0; i < tempConexcions.size(); i++) {
                this.conexcions[i] = tempConexcions.get(i);
            }

            final int conecxions = conexcions.length;
            final int tiles = this.tilesFiles.length;

            this.tiles = new Tile[conecxions * tiles];
            int index = 0;
            for (int i = 0; i < conecxions; i++) {
                for (int j = 0; j < tiles; j++) {
                    this.tiles[index] = new Tile(i, this.tilesFiles[j].getName(), this.conexcions[i], (short) 0);
                    index++;
                }
            }

        } catch (Exception e) {
            e.getCause();
        }

    }

    public void showTiles() {
        for (int i = 0; i < tiles.length; i++) {
            System.out.println(tiles[i].getImg() + " " + tiles[i].getRotation());
        }
    }

    public void showTilesFiles() {
        for (File f : this.tilesFiles) {
            System.out.println(f.getName());
        }
    }

    public Grid getGrid() {
        return grid;
    }

    // #region Draw and Update Entropie

    public void fillEntropie() {
        this.grid.forEach(cell -> {
            cell.setEntropy(tiles);
        });
    }

    private Cell getTheLessEntropyCell() {
        return Arrays.stream(this.grid.getSpaces())
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.isColapsed())
                .sorted(Comparator.comparingInt(Cell::getEntropyLenght))
                .findFirst()
                .map(cell -> {
                    cell.setColapsedR(true);
                    cell.colapseTile(cell.getRandomEntropieValueTile());
                    return cell;
                })
                .orElse(null);
    }

    private void updateNeighbors(Cell cell) {

        Arrays.stream(cell.getNeighbors()).forEach(neighbor -> {
            if (neighbor != null) {
                this.grid.getCell(neighbor.getPosX(), neighbor.getPosY()).updateEntropie(cell.getColapsedTile());
            }
        });
    }

    private Cell getRandomCell() {
        int x = (int) (Math.random() * this.grid.getWidth());
        int y = (int) (Math.random() * this.grid.getHeight());

        return this.grid.getCell(x, y);
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

    public void draw() {

        fillEntropie();
        this.getRandomCell().setColapsedR(true).colapseTile(this.getRandomCell().getRandomEntropieValueTile());
        do {
            updateNeighbors(getTheLessEntropyCell());
        } while (!checkIsAllCollapsed());

    }

}
