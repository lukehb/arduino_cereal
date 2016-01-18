package au.edu.jcu;

import gnu.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.logging.Logger;

/**
 * Created by Luke on 20/08/2015.
 * Copyright 137Industries
 */
public class SerialMonitor implements SerialPortEventListener {

    private final SerialPort serialPort;

    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    private BufferedReader input;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;

    private ArrayList<String> comPortNames = new ArrayList<>();

    public SerialMonitor(String... portsNamesToTry) throws PortInUseException, UnsupportedCommOperationException, IOException, TooManyListenersException {
        for (ArduinoPort arduinoPort : ArduinoPort.values()) {
            comPortNames.add(arduinoPort.toString());
        }
        Collections.addAll(comPortNames, portsNamesToTry);

        CommPortIdentifier portId = getCOMPortID();
        // open serial port, and use class name for the appName.
        serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

        // set port parameters
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        // open the streams
        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

        //setup stuff
        serialPort.disableReceiveTimeout();
        serialPort.enableReceiveThreshold(1);

        // add event listeners
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    }

    public SerialMonitor() throws PortInUseException, UnsupportedCommOperationException, IOException, TooManyListenersException {
        this(new String[]{});
    }

    private CommPortIdentifier getCOMPortID(){
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        if(!portEnum.hasMoreElements()){
            Logger.getLogger(this.getClass().getSimpleName()).severe("No COM port found.");
        }
        //Get the first matching COM port used by arduino
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portNameToTry : comPortNames) {
                if (currPortId.getName().equals(portNameToTry)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        return portId;
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    protected synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private StringFinder stringFinder;
    public void setStringFinder(StringFinder stringFinder) {
        this.stringFinder = stringFinder;
    }

    private static final StringBuilder sb = new StringBuilder();
    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                sb.setLength(0);
                while(input.ready()){
                    int cur = input.read();
                    if(cur == -1){
                        break;
                    }
                    char character = (char) cur;
                    sb.append(character);
                    if(stringFinder != null){
                        stringFinder.read(character);
                    }
                }
                String line = sb.toString();
                System.out.print(line);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

}
