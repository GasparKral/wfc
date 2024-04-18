package gaspardev.model;

public class Conexion {

    private int segments = 4;
    private short rotations[] = { 0, 0, 0, 0 };

    public Conexion() {

    }

    public Conexion(short[] rotations) {
        this.rotations = rotations;
        this.segments = rotations.length;
    }

    public int getSegments() {
        return this.segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public short[] getRotations() {
        return this.rotations;
    }

    public void setRotations(short[] rotations) {
        this.rotations = rotations;
    }

}
