package robot.pwmPin;

import com.pi4j.wiringpi.SoftPwm;

// just a wrapper for Pwm on every pin;
public class PwmPin {
    private int pinNumber;

    public PwmPin(int pinNumber) {
        this.pinNumber = pinNumber;
        int result = SoftPwm.softPwmCreate(pinNumber, 0, 100);
    }

    public void write(int value) throws Exception {
        if (value > 100 || value < 0) {
            throw new Exception("Value must be between 0 and 100");
        }

        SoftPwm.softPwmWrite(pinNumber, value);
    }

    public void stop(){
        SoftPwm.softPwmStop(pinNumber);
    }


}
