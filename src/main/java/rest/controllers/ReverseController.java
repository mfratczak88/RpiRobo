package rest.controllers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import rest.responses.OkResponse;
import robot.Robot;

public class ReverseController extends AbstractController implements Handler<RoutingContext> {
    public ReverseController(Robot robot) {
        super(robot);
    }

    @Override
    public void handle(RoutingContext routingContext) {
        OkResponse.setOkResponse(routingContext.response());
        try {
            mRobot.reverse(Integer.valueOf(routingContext.request().getParam("speed")));
        } catch (Exception e) {
            mRobot.reverse();
        }
        routingContext.next();
    }
}
