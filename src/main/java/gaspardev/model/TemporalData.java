package gaspardev.model;

public class TemporalData {

    private int width;
    private int height;
    private short[][] data;

    public TemporalData() {

    }

    public TemporalData(int width, int height, short[][] data) {
        this.width = width;
        this.height = height;
        this.data = data;
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

    public TemporalData height(int height) {
        setHeight(height);
        return this;
    }

    public short[][] getData() {
        return this.data;
    }

    public void setData(short[][] data) {
        this.data = data;
    }

}
