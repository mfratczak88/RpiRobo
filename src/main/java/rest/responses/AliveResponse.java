package rest.responses;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;

public class AliveResponse {
    public static void setAliveResponse(HttpServerResponse  response){
        response.putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(Json.encodePrettily("I'm here"));
    }
}
