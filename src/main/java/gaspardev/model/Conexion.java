package gaspardev.model;

public class Conexion {

    private int segments = 4;
    private int rotations[] = { 0, 0, 0, 0 };

    public Conexion() {

    }

    public Conexion(int[] rotations) {
        this.rotations = rotations;
        this.segments = rotations.length;
    }

    public int getSegments() {
        return this.segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public int[] getRotations() {
        return this.rotations;
    }

    public void setRotations(int[] rotations) {
        this.rotations = rotations;
    }

}
