package nl.bingley.gameoflife;

import java.util.ArrayList;
import java.util.List;

public class Universe {

    private final int boundaryX;
    private final int boundaryY;

    private Boolean[][] previousState;
    private Boolean[][] currentState;

    //ew don't look
    public int currentBorn;
    public int currentAlive;
    public int currentDied;

    private final StringBuilder initialState;

    public Universe(int boundaryX, int boundaryY, int initialSize) {
        initialState = new StringBuilder();
        this.boundaryX = boundaryX;
        this.boundaryY = boundaryY;
        previousState = new Boolean[boundaryX][boundaryY];
        currentState = new Boolean[boundaryX][boundaryY];
        initialize(initialSize);
    }

    public Universe(int boundaryX, int boundaryY, String generated) {
        initialState = new StringBuilder();
        initialState.append(generated);
        this.boundaryX = boundaryX;
        this.boundaryY = boundaryY;
        previousState = new Boolean[boundaryX][boundaryY];
        currentState = new Boolean[boundaryX][boundaryY];
        String[] rows = generated.split(System.lineSeparator());
        int xStart = boundaryX / 2 - rows.length / 2;
        for (int x = xStart; x < xStart + rows.length; x++) {
            int yStart = boundaryY / 2 - rows.length / 2;
            for (int y = yStart; y < yStart + rows.length; y++) {
                currentState[x][y] = rows[y - yStart].charAt(x - xStart) == '0';
            }
        }
    }

    private void initialize(int initialSize) {
        int yStart = boundaryY / 2 - initialSize / 2;
        for (int y = yStart; y < yStart + initialSize; y++) {
            int xStart = boundaryX / 2 - initialSize / 2;
            for (int x = xStart; x < xStart + initialSize; x++) {
                long random = Math.round(Math.random());
                if (random == 1) {
                    currentState[x][y] = true;
                    initialState.append('0');
                } else {
                    initialState.append('.');
                }
            }
            initialState.append(System.lineSeparator());
        }
    }

    public void setState(boolean value, int x, int y) {
        currentState[x][y] = value;
    }

    public boolean getState(int x, int y) {
        if (currentState[x][y] != null) {
            return currentState[x][y];
        } else {
            return false;
        }
    }

    public boolean getPreviousState(int x, int y) {
        if (previousState[x][y] != null) {
            return previousState[x][y];
        } else {
            return false;
        }
    }

    public void tick() {
        currentBorn = 0;
        currentAlive = 0;
        currentDied = 0;
        Boolean[][] nextState = new Boolean[boundaryX][boundaryY];
        for (int x = 0; x < boundaryX; x++) {
            for (int y = 0; y < boundaryY; y++) {
                nextState[x][y] = getNextState(x, y);
            }
        }
        previousState = currentState;
        currentState = nextState;
    }

    public int getBoundaryX() {
        return boundaryX;
    }

    public int getBoundaryY() {
        return boundaryY;
    }

    private boolean getNextState(int x, int y) {
        boolean current = getState(x, y);
        int adjacent = countAdjacentCells(x, y);
        if (adjacent == 2) {
            if (current) {
                currentAlive++;
            }
            return current;
        } else {
            if (adjacent == 3) {
                if (current) {
                    currentAlive++;
                } else {
                    currentBorn++;
                }
                return true;
            } else {
                if (current) {
                    currentDied++;
                }
                return false;
            }
        }
    }

//    private int countAdjacentCells(int x, int y) {
//        List<Boolean> states = new ArrayList<>();
//        states.add(getStateStopAtBoundary(x - 1, y - 1));
//        states.add(getStateStopAtBoundary(x - 1, y));
//        states.add(getStateStopAtBoundary(x - 1, y + 1));
//        states.add(getStateStopAtBoundary(x, y - 1));
//        states.add(getStateStopAtBoundary(x, y + 1));
//        states.add(getStateStopAtBoundary(x + 1, y - 1));
//        states.add(getStateStopAtBoundary(x + 1, y));
//        states.add(getStateStopAtBoundary(x + 1, y + 1));
//
//        return (int) states.stream()
//                .filter(Boolean::booleanValue)
//                .count();
//    }

    private int countAdjacentCells(int x, int y) {
        List<Boolean> states = new ArrayList<>();
        states.add(getState(wrapAtBoundary(x - 1, boundaryX), wrapAtBoundary(y - 1, boundaryY)));
        states.add(getState(wrapAtBoundary(x - 1, boundaryX), wrapAtBoundary(y, boundaryY)));
        states.add(getState(wrapAtBoundary(x - 1, boundaryX), wrapAtBoundary(y + 1, boundaryY)));
        states.add(getState(wrapAtBoundary(x, boundaryX), wrapAtBoundary(y - 1, boundaryY)));
        states.add(getState(wrapAtBoundary(x, boundaryX), wrapAtBoundary(y + 1, boundaryY)));
        states.add(getState(wrapAtBoundary(x + 1, boundaryX), wrapAtBoundary(y - 1, boundaryY)));
        states.add(getState(wrapAtBoundary(x + 1, boundaryX), wrapAtBoundary(y, boundaryY)));
        states.add(getState(wrapAtBoundary(x + 1, boundaryX), wrapAtBoundary(y + 1, boundaryY)));

        return (int) states.stream()
                .filter(Boolean::booleanValue)
                .count();
    }

    private int wrapAtBoundary(int pos, int boundary) {
        if (pos < 0) {
            return boundary + pos;
        } else {
            return pos % boundary;
        }
    }

    private boolean getStateStopAtBoundary(int x, int y) {
        if (x < 0 || y < 0 || x >= boundaryX || y >= boundaryY) {
            return false;
        } else {
            return getState(x, y);
        }
    }

    @Override
    public String toString() {
        return initialState.toString();
    }
}
