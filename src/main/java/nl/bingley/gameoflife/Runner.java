package nl.bingley.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;

public class Runner {

    //Guns, these produce gliders
    private static final String PATTERN_GOSPER_GLIDER_GUN = "000000000000000000000000100000000000" +
            "000000000000000000000010100000000000" +
            "000000000000110000001100000000000011" +
            "000000000001000100001100000000000011" +
            "110000000010000010001100000000000000" +
            "110000000010001011000010100000000000" +
            "000000000010000010000000100000000000" +
            "000000000001000100000000000000000000" +
            "000000000000110000000000000000000000"; //x=36


    private static final int INITIAL_SIZE = 5;
    private static final int UNIVERSE_X = 400;
    private static final int UNIVERSE_Y = 250;
    private static final int SCALE_MULTIPLIER = 5;
    private static final int REFRESH_INTERVAL = 1024;

    public static void main(String[] args) throws InterruptedException {

        Universe universe = new Universe(UNIVERSE_X, UNIVERSE_Y, INITIAL_SIZE);

        UniversePanel universePanel = initializeWindow(universe);
        InteractionListener listener = new InteractionListener(universePanel, UNIVERSE_X, UNIVERSE_Y, INITIAL_SIZE, REFRESH_INTERVAL);

        Timer renderTimer = new Timer();
        renderTimer.scheduleAtFixedRate(new RenderTimerTask(universePanel), 0, 16);
    }

    private static UniversePanel initializeWindow(Universe initialState) {
        JFrame frame = new JFrame();
        UniversePanel universePanel = new UniversePanel(initialState, SCALE_MULTIPLIER, REFRESH_INTERVAL, INITIAL_SIZE);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
        universePanel.setFont(font);
        universePanel.setFocusable(true);
        frame.getContentPane().add(universePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(UNIVERSE_X * SCALE_MULTIPLIER + SCALE_MULTIPLIER * 2, UNIVERSE_Y * SCALE_MULTIPLIER + SCALE_MULTIPLIER * 2);
        frame.setVisible(true);
        return universePanel;
    }
}
