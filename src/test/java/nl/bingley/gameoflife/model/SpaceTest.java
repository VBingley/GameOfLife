package nl.bingley.gameoflife.model;

import org.junit.Test;

public class SpaceTest {

    @Test
    public void testAddCell() {
        Space space = new Space();
        space.addBornCell(0, 600);
        space.addBornCell(600, 600);
        space.addBornCell(0, -600);
        space.addBornCell(-600, 0);
    }
}
