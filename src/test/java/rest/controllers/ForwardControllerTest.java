package rest.controllers;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import robot.Robot;


import java.util.Random;

import static org.mockito.Mockito.*;

public class ForwardControllerTest {
    private ForwardController classUnderTest;

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
        classUnderTest = new ForwardController(robot);
        when(routingContext.request()).thenReturn(request);
        when(routingContext.response()).thenReturn(response);
        when(response.putHeader("content-type", "application/json")).thenReturn(response);
    }

    @Test
    public void testHandleWithValue() {
        String speedParamValue = String.valueOf(new Random().nextInt());
        int speed = Integer.valueOf(speedParamValue);
        when(request.getParam("speed")).thenReturn(speedParamValue);
        classUnderTest.handle(routingContext);

        verify(robot, times(1)).forward(speed);

    }

    @Test
    public void testHandleWithOutValue() {
        when(request.getParam("speed")).thenReturn(null);
        classUnderTest.handle(routingContext);
        verify(robot).forward();

    }

}