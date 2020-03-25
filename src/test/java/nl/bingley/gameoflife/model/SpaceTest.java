package nl.bingley.gameoflife.model;

import org.junit.Test;

public class SpaceTest {

    @Test
    public void testAddCell() {
        Space space = new Space();
        space.addCell(0, 600);
        space.addCell(600, 600);
        space.addCell(0, -600);
        space.addCell(-600, 0);
    }
}
