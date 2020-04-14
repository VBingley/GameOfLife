package nl.bingley.gameoflife.timertasks;

import nl.bingley.gameoflife.model.Universe;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

@Component
public class TickTimerTask extends TimerTask {

    private static final int MAX_GENERATION = 20000;

    private final Universe universe;
    private int generationRecord = 8148;
    private long lastTick;

    private int lastBorn;
    private int lastSurvived;
    private int lastDied;

    public TickTimerTask(Universe universe) {
        this.universe = universe;
        lastTick = System.currentTimeMillis();
    }

    @Override
    public void run() {
        if (!universe.isPaused() && lastTick < System.currentTimeMillis() - universe.getTickSpeed()) {
            int oldBorn = lastBorn;
            int oldSurvived = lastSurvived;
            int oldDied = lastDied;
            lastBorn = universe.getSpace().getAllBornCells().size();
            lastSurvived = universe.getSpace().getAllSurvivingCells().size();
            lastDied = universe.getSpace().getAllDiedCells().size();
            universe.tick();
            if (isUniverseStable(oldBorn, oldSurvived, oldDied)) {
                if (universe.getGeneration() >= generationRecord) {
                    System.out.println("Universe lasted to gen " + universe.getGeneration() + ':');
                    System.out.println(universe.toString());
                    generationRecord = universe.getGeneration();
                    universe.setPaused(true);
                } else {
                    universe.refresh();
                }
            } else {
                if (universe.getGeneration() > MAX_GENERATION) {
                    universe.setPaused(true);
                }
            }
            lastTick = System.currentTimeMillis();
        } else if (universe.isPaused()) {
            lastTick = System.currentTimeMillis();
        }
    }

    private boolean isUniverseStable(int oldBorn, int oldSurvived, int oldDied) {
        int currentBorn = universe.getSpace().getAllBornCells().size();
        int currentSurvived = universe.getSpace().getAllSurvivingCells().size();
        int currentDied = universe.getSpace().getAllDiedCells().size();
        return currentBorn == currentDied && lastBorn == lastDied && oldBorn == oldDied
            && currentBorn == lastBorn && currentBorn == oldBorn
            && currentSurvived == lastSurvived && currentSurvived == oldSurvived;
    }
}
