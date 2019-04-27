package robot.steeringSystem.motor;

public interface Motor {
    boolean isForward();

    void revolve();

    void setSpeed(int speed);

    int getSpeed();

    void stop();

    void setForwardDirection();

    void setBackWardDirection();

}
