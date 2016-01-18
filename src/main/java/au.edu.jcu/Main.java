package au.edu.jcu;


/**
 * Created by Luke on 20/08/2015.
 * Pilfered mostly from the arduino site
 */


public class Main {


    public static void main(String[] args) throws Exception {

        //add the clickbot to respond to "Click"
        ClickBot clickBot = new ClickBot();
        StringFinder sf = new StringFinder();
        sf.addStringHandler("Click", clickBot::click);

        //turn on the serial monitor
        SerialMonitor serialMonitor = new SerialMonitor(args);
        serialMonitor.setStringFinder(sf);

        //add a thread so console app does not just close immediately
        final WaitingThread waitingThread = new WaitingThread();
        waitingThread.start();

        //turn off waiting thread and serial monitor
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serialMonitor.close();
            waitingThread.notify();
        }));

    }
}
