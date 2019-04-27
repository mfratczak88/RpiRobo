package rest.controllers;

import robot.Robot;

public abstract class AbstractController {
    protected Robot mRobot;

    public AbstractController(Robot robot) {
        mRobot = robot;
    }
}
