package rest.controllers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import rest.responses.ErrorResponse;
import rest.responses.OkResponse;
import robot.Robot;

public class TurnController extends AbstractController implements Handler<RoutingContext> {
    public TurnController(Robot robot) {
        super(robot);
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String direction = routingContext.request().getParam("direction");
        if (direction == null) {
            ErrorResponse.setErrorResponse("No direction given", routingContext.response());
            return;
        } else if (direction != "left" && direction != "right") {
            ErrorResponse.setErrorResponse("Invalid direction given", routingContext.response());
        }


        String speed = routingContext.request().getParam("speed");
        if (speed == null) {
            if (direction == "left") {
                mRobot.turnLeft();
            } else {
                mRobot.turnRight();
            }
        } else {
            if (direction == "left") {
                mRobot.turnLeft(Integer.valueOf(speed));
            } else {
                mRobot.turnRight(Integer.valueOf(speed));
            }
        }

    }
}
