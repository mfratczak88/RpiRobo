package rest.responses;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;

public class OkResponse {
    public static void setOkResponse(HttpServerResponse response) {
        response.putHeader("content-type", "application/json")
                .end(Json.encodePrettily("Ok"));
    }
}
