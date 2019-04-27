package robot.steeringSystem;

public interface SteeringSystem {
    void turnRight(int angle);

    void turnLeft(int angle);

    void turnRight();

    void turnLeft();

    void stop();
    void forward(int speed);
    void forward();
    void reverse(int speed);
    void reverse();

}
