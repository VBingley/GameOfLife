package nl.bingley.gameoflife;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class UniversePanel extends JPanel {
    private static final long serialVersionUID = 119486406615542676L;


    private final int scaleMultiplier = 5;
    private final Universe universe;
    private int refreshInterval = 1024;

    public UniversePanel(Universe initialState) {
        super();
        universe = initialState;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, universe.getUniverseX() * scaleMultiplier + 1, universe.getUniverseY() * scaleMultiplier + 1);
        for (int x = 0; x < universe.getUniverseX(); x++) {
            for (int y = 0; y < universe.getUniverseY(); y++) {
                if (universe.getState(x, y)) {
                    if (universe.getPreviousState(x, y)) {
                        graphics.setColor(Color.BLUE);
                    } else {
                        graphics.setColor(Color.GREEN);
                    }
                    graphics.fillRect(x * scaleMultiplier, y * scaleMultiplier, scaleMultiplier, scaleMultiplier);
                    graphics.setColor(Color.BLUE);
                    graphics.drawRect(x * scaleMultiplier, y * scaleMultiplier, scaleMultiplier, scaleMultiplier);
                } else if (!universe.getState(x, y) && universe.getPreviousState(x, y)) {
                    graphics.setColor(Color.PINK);
                    graphics.drawRect(x * scaleMultiplier, y * scaleMultiplier, scaleMultiplier, scaleMultiplier);
                }
            }
        }
        graphics.setColor(Color.RED);
        graphics.drawString("Gen:  " + universe.getGeneration(), 10, 20);
        graphics.drawString("Ref:  " + refreshInterval, 10, 40);
        graphics.drawString("Born: " + universe.getCurrentBorn(), 10, 80);
        graphics.drawString("Live: " + universe.getCurrentAlive(), 10, 100);
        graphics.drawString("Died: " + universe.getCurrentDied(), 10, 120);
        graphics.dispose();
    }

    public void increaseRefreshInterval() {
        if (refreshInterval < 1024) {
            refreshInterval = refreshInterval * 2;
        }
    }

    public void decreaseRefreshInterval() {
        if (refreshInterval > 8) {
            refreshInterval = refreshInterval / 2;
        }
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public int getScaleMultiplier() {
        return scaleMultiplier;
    }
}