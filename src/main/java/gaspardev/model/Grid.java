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
     * It iterates over each row and column in the grid and creates a new Cell
     * object with the current indices.
     * The new Cell object is then assigned to the corresponding position in the
     * grid.
     *
     * @param None This function does not take any parameters.
     * @return None This function does not return any value.
     */
    public void fillSpaces() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.spaces[x][y] = new Cell(x, y);
            }
        }
    }

    /**
     * Connects the neighbors of each cell in the grid.
     *
     * This function iterates over each cell in the grid and connects it with its
     * neighbors. It checks the cells to the left, right, above, and below the
     * current cell and adds them to the list of neighbors. The neighbors are
     * then set using the `setNeighbors` method of the `Cell` class.
     *
     * @return void
     */
    public void connectNeighbors() {
        for (int i = 0; i < spaces.length; i++) {
            for (int j = 0; j < spaces[i].length; j++) {
                Cell cell = spaces[i][j];
                if (cell != null) {
                    List<Cell> neighbors = new ArrayList<>();

                    if (j - 1 >= 0) {
                        neighbors.add(getCellOrNull(i, j - 1));
                    }
                    if (i + 1 < spaces.length) {
                        neighbors.add(getCellOrNull(i + 1, j));
                    }
                    if (j + 1 < spaces[i].length) {
                        neighbors.add(getCellOrNull(i, j + 1));
                    }
                    if (i - 1 >= 0) {
                        neighbors.add(getCellOrNull(i - 1, j));
                    }

                    cell.setNeighbors(neighbors.toArray(new Cell[0]));
                }
            }
        }
    }

    /**
     * Retrieves the cell at the specified row and column if it exists within the
     * grid, otherwise returns null.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the cell at the specified row and column if it exists within the
     *         grid, otherwise null
     */
    private Cell getCellOrNull(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width ? spaces[row][col] : null;
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