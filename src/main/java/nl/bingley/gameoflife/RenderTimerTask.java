package nl.bingley.gameoflife;

import java.util.TimerTask;

public class RenderTimerTask extends TimerTask {

    private final UniversePanel universePanel;

    public RenderTimerTask(UniversePanel universePanel) {
        this.universePanel = universePanel;
    }

    @Override
    public void run() {
        universePanel.repaint();
    }
}
