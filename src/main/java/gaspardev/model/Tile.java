package gaspardev.model;

import java.io.Serializable;
import java.util.stream.IntStream;

import gaspardev.model.Cell.Direction;

public class Tile implements Serializable {

    private int rotation = 0;
    private String img;
    private Conexion conexions = new Conexion(new int[] { 0, 0, 0, 0 });
    private int weight = 0;

    public Tile() {

    }

    public Tile(int rotation, String img, int weight) {
        this.rotation = rotation;
        this.img = img;
        this.weight = weight;
    }

    public Tile(int rotation, String img, Conexion conexcions, int weight) {
        this.rotation = rotation;
        this.img = img;
        this.conexions = conexcions;
        this.weight = weight;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int[] getConexcions() {
        return this.conexions.getRotations();
    }

    public void setConexcions(int[] conexcions) {
        this.conexions.setRotations(conexcions);
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * A method to generate a string representation of the connections of the tile.
     *
     * @return the string representation of the connections
     */
    public String getConexions() {
        int[] conexions = this.conexions.getRotations();
        return "[" + String.join(", ",
                IntStream.range(0, conexions.length)
                        .mapToObj(i -> String.valueOf(conexions[i]))
                        .toArray(String[]::new))
                + "]";
    }

    /**
     * Compares two Tile objects and checks if their image and rotation are equal.
     *
     * @param t1 the first Tile object to compare
     * @param t2 the second Tile object to compare
     * @return true if the image and rotation of t1 and t2 are equal, false
     *         otherwise
     */
    public static boolean compare(Tile t1, Tile t2) {
        return t1.rotation == t2.rotation &&
                (t1.img == t2.img || t1.img != null && t1.img.equalsIgnoreCase(t2.img));
    }

    /**
     * Checks if the given tile can connect to this tile in the specified direction.
     *
     * @param t         the tile to check connection with
     * @param direction the direction to check connection in
     * @return true if the tiles can connect, false otherwise
     */
    public boolean checkIfCanConect(Tile t, Direction direction) {

        switch (direction) {
            case UP:
                if (this.conexions.getRotations()[0] == t.conexions.getRotations()[2]) {
                    return true;
                } else {
                    return false;
                }
            case DOWN:
                if (this.conexions.getRotations()[2] == t.conexions.getRotations()[0]) {
                    return true;
                } else {
                    return false;
                }
            case LEFT:
                if (this.conexions.getRotations()[3] == t.conexions.getRotations()[1]) {
                    return true;
                } else {
                    return false;
                }
            case RIGHT:
                if (this.conexions.getRotations()[1] == t.conexions.getRotations()[3]) {
                    return true;
                } else {
                    return false;
                }
            default:
                break;
        }

        return false;

    }

    /**
     * Rotates the tile by updating the connections.
     *
     * @param None
     * @return None
     */
    public void rotateTile() {

        int[] returnedCon = new int[this.conexions.getRotations().length];
        for (int i = 0, j = this.rotation; j < returnedCon.length; i++, j++) {
            returnedCon[j] = this.conexions.getRotations()[i];
        }
        for (int i = 0; i < this.rotation; i++) {
            returnedCon[i] = this.conexions.getRotations()[returnedCon.length - this.rotation + i];
        }
        this.conexions = new Conexion(returnedCon);

    }

    @Override
    public String toString() {
        return "{" +
                " rotation='" + getRotation() + "'" +
                ", img='" + getImg() + "'" +
                ", conexcions='" + getConexions() + "'" +
                ", weight='" + getWeight() + "'" +
                "}";
    }

}
