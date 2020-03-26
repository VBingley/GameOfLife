package nl.bingley.gameoflife.model;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class UniverseTest {

    @Test
    public void testOscillator() {
        Universe universe = new Universe("..." + System.lineSeparator() + "000" + System.lineSeparator() + "...");
        universe.tick();
        Collection<Cell> cells = universe.getSpace().getAllBornCells();
        assertEquals(3, cells.size());
    }

    @Test
    public void testGlider() {
        Universe universe = new Universe("000" + System.lineSeparator() + "0.." + System.lineSeparator() + ".0.");
        universe.tick();
        assertEquals(5, universe.getSpace().getAllBornCells().size());
        for (int i = 0; i < 500; i++) {
            universe.tick();
        }
        assertEquals(5, universe.getSpace().getAllBornCells().size());
    }
}