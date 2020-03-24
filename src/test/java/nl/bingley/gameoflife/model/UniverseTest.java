package nl.bingley.gameoflife.model;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UniverseTest {

    @Test
    public void test() {
        Universe universe = new Universe("..." + System.lineSeparator() + "000" + System.lineSeparator() + "...");
        universe.tick();
        Set<Cell> bornCells = universe.getBornCells();
        assertEquals(2, bornCells.size());
    }
}