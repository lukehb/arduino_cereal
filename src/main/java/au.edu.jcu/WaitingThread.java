package au.edu.jcu;

/**
 * Created by Luke on 20/08/2015.
 * Copyright 137Industries
 */
public class WaitingThread extends Thread{

    @Override
    public void run() {
        synchronized (this){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
