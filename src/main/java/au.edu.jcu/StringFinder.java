package au.edu.jcu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luke on 21/08/2015.
 * Copyright 137Industries
 * Finds a specified string in sequential buffer of chars
 */
public class StringFinder {

    private final StringBuilder sb = new StringBuilder();

    private final Map<String, Runnable> stringHandler = new HashMap<>();

    public void addStringHandler(String toFind, Runnable toFire){
        this.stringHandler.put(toFind, toFire);
    }

    public void read(char input){
        sb.append(input);
        for (Map.Entry<String, Runnable> entry : stringHandler.entrySet()) {
            if(sb.toString().endsWith(entry.getKey())){
                entry.getValue().run();
                sb.setLength(0);
                break;
            }
        }
    }

}
