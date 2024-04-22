package gaspardev.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Cell implements Serializable, Comparable<Cell> {

    private int posX;
    private int posY;
    private boolean isColapsed = false;
    private Tile colapsedTile;
    private Cell[] neighbors = new Cell[4];
    private Tile[] entropy;

    public Cell() {

    }

    public Cell(Tile[] entropy) {
        this.entropy = entropy;
    }

    public Cell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public boolean isColapsed() {
        return this.isColapsed;
    }

    public void setColapsed(boolean isColapsed) {
        this.isColapsed = isColapsed;
    }

    public Cell setColapsedR(boolean isColapsed) {
        this.isColapsed = isColapsed;
        return this;
    }

    public void colapseTile(Tile colapsTile) {
        this.colapsedTile = colapsTile;
        this.isColapsed = true;
    }

    public Cell colapseTileR() {
        this.colapsedTile = getTheBestTile();
        this.isColapsed = true;
        return this;
    }

    public static Tile getColapsedTile(Cell cell) {
        return cell.getColapsedTile();
    }

    public Tile getColapsedTile() {
        return this.colapsedTile;
    }

    public Cell[] getNeighbors() {
        return this.neighbors;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setNeighbors(Cell[] neighbors) {
        try {
            if (neighbors.length == 4) {
                this.neighbors = neighbors;
            } else {
                throw new Exception("La lista de vecinos debe ser de 4 elementos");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los vecinos: " + e.getMessage());
        }
    }

    public Tile[] getEntropy() {
        return this.entropy;
    }

    public static int getEntropyLenght(Cell cell) {
        return cell.getEntropy().length;
    }

    public void setEntropy(Tile[] entropy) {
        this.entropy = entropy;
    }

    public void colpasCell(Tile colapsTile) {
        this.isColapsed = true;
        this.colapsedTile = colapsTile;
    }

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    public Direction getDirection(Cell cell) {

        int vecY = cell.getPosY() - this.getPosY();
        int vecX = cell.getPosX() - this.getPosX();

        if (vecY > 0) {
            return Direction.DOWN;
        } else if (vecY < 0) {
            return Direction.UP;
        } else if (vecX > 0) {
            return Direction.RIGHT;
        } else if (vecX < 0) {
            return Direction.LEFT;
        } else {
            return null;
        }

    }

    private void updateEntropie(Tile deleteEntropy) {
        this.entropy = Arrays.stream(this.entropy).filter(tile -> tile != deleteEntropy).toArray(Tile[]::new);
    }

    public void updateNeighborsValue() {

        Arrays.stream(this.neighbors)
                .forEach(neighbor -> {
                    if (neighbor != null) {
                        for (Tile t : neighbor.getEntropy()) {
                            if (!this.colapsedTile.checkIfCanConect(t, this.getDirection(neighbor)))
                                neighbor.updateEntropie(t);
                        }
                    }
                });

    }

    public Tile getTheBestTile() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.entropy.length - 1);
        Tile randomTile = Arrays.stream(
                this.entropy)
                .sorted(Comparator.comparingInt(Tile::getWeight))
                .skip(randomIndex)
                .findFirst()
                .orElse(null);
        return randomTile;
    }

    @Override
    public int compareTo(Cell o) {
        return this.getEntropy().length - o.getEntropy().length;
    }

    @Override
    public String toString() {
        return "{" +
                " posX='" + getPosX() + "'" +
                ", posY='" + getPosY() + "'" +
                ", isColapsed='" + isColapsed() + "'" +
                ", colapsedTile='" + getColapsedTile() + "'";
    }

}
