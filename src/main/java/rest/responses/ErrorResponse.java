package rest.responses;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;

public class ErrorResponse {
    public static void setErrorResponse(String reason, HttpServerResponse response) {
        response.putHeader("content-type", "application/json")
                .setStatusCode(400)
                .end(Json.encodePrettily(reason));

    }
}
