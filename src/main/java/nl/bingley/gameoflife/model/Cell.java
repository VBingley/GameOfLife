package nl.bingley.gameoflife.model;

public class Cell {

    private final long positionX;
    private final long positionY;

    public Cell(long positionX, long positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public long getPositionX() {
        return positionX;
    }

    public long getPositionY() {
        return positionY;
    }
}
