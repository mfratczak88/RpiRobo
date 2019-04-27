# RpiRobo

A REST API to control two wheeled DC motors robot from Raspberry Pi.  


Due to the fact that cross-compliation for rasberryPi is painful in C++ ( outdated version of cross compilation tools on official RPi github https://github.com/raspberrypi/tools/issues/81 ),   
Java was used instead, nonetheless, no performance issues was noticed.

WiringPi pin numbering is used.

All 4 pins in use are controlled as PWM.

### Dependencies

* Vertx (https://github.com/eclipse-vertx/vert.x)
* wiringPi (http://wiringpi.com)
* Mockito (https://site.mockito.org)
* pi4j (https://pi4j.com/1.2/index.html)

