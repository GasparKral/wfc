package gaspardev.model;

import java.io.Serializable;
import java.util.Collections;
import gaspardev.model.Cell.Direction;
import java.util.ArrayList;

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

    public String getConexions() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.conexions.getRotations().length; i++) {
            sb.append(conexions.getRotations()[i]);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
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

    public static boolean compare(Tile t1, Tile t2) {
        if (t1.getImg().equalsIgnoreCase(t2.getImg()) && t1.getRotation() == t2.getRotation()) {
            return true;
        } else {
            return false;
        }
    }

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

    public void rotateTile() {

        ArrayList<Integer> tempArr = new ArrayList<>();
        for (int i = 0; i < this.conexions.getRotations().length; i++) {
            tempArr.add(this.conexions.getRotations()[i]);
        }
        Collections.rotate(tempArr, this.rotation);
        int[] returnedCon = new int[tempArr.size()];
        for (int i = 0; i < tempArr.size(); i++) {
            returnedCon[i] = tempArr.get(i);
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
