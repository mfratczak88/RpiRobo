import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import rest.controllers.*;
import robot.RoboFactory;
import robot.Robot;

public class App extends AbstractVerticle {
    private Router mRouter;
    private static Robot sRobot;

    @Override
    public void start() {
        setUpRobot();
        initRouter();
        bindRoutes();
        initServer();
    }

    private void initRouter() {
        mRouter = Router.router(vertx);

    }

    private void initServer() {
        vertx.createHttpServer()
                .requestHandler(mRouter::accept)
                .listen(config().getInteger("http-port", 8899));
    }

    private void bindRoutes() {
        mRouter.get("/").handler(new HomeController(sRobot));

        mRouter.post("/forward").handler(new ForwardController(sRobot));
        mRouter.post("/forward/:speed").handler(new ForwardController(sRobot));

        mRouter.post("/stop").handler(new StopController(sRobot));

        mRouter.post("/turn/:direction").handler(new TurnController(sRobot));
        mRouter.post("/turn/:direction/:speed").handler(new TurnController(sRobot));

        mRouter.post("/reverse").handler(new ReverseController(sRobot));
        mRouter.post("/reverse/:speed").handler(new ReverseController(sRobot));



    }


    private void setUpRobot() {
        try {
            sRobot = new RoboFactory().getRobot();
        } catch (Exception e) {
            vertx.exceptionHandler().handle(e);
        }
    }

    public static Robot getRobot() {
        return sRobot;
    }
}
