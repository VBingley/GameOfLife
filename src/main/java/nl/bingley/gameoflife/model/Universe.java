package nl.bingley.gameoflife.model;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Universe {

    private static final int initialSize = 64;

    private final Set<Cell> bornCells;
    private final Set<Cell> aliveCells;
    private final Set<Cell> diedCells;

    private boolean paused = false;

    private int generation;

    private final StringBuilder initialPattern;

    public Universe() {
        initialPattern = new StringBuilder();
        bornCells = new HashSet<>();
        aliveCells = new HashSet<>();
        diedCells = new HashSet<>();
        initialize();
    }

    public Universe(String generated) {
        initialPattern = new StringBuilder();
        initialPattern.append(generated);
        bornCells = new HashSet<>();
        aliveCells = new HashSet<>();
        diedCells = new HashSet<>();
        generate();
    }

    public void refresh() {
        bornCells.clear();
        aliveCells.clear();
        diedCells.clear();
        initialize();
    }

    public void restart() {
        bornCells.clear();
        aliveCells.clear();
        diedCells.clear();
        generate();
    }

    public void tick() {
        generation++;
        diedCells.clear();
        Set<Cell> newAliveCells = new HashSet<>();
        Set<Cell> newBornCells = new HashSet<>();
        HashSet<Cell> allCells = new HashSet<>(bornCells);
        allCells.addAll(aliveCells);
        for (Cell cell : allCells) {
            for (long x = cell.getPositionX() - 1; x <= cell.getPositionX() + 1; x++) {
                for (long y = cell.getPositionY() - 1; y <= cell.getPositionY() + 1; y++) {
                    if (x == cell.getPositionX() && y == cell.getPositionY()) {
                        if (hasCellAfterTick(allCells, x, y)) {
                            newAliveCells.add(cell);
                        } else {
                            diedCells.add(cell);
                        }
                    } else if (isCellEmpty(allCells, x, y) && isCellEmpty(newBornCells, x, y) && hasCellAfterTick(allCells, x, y)) {
                        newBornCells.add(new Cell(x, y));
                    }
                }
            }
        }
        bornCells.clear();
        aliveCells.clear();
        bornCells.addAll(newBornCells);
        aliveCells.addAll(newAliveCells);
    }

    public Set<Cell> getBornCells() {
        return bornCells;
    }

    public Set<Cell> getAliveCells() {
        return aliveCells;
    }

    public Set<Cell> getDiedCells() {
        return diedCells;
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

    private void initialize() {
        initialPattern.delete(0, initialPattern.length());
        generation = 0;
        long yStart = -initialSize / 2;
        for (long y = yStart; y < yStart + initialSize; y++) {
            long xStart = -initialSize / 2;
            for (long x = xStart; x < xStart + initialSize; x++) {
                long random = Math.round(Math.random());
                if (random == 1) {
                    bornCells.add(new Cell(x, y));
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
                    bornCells.add(new Cell(x, y));
                }
            }
        }
    }

    private boolean isCellEmpty(Set<Cell> allCells, long posX, long posY) {
        return allCells.stream()
                .noneMatch(cell -> cell.getPositionX() == posX && cell.getPositionY() == posY);
    }

    private boolean hasCellAfterTick(Set<Cell> allCells, long posX, long posY) {
        boolean isEmpty = isCellEmpty(allCells, posX, posY);
        long adjacentCells = countAdjacentCells(allCells, posX, posY);
        return (isEmpty && adjacentCells == 3) || (!isEmpty && (adjacentCells == 2 || adjacentCells == 3));
    }

    private long countAdjacentCells(Set<Cell> allCells, long posX, long posY) {
        return allCells.stream()
                .filter(cell -> cell.getPositionX() >= posX - 1 && cell.getPositionX() <= posX + 1
                        && cell.getPositionY() >= posY - 1 && cell.getPositionY() <= posY + 1
                        && !(cell.getPositionX() == posX && cell.getPositionY() == posY))
                .count();
    }

    @Override
    public String toString() {
        return initialPattern.toString();
    }
}
