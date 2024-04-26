package gaspardev.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

public class Grid implements Iterable<Cell>, Serializable {

    private int width;
    private int height;
    private Cell[][] spaces;

    public Grid() {
        width = 50;
        height = 50;
    }

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.spaces = new Cell[width][height];
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Cell[][] getSpaces() {
        return this.spaces;
    }

    public void setSpaces(Cell[][] spaces) {
        this.spaces = spaces;
    }

    public Cell getCell(int x, int y) {
        return this.spaces[x][y];
    }

    /**
     * Fills the spaces in the grid with cells.
     *
     * This function initializes each cell in the grid with its corresponding row
     * and column indices.
     * It does this by creating a single array of Cell objects, where each Cell
     * object is assigned a unique index based on its row and column indices.
     *
     * This function is much faster and more efficient than the previous version,
     * because it eliminates the nested for loops which caused the algorithm to
     * have a time complexity of O(n^2).
     *
     * @param None This function does not take any parameters.
     * @return None This function does not return any value.
     */
    public void fillSpaces() {
        Cell[] cells = new Cell[this.width * this.height];
        for (int i = 0, x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++, i++) {
                cells[i] = new Cell(x, y, this.height);
            }
        }
        this.spaces = new Cell[this.width][this.height];
        for (int i = 0; i < cells.length; i++) {
            int x = i % this.width;
            int y = i / this.width;
            this.spaces[x][y] = cells[i];
        }
    }

    /**
     * Connects the neighbors of each cell in the grid.
     *
     * This function iterates over each cell in the grid and connects it with its
     * neighbors. It does this by iterating over the rows and columns of the grid
     * only once, and using the indices to determine which neighbors to add to
     * the list.
     *
     * @return void
     */
    public void connectNeighbors() {
        for (int i = 0; i < spaces.length; i++) {
            for (int j = 0; j < spaces[i].length; j++) {
                Cell cell = spaces[i][j];
                if (cell != null) {
                    List<Cell> neighbors = new ArrayList<>(4);

                    if (j > 0) {
                        neighbors.add(spaces[i][j - 1]);
                    }
                    if (i < spaces.length - 1) {
                        neighbors.add(spaces[i + 1][j]);
                    }
                    if (j < spaces[i].length - 1) {
                        neighbors.add(spaces[i][j + 1]);
                    }
                    if (i > 0) {
                        neighbors.add(spaces[i - 1][j]);
                    }

                    cell.setNeighbors(neighbors.toArray(new Cell[0]));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "{" +
                " width='" + getWidth() + "'" +
                ", height='" + getHeight() + "'" +
                ", spaces='" + getSpaces() + "'" +
                "}";
    }

    @Override
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int i = 0;
            int j = 0;

            @Override
            public boolean hasNext() {
                return i < width && j < height;
            }

            @Override
            public Cell next() {
                Cell cell = spaces[i][j];
                j++;
                if (j == height) {
                    i++;
                    j = 0;
                }
                return cell;
            }
        };
    }

}