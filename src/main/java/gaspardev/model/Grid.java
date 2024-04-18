package gaspardev.model;

import java.util.Iterator;
import java.io.Serializable;

public class Grid implements Iterable<Cell>, Serializable {

    private int width;
    private int height;
    private Cell[][] spaces;

    public Grid() {
        width = 50;
        height = 50;
        this.spaces = new Cell[width][height];
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

    public void fillSpaces() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.spaces[i][j] = new Cell(i, j);
            }
        }
    }

    public void fillNeighbors() {

        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    this.spaces[i][j].setNeighbors(new Cell[] {
                            this.getCell(i, j - 1),
                            this.getCell(i + 1, j),
                            this.getCell(i, j + 1),
                            this.getCell(i - 1, j)
                    });
                }
            }
        } catch (Exception e) {
            e.getCause();
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