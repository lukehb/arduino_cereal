package au.edu.jcu;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Luke on 21/08/2015.
 * Copyright 137Industries
 * Simulate a click
 */
public class ClickBot {

    private final Robot bot;
    private final Executor exec;

    public ClickBot() throws AWTException {
        exec = Executors.newFixedThreadPool(1);
        bot = new Robot();
    }

    public void click(){
        exec.execute(() -> {
            bot.mousePress(InputEvent.BUTTON1_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_MASK);
        });
    }

}
