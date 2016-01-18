package au.edu.jcu;

/**
 * Created by Luke on 20/08/2015.
 * Copyright 137Industries
 */
public enum ArduinoPort {

    WINDOWS("COM3"),
    ANDREW("COM7"),
    PI("/dev/ttyACM0"),
    MAC("/dev/tty.usbserial-A9007UX1"),
    LINUX("/dev/ttyUSB0");

    private String name;
    ArduinoPort(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
