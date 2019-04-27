package robot.steeringSystem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import robot.steeringSystem.motor.Motor;

import java.lang.reflect.Field;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SteeringSystemImplTest {
    private SteeringSystem classUnderTest;

    @Mock
    Motor leftWheel;

    @Mock
    Motor rightWheel;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        classUnderTest = new SteeringSystemImpl(rightWheel, leftWheel);
    }

    @Test
    public void turnRight() {
        int initialSpeed = getRandomNumber();
        int valueToDecrease = new Random().nextInt(4) + 1;

        classUnderTest.forward(initialSpeed);

        when(rightWheel.getSpeed()).thenReturn(initialSpeed);

        classUnderTest.turnRight(valueToDecrease);

        InOrder inOrder = Mockito.inOrder(rightWheel);
        inOrder.verify(rightWheel).setSpeed(initialSpeed);
        inOrder.verify(rightWheel).setSpeed(initialSpeed - valueToDecrease);
    }

    @Test
    public void turnLeft() {
        int initialSpeed = getRandomNumber();
        int valueToDecrease = new Random().nextInt(4) + 1;

        classUnderTest.forward(initialSpeed);

        when(leftWheel.getSpeed()).thenReturn(initialSpeed);

        classUnderTest.turnLeft(valueToDecrease);

        InOrder inOrder = Mockito.inOrder(leftWheel);
        inOrder.verify(leftWheel).setSpeed(initialSpeed);
        inOrder.verify(leftWheel).setSpeed(initialSpeed - valueToDecrease);

    }

    @Test
    public void stop() {
        int initialSpeed = getRandomSpeed();
        classUnderTest.forward(initialSpeed);
        classUnderTest.stop();

        InOrder order = Mockito.inOrder(rightWheel);
        order.verify(rightWheel).setSpeed(initialSpeed);
        order.verify(rightWheel).revolve();
        order.verify(rightWheel).stop();

        order = Mockito.inOrder(leftWheel);
        order.verify(leftWheel).setSpeed(initialSpeed);
        order.verify(leftWheel).revolve();
        order.verify(leftWheel).stop();

    }

    @Test
    public void forward() {
        int initialSpeed = getRandomSpeed();
        classUnderTest.forward(initialSpeed);
        InOrder leftWheelOrder = inOrder(leftWheel);
        InOrder rightWheelOrder = inOrder(rightWheel);

        leftWheelOrder.verify(leftWheel).setForwardDirection();
        leftWheelOrder.verify(leftWheel).setSpeed(initialSpeed);
        leftWheelOrder.verify(leftWheel).revolve();

        rightWheelOrder.verify(rightWheel).setForwardDirection();
        rightWheelOrder.verify(rightWheel).setSpeed(initialSpeed);
        rightWheelOrder.verify(rightWheel).revolve();
    }

    @Test
    public void reverse() {
        int initialSpeed = getRandomSpeed();
        classUnderTest.reverse(initialSpeed);
        InOrder leftWheelOrder = inOrder(leftWheel);
        InOrder rightWheelOrder = inOrder(rightWheel);

        leftWheelOrder.verify(leftWheel).setBackWardDirection();
        leftWheelOrder.verify(leftWheel).setSpeed(initialSpeed);
        leftWheelOrder.verify(leftWheel).revolve();

        rightWheelOrder.verify(rightWheel).setBackWardDirection();
        rightWheelOrder.verify(rightWheel).setSpeed(initialSpeed);
        rightWheelOrder.verify(rightWheel).revolve();
    }

    /*
        Tests scenario when:
        forward -> turnRight -> turnLeft
     */
    @Test
    public void testDifferentTurns() {
        int leftWheelFirstTurn = getRandomTurnValueWithin10();
        int rightWheelFirstTurn = getRandomTurnValueWithin10();
        int leftWheelSecondTurn = getRandomTurnValueWithin10();
        int rightWheelSecondTurn = getRandomTurnValueWithin10();
        int initialSpeed = getRandomSpeed();

        classUnderTest.forward(initialSpeed);
        when(leftWheel.getSpeed())
                .thenReturn(initialSpeed)
                .thenReturn(initialSpeed - leftWheelFirstTurn)
                .thenReturn(initialSpeed - leftWheelFirstTurn - leftWheelSecondTurn);
        when(rightWheel.getSpeed())
                .thenReturn(initialSpeed)
                .thenReturn(initialSpeed - rightWheelFirstTurn)
                .thenReturn(initialSpeed - rightWheelFirstTurn - rightWheelSecondTurn);

        classUnderTest.turnLeft(leftWheelFirstTurn);
        classUnderTest.turnRight(rightWheelFirstTurn);
        classUnderTest.turnLeft(leftWheelSecondTurn);
        classUnderTest.turnRight(rightWheelSecondTurn);

        verify(leftWheel, times(1)).setForwardDirection();
        verify(rightWheel, times(1)).setForwardDirection();
        verify(leftWheel, times(1)).setSpeed(initialSpeed);
        verify(leftWheel, times(1)).setSpeed(initialSpeed - leftWheelFirstTurn);
        verify(rightWheel, times(1)).setSpeed(initialSpeed - rightWheelFirstTurn);
        verify(leftWheel, times(1)).setSpeed(initialSpeed - leftWheelFirstTurn - leftWheelSecondTurn);
        verify(rightWheel, times(1)).setSpeed(initialSpeed - rightWheelFirstTurn - rightWheelSecondTurn);
    }

    @Test
    public void testReversingStraightWhenLastCallWasTurn() {
        int initSpeed = getRandomSpeed();
        int reverseSpeed = getRandomSpeed();
        int turnSpeed = getRandomTurnValueWithin10();
        when(rightWheel.getSpeed()).thenReturn(initSpeed);
        classUnderTest.forward(initSpeed);
        classUnderTest.turnRight(turnSpeed);
        classUnderTest.reverse(reverseSpeed);

        verify(leftWheel, times(1)).setSpeed(initSpeed);
        verify(leftWheel, times(1)).setSpeed(reverseSpeed);
        verify(leftWheel, times(0)).setSpeed(initSpeed - turnSpeed);

        verify(rightWheel, times(1)).setSpeed(initSpeed);
        verify(rightWheel, times(1)).setSpeed(initSpeed - turnSpeed);
        verify(rightWheel, times(1)).setSpeed(reverseSpeed);
    }

    @Test
    public void testRidingStraightWhenLastCallWasTurn() {
        int initSpeed = getRandomSpeed();
        int turnSpeed = getRandomTurnValueWithin10();
        when(rightWheel.getSpeed()).thenReturn(initSpeed);

        classUnderTest.forward(initSpeed);
        classUnderTest.turnRight(turnSpeed);
        classUnderTest.forward(initSpeed);

        verify(leftWheel, times(2)).setSpeed(initSpeed);
        verify(leftWheel, times(0)).setSpeed(turnSpeed);

        verify(rightWheel, times(2)).setSpeed(initSpeed);
        verify(rightWheel, times(1)).setSpeed(initSpeed - turnSpeed);

    }

    @Test
    public void testCorrectCallsWhenDirectionChanges() {
        int initialSpeed = getRandomSpeed();
        int secondSpeed = getRandomSpeed();

        classUnderTest.forward(initialSpeed);
        classUnderTest.reverse(initialSpeed);

        // left wheel
        InOrder inOrderLeftWheel = inOrder(leftWheel);

        inOrderLeftWheel.verify(leftWheel).setForwardDirection();
        inOrderLeftWheel.verify(leftWheel).setSpeed(initialSpeed);
        inOrderLeftWheel.verify(leftWheel).revolve();

        inOrderLeftWheel.verify(leftWheel).stop();

        inOrderLeftWheel.verify(leftWheel).setBackWardDirection();
        inOrderLeftWheel.verify(leftWheel).setSpeed(initialSpeed);
        inOrderLeftWheel.verify(leftWheel).revolve();

        // right wheel
        InOrder inOrderRightWheel = inOrder(rightWheel);

        inOrderRightWheel.verify(rightWheel).setForwardDirection();
        inOrderRightWheel.verify(rightWheel).setSpeed(initialSpeed);
        inOrderRightWheel.verify(rightWheel).revolve();

        inOrderRightWheel.verify(rightWheel).stop();

        inOrderRightWheel.verify(rightWheel).setBackWardDirection();
        inOrderRightWheel.verify(rightWheel).setSpeed(initialSpeed);
        inOrderRightWheel.verify(rightWheel).revolve();

    }

    private Field getSpeedField() {
        Field[] fields = classUnderTest.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("mSpeed")) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    private int getRandomSpeed() {
        return new Random().nextInt(101);
    }

    private int getRandomNumber() {
        return new Random().nextInt(100);
    }

    private int getRandomTurnValueWithin10() {
        return new Random().nextInt(11) + 1;
    }
}