package nl.bingley.gameoflife;

import nl.bingley.gameoflife.model.Cell;
import nl.bingley.gameoflife.model.Space;
import nl.bingley.gameoflife.model.Universe;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

@Component
public class UniversePanel extends JPanel {
    private static final long serialVersionUID = 119486406615542676L;

    private int width = 0;
    private int height = 0;
    private int translateX = 0;
    private int translateY = 0;
    private int scaleMultiplier = 5;
    private int refreshInterval = 1024;
    private boolean painting = false;

    private final Universe universe;

    public UniversePanel(Universe initialState) {
        super();
        universe = initialState;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        painting = true;
        graphics.setColor(Color.GRAY);
        Rectangle bounds = graphics.getClipBounds();
        width = bounds.width;
        height = bounds.height;
        graphics.fillRect(0, 0, width, height);
        Space space = universe.getSpace();
        paintCells(graphics, space.getAllBornCells(), Color.GREEN, Color.BLUE);
        paintCells(graphics, (space.getAllSurvivingCells()), Color.BLUE, Color.BLUE);
        paintCells(graphics, (space.getAllDiedCells()), Color.GRAY, Color.PINK);

        graphics.setColor(Color.RED);
        graphics.drawString("Gen:  " + universe.getGeneration(), 10, 20);
        graphics.drawString("fps:  " + 1024 / refreshInterval, 10, 40);
        graphics.drawString("Born: " + space.getAllBornCells().size(), 10, 80);
        graphics.drawString("Live: " + space.getAllSurvivingCells().size(), 10, 100);
        graphics.drawString("Died: " + space.getAllDiedCells().size(), 10, 120);
        graphics.dispose();
        painting = false;
    }

    private void paintCells(Graphics graphics, Collection<Cell> cells, Color fill, Color border) {
        for (Cell cell : cells) {
            int posX = cell.getPositionX() * scaleMultiplier + translateX + width / 2;
            int posY = cell.getPositionY() * scaleMultiplier + translateY + height / 2;
            paintCell(graphics, posX, posY, fill, border);
        }
    }

    private void paintCell(Graphics graphics, int posX, int posY, Color fill, Color border) {
        graphics.setColor(fill);
        graphics.fillRect(posX, posY, scaleMultiplier - 1, scaleMultiplier - 1);
        graphics.setColor(border);
        graphics.drawRect(posX, posY, scaleMultiplier - 1, scaleMultiplier - 1);
    }

    public void increaseRefreshInterval() {
        if (refreshInterval < 1024) {
            refreshInterval = refreshInterval * 2;
        }
    }

    public void decreaseRefreshInterval() {
        if (refreshInterval > 4) {
            refreshInterval = refreshInterval / 2;
        }
    }

    public void increaseScaleMultiplier() {
        translateX = (int) (translateX * ((scaleMultiplier + 1) / (double) scaleMultiplier));
        translateY = (int) (translateY * ((scaleMultiplier + 1) / (double) scaleMultiplier));
        scaleMultiplier++;
    }

    public void decreaseScaleMultiplier() {
        if (scaleMultiplier > 1) {
            translateX = (int) (translateX * ((scaleMultiplier - 1) / (double) scaleMultiplier));
            translateY = (int) (translateY * ((scaleMultiplier - 1) / (double) scaleMultiplier));
            scaleMultiplier--;
        }
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public boolean isPainting() {
        return painting;
    }

    public void addTranslateX(int translateX) {
        this.translateX += translateX;
    }

    public void addTranslateY(int translateY) {
        this.translateY += translateY;
    }
}