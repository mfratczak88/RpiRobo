package rest.controllers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import rest.responses.OkResponse;
import robot.Robot;

public class ForwardController extends AbstractController implements Handler<RoutingContext> {
    public ForwardController(Robot robot) {
        super(robot);
    }

    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
        OkResponse.setOkResponse(routingContext.response());

        try {
            mRobot.forward(Integer.valueOf(request.getParam("speed")));
        } catch (Exception e) {
            mRobot.forward();
        }
        routingContext.next();
    }
}
