# Listening for serial port message from Arduino (using Java).

An example of listening to serial ports in java (using an arduino board).

In this case we scan some serial ports: "COM3", "/dev/ttyACM0",  "/dev/tty.usbserial-A9007UX1", "/dev/ttyUSB0" etc.
and if we can connect we listen for a string message. The message we listen for in this example is "Click". When
we recieve the "Click" string we make java simulate a mouse click on the OS.

I used this serial monitor application to listen for the "Click" string triggered by moving an accellerometer, 
which allowed me to wave the accellerometer up and down to play a web-based flappy bird game.
