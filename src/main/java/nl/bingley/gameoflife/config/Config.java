package nl.bingley.gameoflife.config;

import nl.bingley.gameoflife.timertasks.RenderTimerTask;
import nl.bingley.gameoflife.timertasks.TickTimerTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;

@ComponentScan("nl.bingley.gameoflife")
@Configuration
public class Config {

    @Bean
    public Timer scheduleTimers(RenderTimerTask renderTimerTask, TickTimerTask tickTimerTask) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(renderTimerTask, 0, 16);
        timer.scheduleAtFixedRate(tickTimerTask, 0, 4);
        return timer;
    }
}
