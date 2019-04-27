package robot;

import robot.steeringSystem.SteeringSystem;

class RobotImpl implements Robot, SteeringSystem {
    private SteeringSystem mSteeringSystem;

    public RobotImpl(SteeringSystem steeringSystem) {
        mSteeringSystem = steeringSystem;
    }


    @Override
    public void turnRight(int angle) {
        mSteeringSystem.turnRight(angle);
    }

    @Override
    public void turnLeft(int angle) {
        mSteeringSystem.turnLeft(angle);
    }

    @Override
    public void turnRight() {
        mSteeringSystem.turnRight();
    }

    @Override
    public void turnLeft() {
        mSteeringSystem.turnLeft();
    }

    @Override
    public void stop() {
        mSteeringSystem.stop();
    }

    @Override
    public void forward(int speed) {
        mSteeringSystem.forward(speed);
    }

    @Override
    public void forward() {
        mSteeringSystem.forward();
    }

    @Override
    public void reverse(int speed) {
        mSteeringSystem.reverse(speed);
    }

    @Override
    public void reverse() {
        mSteeringSystem.reverse();
    }
}
