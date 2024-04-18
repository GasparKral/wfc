package gaspardev.model;

import java.io.Serializable;
import java.util.Arrays;

public class Cell implements Serializable {

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

    public void updateEntropie(Tile deleteEntropy) {
        this.entropy = Arrays.stream(this.entropy).filter(tile -> tile != deleteEntropy).toArray(Tile[]::new);
    }

    public Tile getRandomEntropieValueTile() {

        double index = Math.random();
        double array[] = new double[entropy.length];
        Tile returnedTile = null;

        for (int i = 0; i < array.length; i++) {
            array[i] = (1 / array.length) * this.getEntropy()[i].getWeight();
        }

        for (int i = 0; i < array.length; i++) {
            if (index >= array[i]) {
                returnedTile = this.getEntropy()[i];
            }
        }
        return returnedTile;
    }

    @Override
    public String toString() {
        return "{" +
                " posX='" + getPosX() + "'" +
                ", posY='" + getPosY() + "'" +
                ", isColapsed='" + isColapsed() + "'" +
                ", colapsedTile='" + getColapsedTile() + "'" +
                ", neighbors='" + getNeighbors() + "'" +
                ", entropy='" + getEntropy() + "'" +
                "}";
    }

}
