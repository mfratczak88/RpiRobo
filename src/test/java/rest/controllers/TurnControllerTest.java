package rest.controllers;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import robot.Robot;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TurnControllerTest {
    private TurnController classUnderTest;

    @Mock
    Robot robot;

    @Mock
    RoutingContext routingContext;

    @Mock
    HttpServerRequest request;

    @Mock
    HttpServerResponse response;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp() {
        classUnderTest = new TurnController(robot);
        when(routingContext.request()).thenReturn(request);
        when(routingContext.response()).thenReturn(response);
        when(response.putHeader("content-type", "application/json")).thenReturn(response);
        when(response.setStatusCode(400)).thenReturn(response);
    }

    @Test
    public void testNoDirection() {
        when(request.getParam("direction")).thenReturn(null);
        classUnderTest.handle(routingContext);
        verify(robot, never()).turnLeft();
        verify(robot, never()).turnRight();
    }

    @Test
    public void testWrongDirection() {
        when(request.getParam("direction")).thenReturn(null);
        classUnderTest.handle(routingContext);
        verify(robot, never()).turnLeft();
        verify(robot, never()).turnRight();
    }

    @Test
    public void testLeftTurnWithoutSpeed() {
        when(request.getParam("direction")).thenReturn("left");
        classUnderTest.handle(routingContext);
        verify(robot, times(1)).turnLeft();
    }

    @Test
    public void testRightTurnWithoutSpeed() {
        when(request.getParam("direction")).thenReturn("right");
        classUnderTest.handle(routingContext);
        verify(robot, times(1)).turnRight();
    }

    @Test
    public void testLeftTurnWithSpeed() {
        int speed = new Random().nextInt();
        when(request.getParam("direction")).thenReturn("left");
        when(request.getParam("speed")).thenReturn(String.valueOf(speed));
        classUnderTest.handle(routingContext);
        verify(robot, times(1)).turnLeft(speed);
    }

    @Test
    public void testRightTurnWithSpeed() {
        int speed = new Random().nextInt();
        when(request.getParam("direction")).thenReturn("right");
        when(request.getParam("speed")).thenReturn(String.valueOf(speed));
        classUnderTest.handle(routingContext);
        verify(robot, times(1)).turnRight(speed);
    }


}