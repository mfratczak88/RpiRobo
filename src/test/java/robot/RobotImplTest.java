package robot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import robot.steeringSystem.SteeringSystem;

import java.util.Random;


import static org.mockito.Mockito.*;

public class RobotImplTest {
    private RobotImpl classUnderTest;
    private int randomInt;

    @Mock
    SteeringSystem steeringSystem;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        classUnderTest = new RobotImpl(steeringSystem);
        randomInt = getRandomInt();
    }

    @Test
    public void turnRight() {
        classUnderTest.turnRight();
        verify(steeringSystem, times(1)).turnRight();
    }

    @Test
    public void turnLeft() {
        classUnderTest.turnLeft();
        verify(steeringSystem, times(1)).turnLeft();
    }

    @Test
    public void turnRight1() {
        classUnderTest.turnRight(randomInt);
        verify(steeringSystem, times(1)).turnRight(randomInt);
    }

    @Test
    public void turnLeft1() {
        classUnderTest.turnLeft(randomInt);
        verify(steeringSystem, times(1)).turnLeft(randomInt);
    }

    @Test
    public void stop() {
        classUnderTest.stop();
        verify(steeringSystem, times(1)).stop();
    }

    @Test
    public void forward() {
        classUnderTest.forward();
        verify(steeringSystem, times(1)).forward();
    }

    @Test
    public void forward1() {
        classUnderTest.forward(randomInt);
        verify(steeringSystem, times(1)).forward(randomInt);
    }

    @Test
    public void reverse() {
        classUnderTest.reverse();
        verify(steeringSystem, times(1)).reverse();
    }

    @Test
    public void reverse1() {
        classUnderTest.reverse(randomInt);
        verify(steeringSystem, times(1)).reverse(randomInt);
    }

    private int getRandomInt() {
        return new Random().nextInt();
    }
}