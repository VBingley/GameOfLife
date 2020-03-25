package nl.bingley.gameoflife;

import nl.bingley.gameoflife.model.Cell;
import nl.bingley.gameoflife.model.Universe;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UniversePanel extends JPanel {
    private static final long serialVersionUID = 119486406615542676L;

    private int width = 0;
    private int height = 0;
    private int translateX = 0;
    private int translateY = 0;
    private final int scaleMultiplier = 5;
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
        paintCells(graphics, universe.getAliveCells(), Color.GREEN, Color.BLUE);
//        paintCells(graphics, (universe.getBornCells()), Color.GREEN, Color.BLUE);
//        paintCells(graphics, (universe.getAliveCells()), Color.BLUE, Color.BLUE);
//        paintCells(graphics, (universe.getDiedCells()), Color.GRAY, Color.PINK);

        graphics.setColor(Color.RED);
        graphics.drawString("Gen:  " + universe.getGeneration(), 10, 20);
        graphics.drawString("Ref:  " + refreshInterval, 10, 40);
//        graphics.drawString("Born: " + universe.getBornCells().size(), 10, 80);
//        graphics.drawString("Live: " + universe.getAliveCells().size(), 10, 100);
//        graphics.drawString("Died: " + universe.getDiedCells().size(), 10, 120);
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

    private Set<Cell> removeInvisibleCells(Set<Cell> cells) {
        int cellsX = (width + translateX / 2) / scaleMultiplier / 2;
        int cellsY = (height + translateY / 2) / scaleMultiplier / 2;
        return cells.stream()
                .filter(cell -> cell.getPositionX() >= cellsX && cell.getPositionX() <= cellsX + translateX / scaleMultiplier
                        && cell.getPositionY() >= cellsY && cell.getPositionY() <= cellsY + translateY / scaleMultiplier)
                .collect(Collectors.toSet());
    }

    private void paintCell(Graphics graphics, int posX, int posY, Color fill, Color border) {
        graphics.setColor(fill);
        graphics.fillRect(posX, posY, scaleMultiplier, scaleMultiplier);
        graphics.setColor(border);
        graphics.drawRect(posX, posY, scaleMultiplier, scaleMultiplier);
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