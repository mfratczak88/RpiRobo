package robot;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import config.Config;
import robot.pwmPin.*;
import robot.steeringSystem.SteeringSystem;
import robot.steeringSystem.SteeringSystemImpl;
import robot.steeringSystem.motor.Motor;
import robot.steeringSystem.motor.MotorImpl;

//
public class RoboFactory {
    private static GpioController sGpioController;

    /*
        use wiringPi pin numbering here.
     */
    public Robot getRobot() throws Exception {
        GpioController sGpioController = GpioFactory.getInstance();
        Gpio.wiringPiSetup();

        int leftMotorMinus = Config.getInstance().getLeftMotorMinus();
        int leftMotorPlus = Config.getInstance().getLeftMotorPlus();
        int rightMotorMinus = Config.getInstance().getRightMotorMinus();
        int rightMotorPlus = Config.getInstance().getRightMotorPlus();

        PwmPin leftMotorMinusPin = new PwmPin(leftMotorMinus);
        PwmPin leftMotorPlusPin = new PwmPin(leftMotorPlus);
        PwmPin rightMotorMinusPin = new PwmPin(rightMotorMinus);
        PwmPin rightMotorPlusPin = new PwmPin(rightMotorPlus);


        Motor leftMotor = new MotorImpl(
                leftMotorPlusPin, leftMotorMinusPin
        );

        Motor rightMotor = new MotorImpl(
                rightMotorPlusPin, rightMotorMinusPin
        );

        SteeringSystem steeringSystem = new SteeringSystemImpl(rightMotor, leftMotor);

        return new RobotImpl(steeringSystem);

    }

    public GpioController getGpioController() {
        return sGpioController;
    }

}
