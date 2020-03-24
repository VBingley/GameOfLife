package nl.bingley.gameoflife.listeners;

import nl.bingley.gameoflife.UniversePanel;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@Component
public class MovementListener implements MouseListener, MouseMotionListener {

    private final UniversePanel universePanel;
    private Point origin;

    public MovementListener(UniversePanel universePanel) {
        this.universePanel = universePanel;
        origin = new Point();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        origin = mouseEvent.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Point target = mouseEvent.getPoint();
        universePanel.addTranslateX(target.x - origin.x);
        universePanel.addTranslateY(target.y - origin.y);
        origin = target;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}