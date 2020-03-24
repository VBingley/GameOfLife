package nl.bingley.gameoflife;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Universe {

    private static final int initialSize = 5;
    private static final int universeX = 400;
    private static final int universeY = 250;

    private Boolean[][] previousState;
    private Boolean[][] currentState;

    private boolean paused = false;

    private int generation;
    private int currentBorn;
    private int currentAlive;
    private int currentDied;

    private final StringBuilder initialState;

    public Universe() {
        initialState = new StringBuilder();
        previousState = new Boolean[universeX][universeY];
        currentState = new Boolean[universeX][universeY];
        initialize();
    }

    public Universe(String generated) {
        initialState = new StringBuilder();
        initialState.append(generated);
        previousState = new Boolean[universeX][universeY];
        currentState = generate(generated);
    }

    public void refresh() {
        previousState = new Boolean[universeX][universeY];
        currentState = new Boolean[universeX][universeY];
        initialize();
    }

    public void restart() {
        previousState = new Boolean[universeX][universeY];
        currentState = generate(initialState.toString());
    }

    public void tick() {
        generation++;
        currentBorn = 0;
        currentAlive = 0;
        currentDied = 0;
        Boolean[][] nextState = new Boolean[universeX][universeY];
        for (int x = 0; x < universeX; x++) {
            for (int y = 0; y < universeY; y++) {
                nextState[x][y] = getNextState(x, y);
            }
        }
        previousState = currentState;
        currentState = nextState;
    }

    public int getUniverseX() {
        return universeX;
    }

    public int getUniverseY() {
        return universeY;
    }

    public int getGeneration() {
        return generation;
    }

    public int getCurrentBorn() {
        return currentBorn;
    }

    public int getCurrentAlive() {
        return currentAlive;
    }

    public int getCurrentDied() {
        return currentDied;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean getPreviousState(int x, int y) {
        if (previousState[x][y] != null) {
            return previousState[x][y];
        } else {
            return false;
        }
    }

    public boolean getState(int x, int y) {
        if (currentState[x][y] != null) {
            return currentState[x][y];
        } else {
            return false;
        }
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

    private void initialize() {
        initialState.delete(0, initialState.length());
        generation = 0;
        int yStart = universeY / 2 - initialSize / 2;
        for (int y = yStart; y < yStart + initialSize; y++) {
            int xStart = universeX / 2 - initialSize / 2;
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

    private Boolean[][] generate(String generated) {
        generation = 0;
        Boolean[][] state = new Boolean[universeX][universeY];
        String[] rows = generated.split(System.lineSeparator());
        int xStart = universeX / 2 - rows.length / 2;
        for (int x = xStart; x < xStart + rows.length; x++) {
            int yStart = universeY / 2 - rows.length / 2;
            for (int y = yStart; y < yStart + rows.length; y++) {
                state[x][y] = rows[y - yStart].charAt(x - xStart) == '0';
            }
        }
        return state;
    }

    private int countAdjacentCells(int x, int y) {
        List<Boolean> states = new ArrayList<>();
        states.add(getState(wrapAtBoundary(x - 1, universeX), wrapAtBoundary(y - 1, universeY)));
        states.add(getState(wrapAtBoundary(x - 1, universeX), wrapAtBoundary(y, universeY)));
        states.add(getState(wrapAtBoundary(x - 1, universeX), wrapAtBoundary(y + 1, universeY)));
        states.add(getState(wrapAtBoundary(x, universeX), wrapAtBoundary(y - 1, universeY)));
        states.add(getState(wrapAtBoundary(x, universeX), wrapAtBoundary(y + 1, universeY)));
        states.add(getState(wrapAtBoundary(x + 1, universeX), wrapAtBoundary(y - 1, universeY)));
        states.add(getState(wrapAtBoundary(x + 1, universeX), wrapAtBoundary(y, universeY)));
        states.add(getState(wrapAtBoundary(x + 1, universeX), wrapAtBoundary(y + 1, universeY)));

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

    @Override
    public String toString() {
        return initialState.toString();
    }
}
