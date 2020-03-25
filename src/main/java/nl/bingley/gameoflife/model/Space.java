package nl.bingley.gameoflife.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Space {

    private static final int SIZE = 50;

    private final List<Cell> aliveCells;

    private Boolean[][] plusXplusY;
    private Boolean[][] plusXminY;
    private Boolean[][] minXplusY;
    private Boolean[][] minXminY;

    public Space() {
        aliveCells = new ArrayList<>();
        plusXplusY = new Boolean[SIZE][SIZE];
        plusXminY = new Boolean[SIZE][SIZE];
        minXplusY = new Boolean[SIZE][SIZE];
        minXminY = new Boolean[SIZE][SIZE];
    }

    public List<Cell> getAliveCells() {
        return aliveCells;
    }

    public boolean isAlive(int x, int y) {
        try {
            if (x >= 0 && y >= 0) {
                return plusXplusY[x][y];
            } else if (x >= 0) { // y<0 implied
                return plusXminY[x][Math.abs(y)];
            } else if (y >= 0) { // x<0 implied
                return minXplusY[Math.abs(x)][y];
            } else {
                return minXminY[Math.abs(x)][Math.abs(y)];
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public void addCell(int x, int y) {
        aliveCells.add(new Cell(x, y));
        if (x >= 0 && y >= 0) {
            plusXplusY = setValue(plusXplusY, x, y);
        } else if (x >= 0) { // y<0 implied
            plusXminY = setValue(plusXminY, x, Math.abs(y));
        } else if (y >= 0) { // x<0 implied
            minXplusY = setValue(minXplusY, Math.abs(x), y);
        } else {
            minXminY = setValue(minXminY, Math.abs(x), Math.abs(y));
        }
    }

    private Boolean[][] setValue(Boolean[][] array, int x, int y) {
        Boolean[] arrayY;
        try {
            arrayY = array[x];
        } catch (ArrayIndexOutOfBoundsException e) {
            array = Arrays.copyOf(array, x + SIZE);
            arrayY = array[x];
        }
        if (arrayY == null) {
            arrayY = new Boolean[y];
        }
        try {
            arrayY[y] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            arrayY = Arrays.copyOf(arrayY, y + SIZE);
            arrayY[y] = true;
            array[x] = arrayY;
        }
        return array;
    }
}
