package robot.steeringSystem;

import robot.steeringSystem.motor.Motor;

import java.lang.reflect.Method;

public class SteeringSystemImpl implements SteeringSystem {
    private Motor mRightWheel;
    private Motor mLeftWheel;
    private int mSpeed;
    private static final int DEFAULT_SPEED = 50;
    private static final int DEFAULT_VALUE_FOR_TURNS = 10;

    public SteeringSystemImpl(Motor rightWheel, Motor leftWheel) {
        this.mRightWheel = rightWheel;
        this.mLeftWheel = leftWheel;
    }

    @Override
    public void turnRight(int angle) {
        decreaseSpeed(mRightWheel, angle);
    }

    @Override
    public void turnLeft(int angle) {
        decreaseSpeed(mLeftWheel, angle);
    }

    @Override
    public void turnRight() {
        turnRight(DEFAULT_VALUE_FOR_TURNS);
    }

    @Override
    public void turnLeft() {
        turnLeft(DEFAULT_VALUE_FOR_TURNS);
    }

    @Override
    public void stop() {
        mRightWheel.stop();
        mLeftWheel.stop();
    }

    @Override
    public void forward(int speed) {
        mSpeed = speed;
        moveLinearly(getMethodToCallByName("setForwardDirection"));
    }

    @Override
    public void forward() {
        forward(DEFAULT_SPEED);
    }


    @Override
    public void reverse(int speed) {
        mSpeed = speed;
        moveLinearly(getMethodToCallByName("setBackwardDirection"));
    }

    @Override
    public void reverse() {
        reverse(DEFAULT_SPEED);
    }

    private void moveLinearly(Method methodToCallBefore){
        try {
            prepareBeforeLinearMovement(methodToCallBefore);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        changeSpeed(mSpeed);
        revolve();
    }

    private void prepareBeforeLinearMovement(Method methodToCallBefore) {
        stop();
        try {
            methodToCallBefore.invoke(this);
        } catch (Exception e) {
            // ain't gonna happen
            e.printStackTrace();
        }
    }

    private void decreaseSpeed(Motor motor, int speed) {
        motor.setSpeed(motor.getSpeed() - speed);
    }

    private void setForwardDirection() {
        mRightWheel.setForwardDirection();
        mLeftWheel.setForwardDirection();
    }

    private void setBackwardDirection() {
        mLeftWheel.setBackWardDirection();
        mRightWheel.setBackWardDirection();
    }

    private Method getMethodToCallByName(String name) {
        Method[] methods = SteeringSystemImpl.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().contains(name)) {
                return method;
            }
        }
        return null;
    }

    private void changeSpeed(int speed){
        mRightWheel.setSpeed(speed);
        mLeftWheel.setSpeed(speed);
    }

    private void revolve(){
        mLeftWheel.revolve();
        mRightWheel.revolve();
    }
}
