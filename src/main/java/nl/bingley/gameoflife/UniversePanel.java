package nl.bingley.gameoflife;

import javax.swing.*;
import java.awt.*;

public class UniversePanel extends JPanel {
    private static final long serialVersionUID = 119486406615542676L;


    private final int paintMultiplier;
    private Universe universe;
    private final int initialSize;
    private long generation = 0;
    private long genRecord = 500;
    private int refreshInterval;

    private long lastRepaint;

    // um
    public boolean pause;
    public boolean manualTick;

    public UniversePanel(Universe initialState, int paintMultiplier, int refreshInterval, int initialSize) {
        super();
        universe = initialState;
        this.initialSize = initialSize;
        this.paintMultiplier = paintMultiplier;
        this.refreshInterval = refreshInterval;
        lastRepaint = System.currentTimeMillis();
    }

    public void log(String message) {
        System.out.println(message);
        System.out.println(universe.toString());
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
        generation = 0;
    }

    public String getUniverseString() {
        return universe.toString();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, universe.getBoundaryX() * paintMultiplier + 1, universe.getBoundaryY() * paintMultiplier + 1);
        for (int x = 0; x < universe.getBoundaryX(); x++) {
            for (int y = 0; y < universe.getBoundaryY(); y++) {
                if (universe.getState(x, y)) {
                    if (universe.getPreviousState(x, y)) {
                        graphics.setColor(Color.BLUE);
                    } else {
                        graphics.setColor(Color.GREEN);
                    }
                    graphics.fillRect(x * paintMultiplier, y * paintMultiplier, paintMultiplier, paintMultiplier);
                    graphics.setColor(Color.BLUE);
                    graphics.drawRect(x * paintMultiplier, y * paintMultiplier, paintMultiplier, paintMultiplier);
                } else if (!universe.getState(x, y) && universe.getPreviousState(x, y)) {
                    graphics.setColor(Color.PINK);
                    graphics.drawRect(x * paintMultiplier, y * paintMultiplier, paintMultiplier, paintMultiplier);
                }
            }
        }
        graphics.setColor(Color.RED);
        graphics.drawString("Gen:  " + generation, 10, 20);
        graphics.drawString("Ref:  " + refreshInterval, 10, 40);
        graphics.drawString("Born: " + universe.currentBorn, 10, 80);
        graphics.drawString("Live: " + universe.currentAlive, 10, 100);
        graphics.drawString("Died: " + universe.currentDied, 10, 120);
        graphics.dispose();
    }

    @Override
    public void repaint() {
        if (universe != null && (!pause || manualTick) && lastRepaint < System.currentTimeMillis() - refreshInterval) {
            manualTick = false;
            int lastBorn = universe.currentBorn;
            int lastAlive = universe.currentAlive;
            int lastDied = universe.currentDied;
            universe.tick();
            generation++;
            if (lastBorn == lastDied && universe.currentBorn == universe.currentDied && lastBorn == universe.currentBorn && lastAlive == universe.currentAlive && lastDied == universe.currentDied) {
                if (generation >= genRecord) {
                    log("Universe lasted to gen " + generation + ':');
                    genRecord = generation;
                    pause = true;
                } else {
                    universe = new Universe(universe.getBoundaryX(), universe.getBoundaryY(), initialSize);
                    generation = 0;
                }
            } else if (generation > 3500) {
                pause = true;
            }
            lastRepaint = System.currentTimeMillis();
        }
        super.repaint();
    }
}