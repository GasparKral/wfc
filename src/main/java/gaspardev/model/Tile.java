package gaspardev.model;

import java.io.Serializable;

public class Tile implements Serializable {

    private int rotation = 0;
    private String img;
    private Conexion conexions = new Conexion(new short[] { 0, 0, 0, 0 });
    private short weight = 0;

    public Tile() {

    }

    public Tile(int rotation, String img, short weight) {
        this.rotation = rotation;
        this.img = img;
        this.weight = weight;
    }

    public Tile(int rotation, String img, Conexion conexcions, short weight) {
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

    public short[] getConexcions() {
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

    public void setConexcions(short[] conexcions) {
        this.conexions.setRotations(conexcions);
    }

    public short getWeight() {
        return this.weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public static boolean compare(Tile t1, Tile t2) {
        if (t1.getImg().equalsIgnoreCase(t2.getImg()) && t1.getRotation() == t2.getRotation()) {
            return true;
        } else {
            return false;
        }
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
