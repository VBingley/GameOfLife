package nl.bingley.gameoflife.timertasks;

import nl.bingley.gameoflife.UniversePanel;
import nl.bingley.gameoflife.model.Universe;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

@Component
public class TickTimerTask extends TimerTask {

    private final Universe universe;
    private final UniversePanel universePanel;
    private final int generationRecord = 500;
    private long lastTick;

    public TickTimerTask(Universe universe, UniversePanel universePanel) {
        this.universe = universe;
        this.universePanel = universePanel;
        lastTick = System.currentTimeMillis();
    }

    @Override
    public void run() {
        if (!universe.isPaused() && lastTick < System.currentTimeMillis() - universePanel.getRefreshInterval()) {
            int lastBorn = universe.getBorn();
            int lastAlive = universe.getAlive();
            int lastDied = universe.getDied();
            universe.tick();
//            if (lastBorn == lastDied && universe.getBorn() == universe.getDied()
//                    && lastBorn == universe.getBorn() && lastAlive == universe.getAlive()
//                    && lastDied == universe.getDied()) {
//                if (universe.getGeneration() >= generationRecord) {
//                    System.out.println("Universe lasted to gen " + universe.getGeneration() + ':');
//                    System.out.println(universe.toString());
//                    generationRecord = universe.getGeneration();
//                    universe.setPaused(true);
//                } else {
//                    universe.refresh();
//                }
//            } else if (universe.getGeneration() > 3500) {
//                universe.setPaused(true);
//            }
            lastTick = System.currentTimeMillis();
        } else if (universe.isPaused()) {
            lastTick = System.currentTimeMillis();
        }
    }
}
