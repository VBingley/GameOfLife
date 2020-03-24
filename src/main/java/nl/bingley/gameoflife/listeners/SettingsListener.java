package nl.bingley.gameoflife.listeners;

import nl.bingley.gameoflife.UniversePanel;
import nl.bingley.gameoflife.model.Universe;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class SettingsListener implements KeyListener {

    private final Universe universe;
    private final UniversePanel universePanel;

    public SettingsListener(Universe universe, UniversePanel universePanel) {
        this.universe = universe;
        this.universePanel = universePanel;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                universe.setPaused(!universe.isPaused());
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_BACK_SPACE:
                universe.refresh();
                break;
            case KeyEvent.VK_UP:
                universePanel.increaseRefreshInterval();
                break;
            case KeyEvent.VK_DOWN:
                universePanel.decreaseRefreshInterval();
                break;
            case KeyEvent.VK_RIGHT:
                if (universe.isPaused()) {
                    universe.tick();
                }
                break;
            case KeyEvent.VK_P:
                System.out.println("Universe initial state:");
                System.out.println(universe.toString());
                break;
            case KeyEvent.VK_R:
                universe.restart();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
