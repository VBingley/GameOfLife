package nl.bingley.gameoflife;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InteractionListener implements KeyListener {

    private final int universeX;
    private final int universeY;
    private final int universeInitialSize;
    private final UniversePanel universePanel;
    private int refreshInterval;

    public InteractionListener(UniversePanel universePanel, int universeX, int universeY, int universeInitialSize, int refreshInterval) {
        this.universeX = universeX;
        this.universeY = universeY;
        this.universeInitialSize = universeInitialSize;
        this.universePanel = universePanel;
        universePanel.addKeyListener(this);
        this.refreshInterval = refreshInterval;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                universePanel.pause = !universePanel.pause;
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_BACK_SPACE:
                refresh();
                break;
            case KeyEvent.VK_UP:
                increaseRefreshInterval();
                break;
            case KeyEvent.VK_DOWN:
                decreaseRefreshInterval();
                break;
            case KeyEvent.VK_RIGHT:
                universePanel.manualTick = true;
                break;
            case KeyEvent.VK_P:
                universePanel.log("Universe initial state:");
                break;
            case KeyEvent.VK_R:
                restart();
                break;
        }
    }

    private void restart() {
        Universe universe = new Universe(universeX, universeY, universePanel.getUniverseString());
        universePanel.setUniverse(universe);
        universePanel.pause = false;
    }

    private void refresh() {
        Universe universe = new Universe(universeX, universeY, universeInitialSize);
        universePanel.setUniverse(universe);
        universePanel.pause = false;
    }

    private void increaseRefreshInterval() {
        if (refreshInterval < 1024) {
            refreshInterval = refreshInterval * 2;
        }
        universePanel.setRefreshInterval(refreshInterval);
    }

    private void decreaseRefreshInterval() {
        if (refreshInterval > 8) {
            refreshInterval = refreshInterval / 2;
        }
        universePanel.setRefreshInterval(refreshInterval);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
