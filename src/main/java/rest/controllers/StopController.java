package rest.controllers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import rest.responses.OkResponse;
import robot.Robot;

public class StopController extends AbstractController implements Handler<RoutingContext> {
    public StopController(Robot robot) {
        super(robot);
    }

    @Override
    public void handle(RoutingContext routingContext) {
        mRobot.stop();
        OkResponse.setOkResponse(routingContext.response());
    }
}
