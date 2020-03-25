package nl.bingley.gameoflife.model;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Universe {

    private static final int initialSize = 128;

    private boolean paused = false;

    private Space space;
    private int generation;
    private int born;
    private int alive;
    private int died;

    private final StringBuilder initialPattern;

    public Universe() {
        initialPattern = new StringBuilder();
        space = new Space();
        initialize();
    }

    public Universe(String generated) {
        initialPattern = new StringBuilder();
        initialPattern.append(generated);
        space = new Space();
        generate();
    }

    public void refresh() {
        space = new Space();
        initialize();
    }

    public void restart() {
        space = new Space();
        generate();
    }

    public void tick() {
        generation++;
        born = 0;
        alive = 0;
        died = 0;
        Space newSpace = new Space();
        for (Cell cell : space.getAliveCells()) {
            for (int x = cell.getPositionX() - 1; x <= cell.getPositionX() + 1; x++) {
                for (int y = cell.getPositionY() - 1; y <= cell.getPositionY() + 1; y++) {
                    if (!newSpace.isAlive(x, y) && isAliveAfterTick(x, y)) {
                        newSpace.addCell(x, y);
                        if (space.isAlive(x, y)) {
                            alive++;
                        } else {
                            born++;
                        }
                    }
                }
            }
        }
        space = newSpace;
    }

    private boolean isAliveAfterTick(int x, int y) {
        boolean isAlive = space.isAlive(x, y);
        int adjacentCells = countAdjacentCells(x, y);
        return (!isAlive && adjacentCells == 3) || (isAlive && (adjacentCells == 2 || adjacentCells == 3));
    }

    private int countAdjacentCells(int posX, int posY) {
        int counter = 0;
        for (int x = posX - 1; x <= posX + 1; x++) {
            for (int y = posY - 1; y <= posY + 1; y++) {
                if (!(x == posX && y == posY) && space.isAlive(x, y)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private void initialize() {
        initialPattern.delete(0, initialPattern.length());
        generation = 0;
        int yStart = -initialSize / 2;
        for (int y = yStart; y < yStart + initialSize; y++) {
            int xStart = -initialSize / 2;
            for (int x = xStart; x < xStart + initialSize; x++) {
                long random = Math.round(Math.random());
                if (random == 1) {
                    space.addCell(x, y);
                    initialPattern.append('0');
                } else {
                    initialPattern.append('.');
                }
            }
            initialPattern.append(System.lineSeparator());
        }
    }

    private void generate() {
        generation = 0;
        String[] rows = initialPattern.toString().split(System.lineSeparator());
        int xStart = -rows.length / 2;
        for (int x = xStart; x < xStart + rows.length; x++) {
            int yStart = -rows.length / 2;
            for (int y = yStart; y < yStart + rows.length; y++) {
                if (rows[y - yStart].charAt(x - xStart) == '0') {
                    space.addCell(x, y);
                }
            }
        }
    }

    @Override
    public String toString() {
        return initialPattern.toString();
    }

    public int getGeneration() {
        return generation;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Collection<Cell> getAliveCells() {
        return space.getAliveCells();
    }

    public int getBorn() {
        return born;
    }

    public int getAlive() {
        return alive;
    }

    public int getDied() {
        return died;
    }
}
