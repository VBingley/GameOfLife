package nl.bingley.gameoflife.timertasks;

import nl.bingley.gameoflife.UniversePanel;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

@Component
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
