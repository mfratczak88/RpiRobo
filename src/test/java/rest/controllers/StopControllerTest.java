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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StopControllerTest {
    private StopController classUnderTest;

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
        classUnderTest = new StopController(robot);
        when(routingContext.request()).thenReturn(request);
        when(routingContext.response()).thenReturn(response);
        when(response.putHeader("content-type", "application/json")).thenReturn(response);
    }

    @Test
    public void handle() {
        classUnderTest.handle(routingContext);
        verify(robot).stop();
    }

}