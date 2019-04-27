package robot.steeringSystem.motor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import robot.pwmPin.PwmPin;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MotorImplTest {
    @Mock
    PwmPin pinForwardMock;

    @Mock
    PwmPin pinBackwardMock;

    private MotorImpl classUnderTest;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        classUnderTest = new MotorImpl(pinForwardMock, pinBackwardMock);
    }

    @Test
    public void testRevolveForward() {
        int speed = new Random().nextInt(101);
        classUnderTest.setForwardDirection();
        classUnderTest.setSpeed(speed);
        classUnderTest.revolve();

        try {
            verify(pinForwardMock).write(speed);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRevolveBackWard() {
        int speed = new Random().nextInt(101);
        classUnderTest.setBackWardDirection();
        classUnderTest.setSpeed(speed);
        classUnderTest.revolve();

        try {
            verify(pinBackwardMock).write(speed);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSetSpeedUnderThreshold() {
        classUnderTest.setSpeed(new Random().nextInt(101) * (-1));
        assertEquals(0, classUnderTest.getSpeed());
    }

    @Test
    public void testSetSpeedOverThreshold() {
        classUnderTest.setSpeed(new Random().nextInt(101) + 100);
        assertEquals(100, classUnderTest.getSpeed());
    }

    @Test
    public void testCallToPinOnSetSpeed() {
        int initialSpeed = new Random().nextInt(101);
        int laterSpeed = new Random().nextInt(101);
        classUnderTest.setForwardDirection();
        classUnderTest.setSpeed(initialSpeed);
        classUnderTest.revolve();
        classUnderTest.setSpeed(laterSpeed);

        try {
            verify(pinForwardMock, times(1)).write(initialSpeed);
            verify(pinForwardMock, times(1)).write(laterSpeed);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testNoSimultaneousMovements() {
        int forwardSpeed = getRandomSpeedWithinRange();
        int backwardSpeed = getRandomSpeedWithinRange();

        classUnderTest.setForwardDirection();
        classUnderTest.setSpeed(forwardSpeed);
        classUnderTest.revolve();
        classUnderTest.setBackWardDirection();
        classUnderTest.setSpeed(backwardSpeed);

        InOrder orderVerifierPinForward = Mockito.inOrder(pinForwardMock);
        InOrder orderVerifierPinBackward = Mockito.inOrder(pinBackwardMock);
        try {
            orderVerifierPinForward.verify(pinForwardMock).write(forwardSpeed);
            orderVerifierPinForward.verify(pinForwardMock).write(0);
            orderVerifierPinBackward.verify(pinBackwardMock).write(forwardSpeed);
            orderVerifierPinBackward.verify(pinBackwardMock).write(backwardSpeed);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAbortionOnPinError() {
        try {
            int dummySpeed = getRandomSpeedWithinRange();
            classUnderTest.setSpeed(dummySpeed);
            doThrow(Exception.class).when(pinForwardMock).write(dummySpeed);
            Field fieldToCheck = getPinInUseField();
            PwmPin pwmPin = (PwmPin) fieldToCheck.get(classUnderTest);
            assertNull(pwmPin);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void stop() {
        classUnderTest.stop();
        Field fieldToCheck = getPinInUseField();
        try {
            PwmPin pwmPin = (PwmPin) fieldToCheck.get(classUnderTest);
            assertNull(pwmPin);
        } catch (Exception e) {
            fail();
        }
    }

    private Field getPinInUseField() {
        for (Field field : classUnderTest.getClass().getDeclaredFields()) {
            if (field.getName().contains("PinInUse")) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }


    private int getRandomSpeedWithinRange() {
        return new Random().nextInt(101);
    }
}