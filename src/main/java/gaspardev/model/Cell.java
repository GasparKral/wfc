package gaspardev.model;

public class Cell {

    private boolean isColapsed = false;
    private Tile colapsedTile;
    private Cell[] neighbors = new Cell[4];
    private Tile[] entropy;

    public Cell() {

    }

    public Cell(Tile[] entropy) {
        this.entropy = entropy;
    }

    public boolean isColapsed() {
        return this.isColapsed;
    }

    public void setColapsed(boolean isColapsed) {
        this.isColapsed = isColapsed;
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

    public void setEntropy(Tile[] entropy) {
        this.entropy = entropy;
    }

    public void colpasCell(Tile colapsTile) {
        this.isColapsed = true;
        this.colapsedTile = colapsTile;
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
                " isColapsed='" + isColapsed() + "'" +
                ", colapsedTile='" + getColapsedTile() != null ? getColapsedTile().getImg()
                        : "null" + "'" +
                                ", neighbors='" + getNeighbors() + "'" +
                                ", entropy='" + getEntropy() + "'" +
                                "}";
    }

}
