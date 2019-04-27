package rest.controllers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import rest.responses.OkResponse;
import robot.Robot;

public class HomeController extends AbstractController implements Handler<RoutingContext> {
    public HomeController(Robot robot) {
        super(robot);
    }

    @Override
    public void handle(RoutingContext routingContext) {
        OkResponse.setOkResponse(routingContext.response());
    }
}
